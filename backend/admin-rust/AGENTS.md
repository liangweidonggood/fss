# Rust 后端知识库

**生成时间:** 2026-03-18
**范围:** Rust 后端

## 概述

FSS 管理后台服务的 Rust 后端实现。使用 Rust 1.91.1+ 和 Cargo 构建，设计追求高性能和内存安全。当前为极简脚手架结构。

## 目录结构
```
admin-rust/
├── Cargo.toml      # Package manifest, dependencies
├── src/
│   └── main.rs     # Application entry point
└── AGENTS.md       # This file
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 入口点 | `src/main.rs` | 主函数，应用启动 |
| 依赖 | `Cargo.toml` | crate 声明，版本 |
| API 路由 | 创建 `src/routes/` | 推荐结构 |
| 模型 | 创建 `src/models/` | 数据结构，ORM 实体 |
| 服务 | 创建 `src/services/` | 业务逻辑层 |
| 配置 | 创建 `src/config.rs` | 环境，设置 |

## Rust 开发规范

- 使用 Rust 2024 edition 特性
- 遵循 `cargo fmt` 和 `cargo clippy` 指南
- 使用 `?` 操作符传播错误，生产环境避免 `unwrap()`
- 优先使用 `Result<T, E>` 而非 panic
- 使用 `tracing` crate 进行结构化日志
- 异步运行时: `tokio`（推荐用于 Web 服务器）
- Web 框架: `axum` 或 `actix-web`（选择一个，保持一致）
- 配置: `config` crate 处理环境特定文件
- 使用 `serde` 进行序列化/反序列化
- 命名: 函数/变量 `snake_case`，类型 `CamelCase`，常量 `SCREAMING_SNAKE_CASE`

## 禁止模式 (本项目)

- Never use `std::mem::transmute` or unsafe blocks without thorough documentation
- Avoid blocking operations in async contexts (use `tokio::task::spawn_blocking`)
- Don't clone large data structures unnecessarily, use references (`&T` or `&str`)
- Never ignore `Result` types with `let _ = ...`, handle errors explicitly
- Avoid `async fn` in traits without `async-trait` or Rust 1.75+ native support
- Don't mix multiple async runtimes (stick to tokio)
- Avoid deep nesting of match statements, use early returns or `?` operator
- Never store secrets in source code, use environment variables
- Don't use `Box<dyn Error>` in library code, use concrete error types

## 常用命令

```bash
# 构建项目
cargo build

# 构建发布版本
cargo build --release

# 运行应用
cargo run

# 运行测试
cargo test

# 格式化代码
cargo fmt

# 运行 linter (clippy)
cargo clippy

# 检查不构建
cargo check

# 添加依赖
cargo add tokio --features full
cargo add axum serde serde_json tracing tracing-subscriber
```

## 备注

- 这是一个脚手架项目，按照 Rust 项目约定扩展 `src/`
- 如果项目变大，考虑拆分成多个 workspace crates
- 开发时使用 `cargo watch` 实现热重载: `cargo install cargo-watch`
- 数据库集成: `sqlx`（编译期检查查询）或 `diesel`（完整 ORM）
- API 文档: `utoipa` 生成 OpenAPI/Swagger
- 后端独立于前端 monorepo，由 Cargo 管理而非 pnpm
