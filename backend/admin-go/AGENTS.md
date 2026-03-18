# Go 后端知识库

**生成时间:** 2026-03-18
**分支:** main

---

## 概述

`backend/admin-go` 是 FSS 管理后台的 **Go 语言实现**。当前为极简脚手架，基于 Go 1.24，准备进行完整 API 开发。

**状态:** 启动模板（尚无业务逻辑）

---

## 目录结构

```
backend/admin-go/
├── main.go           # 入口点（当前为演示代码）
├── go.mod            # 模块: admin-go, Go 1.24
├── .golangci.yml     # 代码检查配置（标准 + gofmt/goimports）
└── README.md         # 工具设置指南
```

**尚无子目录** - 需要按照 Go 约定添加 `internal/`、`cmd/`、`pkg/`。

---

## 开发导航

| 任务 | 文件 | 说明 |
|------|------|------|
| 添加 API 处理器 | 创建 `internal/api/` | 标准 Go 实践 |
| 配置管理 | 创建 `internal/config/` | 环境/数据库配置 |
| 业务逻辑 | 创建 `internal/services/` | 核心领域代码 |
| 数据库模型 | 创建 `internal/models/` | GORM/sqlx 结构体 |
| 主入口 | `main.go` | 替换演示代码 |
| 依赖管理 | `go.mod` | 添加导入后运行 `go mod tidy` |
| 代码质量 | `.golangci.yml` | 运行 `golangci-lint run` |

---

## Go 开发规范

- **最低版本**: Go 1.24
- **模块名**: `admin-go`（本地使用，不发布）
- **代码检查**: `golangci-lint` 使用标准检查器 + gofmt/goimports
- **目录结构**: 使用标准布局:
  - `/cmd/` - 可执行文件
  - `/internal/` - 私有应用代码
  - `/pkg/` - 公共库（如需）
- **格式化**: 始终运行 `gofmt` 或 `goimports`
- **依赖**: 使用 `go mod tidy` 清理

---

## 禁止模式 (本项目)

- **禁止**提交 `vendor/` 目录 - 使用 Go modules
- **禁止**把所有代码放 `main.go` - 尽早拆分成包
- **禁止**使用全局变量存储状态 - 依赖注入
- **禁止**忽略 `golangci-lint` 错误 - 提交前修复
- **禁止**导入未使用包 - `go mod tidy` 会导致 CI 失败
- **禁止**创建循环导入 - 提前规划包依赖

---

## 快速开始

```bash
cd backend/admin-go

# 安装 linter
go install github.com/golangci/golangci-lint/v2/cmd/golangci-lint@latest

# 运行代码检查
golangci-lint run

# 运行演示
go run main.go

# 添加依赖并清理
go mod tidy
```

---

*为 Go 后端开发生成 - 请与实际实现保持同步。*
