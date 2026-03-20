# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

FSS (full-stack-scaffolding) 是一个**多语言跨平台全栈脚手架**，使用 `pnpm` + `Turborepo` 管理的 Monorepo 项目。支持多种前端应用类型（Web、桌面端、移动端、小程序）和多种后端语言实现（Java、Go、Rust）。

## 环境要求

- Node.js 22.21.1+
- pnpm 10.26.0+

## 常用命令

### 项目级命令

```bash
# 安装所有依赖
pnpm install

# 全项目代码检查 (ESLint + Stylelint + Prettier)
pnpm lint

# 自动修复 lint 错误和格式化问题
pnpm lint:fix

# 格式化所有代码
pnpm format

# 使用 Turborepo 构建整个项目
pnpm build

# 使用 Turborepo 运行优化的 lint 检查
pnpm turbo:lint
```

### 前端应用命令

```bash
# 启动 Web 管理后台开发服务器
cd apps/web/admin-pc && pnpm dev

# 构建 Web 管理后台生产版本
cd apps/web/admin-pc && pnpm build

# 启动 Tauri 桌面应用开发
cd apps/desktop/toolbox && pnpm tauri dev
```

### 后端代码检查命令

```bash
# Java 后端代码质量检查
pnpm admin-java:check

# Go 后端代码质量检查
pnpm admin-go:check
```

## 项目架构

### 目录结构

```
fss/
├── apps/                 # 前端应用（多个独立应用）
│   ├── web/admin-pc/     # React + TypeScript 管理后台
│   ├── desktop/toolbox/  # Tauri 桌面应用
│   ├── app/              # 移动端应用框架
│   └── mini-program/     # 小程序支持
├── backend/              # 多语言后端实现（互相独立）
│   ├── admin-java/       # Spring Boot (Java 21)
│   ├── admin-go/         # Go 后端
│   └── admin-rust/       # Rust 后端
├── packages/             # 前端共享包
│   ├── ui/               # 可复用 UI 组件库
│   └── utils/            # 通用工具函数库
├── global/               # 全局基础设施
│   ├── config/           # 共享配置（TypeScript、ESLint 等）
│   ├── proto/            # Protobuf API 定义
│   └── scripts/          # 全局构建脚本
└── 根配置文件            # Turborepo, pnpm, ESLint 等配置
```

### 架构特点

1. **Monorepo 只管理前端代码** - 后端作为独立模块存在于 `backend/` 目录
2. **多语言后端共存** - Java/Go/Rust 三种后端实现互相独立，可按需选择
3. **多应用架构** - 支持 Web、桌面、移动端、小程序，各应用独立构建部署
4. **代码共享** - 通过 `packages/` 共享 UI 组件和工具函数

## 开发规范

- 前端使用 **TypeScript + React 19 + Ant Design 6 + UnoCSS**
- Git 提交遵循 **Conventional Commits** 规范
- 统一代码质量检查：ESLint (TS), SpotBugs (Java), golangci-lint (Go)
- 后端语言互相独立，不要修改不相关的后端模块
- 不要将后端代码加入 pnpm workspace
- 使用 Turborepo 统一管理构建任务

## 技术栈

**前端:** React 19, TypeScript 5.9, Vite 7.2, Ant Design 6.1, UnoCSS 66.5, Redux Toolkit 2.11, React Router 7.11

**后端:** Java 21 (Spring Boot), Go 1.24.4, Rust 1.91.1, Python 3.14.0

**工具链:** pnpm 10.26, Turborepo 2.7, ESLint 9.39, Prettier 3.7, Husky 9.1