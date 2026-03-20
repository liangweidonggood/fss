# Java Spring Boot 后端知识库

## 概述

多模块 Spring Boot 3.3.13 后端，使用 Java 21，Gradle 构建系统，MyBatis-Flex ORM，Flyway 数据库迁移。支持 JWT 认证、多租户架构、RESTful API 和 Swagger 文档。

## 目录结构

```
admin-java/
├── admin-common/          # 共享基类、配置、工具
├── admin-auth/            # JWT 认证与安全
├── admin-core/
│   └── admin-core-service/  # 业务逻辑、服务、实体、控制器
├── admin-api/
│   └── admin-api-system/    # API 定义
├── admin-apps/
│   └── admin-apps-boot/     # 应用入口
├── doc/                    # SQL 迁移 (Flyway)
└── build.gradle.kts        # Gradle 构建配置
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| REST 控制器 | `admin-core-service/.../controller/` | `@RestController`, `@RequestMapping` |
| 业务逻辑 | `admin-core-service/.../service/` | 服务接口 + 实现 |
| 数据访问 | `admin-core-service/.../mapper/` | MyBatis-Flex 映射器 |
| 实体 | `admin-core-service/.../entity/` | ORM 实体 |
| 配置 | `admin-common/.../config/` | 缓存、Jackson、异常处理 |
| 安全 | `admin-auth/.../` | JWT 过滤器、SecurityConfig |
| 数据库迁移 | `doc/*.sql` | Flyway SQL 脚本 |
| 基类 | `admin-common/.../base/` | `Result`, `ResultCode`, `BaseEntity` |

## 开发规范

- **包名**: `com.lwd.admin.{模块}.{功能}`
- **依赖注入**: 通过 `@RequiredArgsConstructor` 构造注入 (Lombok)
- **响应**: 使用 `com.lwd.admin.base` 中的 `Result<T>` 包装
- **实体**: 继承 `BaseEntity` 获取审计字段
- **配置**: 使用 `@ConfigurationProperties` 处理自定义属性
- **测试**: 单元测试放 `src/test/java`（尚未添加）
- **API 文档**: 使用 `@Tag` 和 `@Operation` 注解给 Knife4j/Swagger

## 禁止模式

- **不要**在控制器直接注入 `RedisTemplate` — 使用 Service 层
- **不要**在 Mapper/Repository 类放业务逻辑 — 放在 Service
- **不要**在 Controller 方法使用 `@Transactional` — 委托给 Service
- **不要**绕过 Service 层直接进行数据库操作
