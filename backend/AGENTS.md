# 后端目录知识库

## 概述

FSS 全栈脚手架的多语言后端目录，支持 Java、Go、Rust 三种实现。

## 目录结构

| 目录 | 说明 |
|-----------|-------------|
| `admin-java/` | Spring Boot 模块化架构项目（认证、API、核心、应用），使用 Gradle 构建，MyBatis-Flex ORM，Flyway 迁移 |
| `admin-go/` | 使用 Gin 框架的极简 Go 后端。入口: `main.go` |
| `admin-rust/` | 极简 Rust 后端。入口: `src/main.rs` |

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 全功能后端 | `admin-java/` | 完整 Spring Boot 项目，包含认证、系统模块、云原生结构 |
| 快速 REST API | `admin-go/` | 轻量级 Go 服务，最简配置 |
| 高性能服务 | `admin-rust/` | 适用于性能关键场景的 Rust 后端 |
| 数据库迁移 | `admin-java/doc/` 或 `admin-java/src/main/resources/db/migration` | Flyway SQL 迁移 |

## 开发规范

- **构建**: 每个后端独立，在各自目录内运行构建命令
- **Java**: 使用 Gradle wrapper (`./gradlew`) 构建
- **Go**: 使用标准 `go build` / `go run`
- **Rust**: 使用 Cargo (`cargo build` / `cargo run`)
- **数据库**: Java 后端使用 Flyway；其他后端需要手动配置迁移
