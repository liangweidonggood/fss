# FSS 全栈脚手架介绍

## 1. 项目概述

FSS（full-stack-scaffolding）是一个功能完备的全栈脚手架，旨在提供可扩展的前端架构和多业务多语言后端支持，帮助开发者快速构建现代化全栈应用。

## 2. 技术栈

### 2.1 前端技术
- **框架**: React 19
- **语言**: TypeScript 5.9
- **构建工具**: Vite 7.2
- **UI 框架**: Ant Design 6.1
- **样式方案**: UnoCSS 66.5
- **状态管理**: Redux Toolkit 2.11 + React Redux 9.2
- **路由**: React Router 7.11

### 2.2 后端技术
支持多种后端语言：
- Java 21
- Rust 1.91.1
- Go 1.24.4
- Python 3.14.0

### 2.3 开发工具链
- **包管理**: pnpm 10.26
- **Monorepo 管理**: Turborepo 2.7
- **代码质量**: ESLint 9.39 + Prettier 3.7 + Stylelint 16.26
- **Git 钩子**: Husky 9.1 + commitlint

## 3. 项目结构

```
fss/
├── apps/                 # 应用目录
│   ├── app/             # 移动端应用
│   ├── desktop/         # 桌面端应用
│   ├── mini-program/    # 小程序应用
│   └── web/             # Web 应用
│       └── admin-pc/    # 管理后台
├── backend/             # 后端代码
├── global/              # 全局资源
│   ├── config/          # 全局配置
│   ├── proto/           # Protobuf 定义
│   └── scripts/         # 全局脚本
├── packages/            # 共享包
│   ├── ui/              # UI 组件库
│   └── utils/           # 工具函数库
└── 根配置文件           # 项目级配置
```

## 4. 核心特性

### 4.1 多应用架构
- 支持 Web、桌面端、移动端和小程序等多种应用类型
- 各应用独立开发、构建和部署
- 共享代码和组件，提高开发效率

### 4.2 多语言后端支持
- 支持 Java、Rust、Go 和 Python 等多种后端语言
- 适合不同业务场景和团队技术栈
- 便于横向扩展和技术选型

### 4.3 现代化前端架构
- React 19 配合 TypeScript 提供类型安全
- Vite 构建工具提供快速开发体验
- UnoCSS 提供原子化样式方案
- Ant Design 提供成熟的 UI 组件
- Redux Toolkit 提供高效的状态管理

### 4.4 完善的开发工具链
- Monorepo 结构便于代码共享和管理
- 统一的代码规范和质量检查
- Git 提交规范确保代码提交质量
- 自动化的预提交检查

## 5. 快速开始

### 5.1 环境要求
- Node.js 22.21.1+
- pnpm 10.26.0+

### 5.2 安装依赖
```bash
pnpm install
```

### 5.3 开发命令
```bash
# 启动 Web 管理后台开发服务器
cd apps/web/admin-pc
pnpm dev

# 运行代码质量检查
pnpm lint

# 修复代码质量问题
pnpm lint:fix
```

### 5.4 构建命令
```bash
# 构建 Web 管理后台
cd apps/web/admin-pc
pnpm build
```

## 6. 代码规范

遵循 Vue 项目的提交规范，支持以下类型：
- `feat`: 增加新功能
- `fix`: 修复问题/BUG
- `style`: 代码风格相关
- `perf`: 优化/性能提升
- `refactor`: 重构
- `revert`: 撤销修改
- `test`: 测试相关
- `docs`: 文档/注释
- `chore`: 依赖更新/脚手架配置修改等
- `workflow`: 工作流改进
- `ci`: 持续集成
- `types`: 类型定义文件更改
- `wip`: 开发中

示例：
```
feat: 全局配置
```

## 7. 总结

FSS 全栈脚手架提供了一个现代化、可扩展的全栈开发框架，支持多种前端应用类型和后端语言，适合构建复杂的企业级应用。其完善的开发工具链和代码规范有助于提高开发效率和代码质量，是团队协作开发的理想选择。

## 8. 许可证

ISC License