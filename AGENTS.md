# 项目知识库

**生成时间:** 2026-03-18
**分支:** main

## 概述

FSS（full-stack-scaffold）全栈脚手架，现代化 Monorepo 架构。支持多前端应用类型（Web、桌面端、移动端、小程序）和多语言后端（Java、Go、Rust），基于 Turborepo + pnpm 管理。

## 项目结构
```
fss/
├── apps/             # 前端应用目录（多个独立应用）
├── backend/          # 后端实现（多语言支持，独立）
├── packages/         # 前端共享包（UI组件、工具函数）
├── global/           # 全局配置、Protobuf 定义、脚本
└── 根配置文件        # Turborepo/pnpm/ESLint 等配置
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 前端开发 | `apps/web/admin-pc/` | React 管理后台示例 |
| 桌面应用 | `apps/desktop/toolbox/` | Tauri 桌面应用 |
| Java 后端 | `backend/admin-java/` | 完整 Spring Boot 项目 |
| Go 后端 | `backend/admin-go/` | Go 后端实现 |
| Rust 后端 | `backend/admin-rust/` | Rust 后端实现 |
| 共享 UI 组件 | `packages/ui/` | 可复用 UI 组件库 |
| 工具函数 | `packages/utils/` | 通用工具函数 |

## 开发规范
- 前端使用 TypeScript + React 19 + Ant Design 6
- 样式方案使用 UnoCSS 原子化 CSS
- Git 提交遵循 Conventional Commits 规范
- 多语言代码检查：ESLint（TS），SpotBugs（Java），golangci-lint（Go）
- 所有前端应用独立构建部署，共享 `packages/` 代码
- 后端语言实现相互独立，可选择任意一种作为后端

## 禁止模式 (本项目)
- 禁止直接修改他人维护的后端模块代码，保持后端语言独立性
- 不要把后端代码放入 pnpm workspace（已在 `.gitignore` 排除）
- 禁止绕过 Turborepo 直接在子应用运行命令，使用 turbo run 统一管理
- 不要提交敏感信息到 Git（查看 `.gitignore`）

## 特色风格
- Monorepo 只管理前端代码，后端作为独立模块存在于 `backend/`
- 支持多语言后端同时存在，便于技术选型对比
- 统一的代码质量检查配置，前端后端各语言自有检查

## 常用命令
```bash
# 安装依赖
pnpm install

# 启动开发服务器（指定应用）
cd apps/web/admin-pc && pnpm dev

# 全项目代码检查
pnpm lint

# 修复代码问题
pnpm lint:fix

# 使用 Turborepo 全量检查
pnpm turbo:lint

# 构建生产版本
pnpm build

# 格式化代码
pnpm format
```

## 备注
- 这是一个脚手架项目，各个模块都是可插拔的
- 只需要前端可删除整个 `backend/` 目录
- 只需要某种后端可删除其他语言后端目录
- 环境变量参考 `global/config/.env.example`
