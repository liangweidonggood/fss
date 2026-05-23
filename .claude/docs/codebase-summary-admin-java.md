# Java 后端 (admin-java) 代码库总结

## 一、Gradle 多模块结构

```
admin-java/
├── admin-common/           # 公共基础设施
│   └── base/ Result.java, ResultCode.java, BaseEntity.java
│   └── config/ (Jackson, ResponseAdvice, SchemaContextHolder, ThreadPool, cache, exception, flex)
│   └── constant/ DatePattern.java, DbColumn.java
│   └── util/ FlexCodegen.java, FlexCodegenData.java
├── admin-auth/             # 认证与安全模块
│   └── config/ SecurityConfig, AuthenticationTokenFilter, DynamicAuthorizationManager
│   └── controller/ LoginController
│   └── service/ CustomUserDetailsServiceImpl
├── admin-core/             # 核心业务模块
│   └── admin-core-service/
│       └── component/ FlywayMigrator (多租户迁移)
│       └── config/tenant/ TenantInterceptor, WebConfig
│       └── system/ (controller, entity, mapper, service/impl: Tenant, User)
│       └── util/ CoreCodegen, TenantUtil
│       └── resources/ db/migration/, mapper/, templates/
├── admin-api/              # API 模块
│   └── admin-api-system/
├── admin-apps/             # 应用入口
│   └── admin-apps-boot/ (启动类 + 配置文件)
├── doc/                    # SQL 脚本
└── build.gradle.kts + settings.gradle.kts + gradle.properties
```

## 二、技术栈

- **框架**: Spring Boot 3.3.13 + Java 21
- **ORM**: MyBatis-Flex 1.10.5 + MyBatis Spring Boot 3.0.5
- **数据库**: MySQL 8.3.0, HikariCP 连接池
- **缓存**: Caffeine 3.2.0 + Redis (Lettuce 6.5.1)
- **安全**: Spring Security 6.x + JWT (jjwt 0.12.12)
- **迁移**: Flyway 10.15.0
- **文档**: Knife4j 4.5.0
- **工具库**: Guava 33.10, Lombok 1.18.42, Hutool 5.8.37
- **代码检查**: Checkstyle (Google Java Style) + PMD

## 三、多租户架构

### 动态 Schema 机制
1. **SchemaContextHolder**: 使用 `InheritableThreadLocal` 存储当前 schema
2. **TenantInterceptor**: 拦截请求，从 Header 或 JWT 解析 tenant ID，查库获取 schema 名，注入上下文
3. **MyDynamicSchemaProcessor**: 运行时动态替换表名前缀 schema
4. **TenantUtil**: 提供 `set/get/clear` 辅助方法

### 多租户数据隔离
- 每个租户独立数据库 schema
- FlywayMigrator 在应用启动时为每个租户独立执行迁移
- Redis key 使用 `{schema}:` 前缀隔离

## 四、安全认证

### SecurityConfig
- 无状态会话 `sessionCreationPolicy = STATELESS`
- 禁用 CSRF、CORS
- 公开路由: `/auth/login`, `/*/api-docs/**`, `/error`, `/favicon.ico`
- 认证入口: `AuthenticationTokenFilter`

### JWT 认证 (占位实现)
- `AuthenticationTokenFilter`: 解析 Bearer Token → 提取 username → 查库 → 设置 SecurityContext
- `DynamicAuthorizationManager`: 权限校验（硬编码占位 "USER"）
- `CustomUserDetailsServiceImpl`: 从 UserService 加载用户，Caffeine 缓存

### LoginController
- `POST /auth/login`: 接收 username/password，验证后返回 JWT
- 登录成功缓存 `loginToken:{token}` → `username:{token}`

## 五、缓存策略

### 双缓存架构
| 缓存 | 用途 | 配置 |
|------|------|------|
| Caffeine (shortTerm) | 用户详情、权限 | expireAfterWrite=30min, maxSize=500 |
| Caffeine (permanent) | 字典、配置 | 无过期, maxSize=1000 |
| Redis | 登录令牌、分布式缓存 | 前缀 `app:tenant:{schema}`, JSON序列化 |

### DynamicPrefixStringSerializer
- Redis key 前缀生成器，读取当前 SchemaContext 动态确定前缀
- 自动添加 `app:tenant:{schema}:` 前缀

## 六、数据库迁移 (Flyway)

### FlywayMigrator
- 触发时机: ApplicationReadyEvent
- 流程: 查询所有租户 → 为每个租户创建独立 Flyway 实例 → 执行迁移
- 迁移文件: `V2__init_user.sql`, `V3__add_user_security_fields.sql`

### 实体设计

**User 表 (sys_user)**:
- 审计字段: create_user, create_time, update_user, update_time
- 安全字段: password, status, locked, account_expired, credentials_expired
- MyBatis-Flex: `@Id(keyType = KeyType.Auto)`, 逻辑删除 `@Column(isLogicDelete = true)`, 乐观锁 `@Column(version = true)`

**Tenant 表 (sys_tenant)**:
- 字段: id, tenant_name, schema_name, status, expire_time, max_users
- 软删除策略

## 七、全局配置

### JacksonConfig
- 日期格式: `yyyy-MM-dd HH:mm:ss`
- 时区: GMT+8
- 禁用: WRITE_DATES_AS_TIMESTAMPS

### ResponseAdvice
- 实现 `ResponseBodyAdvice`，统一包装返回值
- 排除: Result 类型、String 类型、swagger/error 路径

### ThreadPoolConfig
- CPU 密集型: 虚拟线程 (VirtualThreadPerTaskExecutor)
- IO 密集型: `FixedThreadPool` core=50, max=100
- 周期性任务: `ScheduledThreadPool`

### GlobalExceptionHandler
覆盖: MethodArgumentNotValidException, BindException, ConstraintViolationException, BusinessException, AccessDeniedException, AuthenticationException, Exception (兜底)

### CustomErrorController
- 实现 `ErrorController`，处理 404/500 等，统一返回 Result JSON

### FaviconController
- 返回空 favicon 避免 Spring Boot 默认 favicon 请求打印异常日志

## 八、MyBatis-Flex 配置

### FlexConfig
- 注册全局监听器: GlobalInsertListener (自动设置 create_time/create_user), GlobalUpdateListener (自动设置 update_time/update_user), GlobalOnSetListener (审计字段 OnSet 事件)
- DataAuthDialectImpl: 数据权限方言实现

### 代码生成 (FlexCodegen)
- 输入: 模块名、表名、基础包路径
- 输出: Entity、Mapper、Service、ServiceImpl、Controller、Mapper XML、TableDef
- 使用 FlexCodegenData 配置生成参数

## 九、关键模式

1. **构造注入优于字段注入**: 使用 @RequiredArgsConstructor
2. **审计自动填充**: 通过 MyBatis-Flex 全局监听器自动处理
3. **线程池分离**: CPU/IO 任务使用不同线程池
4. **响应统一包装**: Result<T> (code + message + data)
5. **异常分级处理**: BusinessException 业务异常，GlobalExceptionHandler 统一处理
6. **缓存穿透防护**: Redis + Caffeine 双缓存，不同 TTL 策略
7. **包命名**: `com.lwd.admin.{module}.{layer}`
