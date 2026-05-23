# Go/Rust 后端、共享包与全局配置总结

## 一、Go 后端 (backend/admin-go)

### 当前状态
极简脚手架，`main.go` 仅包含 GoLand 生成的演示代码（打印 Hello + 循环输出除法）。

### 技术栈
- **语言**: Go 1.24，模块名 `admin-go`
- **Web 框架**: 尚未引入
- **ORM**: 尚未引入
- **代码检查**: golangci-lint v2 (standard + gofmt + goimports)
- **依赖**: 零外部依赖

### 检查配置
- standard 级别，禁用 dogsled
- gofmt + goimports 启用
- 超时 5 分钟，modules-download-mode: readonly

### 架构约定
推荐布局: `/cmd/`, `/internal/` (api/config/services/models), `/pkg/`
禁止: vendor 目录、全局变量、循环导入、忽略 linter 错误

## 二、Rust 后端 (backend/admin-rust)

### 当前状态
极简脚手架，`main.rs` 仅 "Hello, world!"。`Cargo.toml` 依赖为空。

### 技术栈
- **语言**: Rust 1.91.1+, 2024 edition
- **包名**: admin-rust v0.1.0
- **框架**: 尚未引入（推荐 axum/actix-web + tokio + sqlx/diesel）

### 架构约定
推荐结构: `src/main.rs`, `src/routes/`, `src/models/`, `src/services/`, `src/config.rs`
禁止: unsafe 无文档、异步中阻塞、混合异步运行时、源码存密钥

## 三、共享 UI 包 (packages/ui)

- **包名**: @fss/ui v1.0.0
- **结构**: `src/components/`, `src/hooks/`, `src/theme/`, `src/index.ts`
- **规范**: PascalCase 命名、UnoCSS 样式、避免内联样式、同时导出命名和默认导出、JSDoc 文档

## 四、共享工具包 (packages/utils)

- **包名**: @fss/utils v1.0.0
- **结构**: `src/string/`, `src/number/`, `src/date/`, `src/object/`, `src/array/`, `src/type/`, `src/http/`
- **规范**: 仅纯函数（无副作用、无 DOM）、全部类型化、零外部运行时依赖

## 五、全局 TypeScript 配置 (tsconfig.base.json)

- target ES2022, module ESNext, bundler 模式
- jsx: react-jsx, strict: true, noEmit: true
- 禁止未使用局部变量/参数
- verbatimModuleSyntax, erasableSyntaxOnly

## 六、全局 Java 代码检查

### Checkstyle (checkstyle.xml)
- 基于 Google Java Style
- UTF-8 编码，行宽 120，基础缩进 4 空格，case 缩进 2
- 全面命名规范 + Javadoc 要求
- 支持 CHECKSTYLE.OFF/ON 注释过滤

### PMD (ruleset.xml)
- 7 大类规则: 最佳实践、代码风格、设计、文档、错误易发性、多线程、性能、安全
- TooManyMethods 上限 30
- AvoidDuplicateLiterals 重复上限 10 次/最小长度 3

## 七、跨项目模式总结

1. **全栈脚手架**: 后端/共享包均处于框架初始状态，AGENTS.md 作为开发蓝图
2. **独立构建**: Go Modules / Cargo / pnpm + Turborepo 互不干扰
3. **多语言质检**: golangci-lint / cargo clippy / Checkstyle+PMD / ESLint+Prettier+Stylelint
4. **前端共享**: packages/ui (组件) + packages/utils (工具函数)
5. **API 定义**: `global/proto/` 存放 Protobuf 跨语言 API 定义

```
FSS Monorepo (pnpm + Turborepo)
├── packages/ (ui + utils) — 前端共享
├── apps/ (web / desktop / app / mini-program)
├── Go 后端 (独立) — golangci-lint
├── Rust 后端 (独立) — cargo clippy
└── Java 后端 (独立) — Checkstyle + PMD + Gradle
    └── global/proto/ — API 定义
```
