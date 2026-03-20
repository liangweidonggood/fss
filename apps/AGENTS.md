# 应用目录知识库

## 概述

包含按平台类型组织的所有前端应用（网页、桌面端、移动端、小程序）。

## 目录结构

```
apps/
├── web/                          # 网页浏览器应用
│   └── admin-pc/                # React 管理后台（React 19 + Vite + AntD 6）
├── desktop/                     # 桌面端应用
│   └── toolbox/                 # Tauri 2 桌面工具应用
├── app/                         # 移动端应用（占位）
└── mini-program/                # 小程序应用（占位）
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| Web 管理后台开发 | `web/admin-pc/` | 使用 Ant Design 6 的全功能后台 |
| 桌面应用开发 | `desktop/toolbox/` | 使用 Tauri 2 + Rust 的跨平台应用 |
| 新增 Web 应用 | `web/<新应用名称>/` | 使用 Vite 模板 |
| 新增桌面应用 | `desktop/<新应用名称>/` | 使用 Tauri 模板 |
| 移动端应用（未来） | `app/` | 当前为占位 |
| 小程序（未来） | `mini-program/` | 当前为占位 |

## 开发规范

- **技术栈**: 所有应用使用 React 19 + TypeScript 5.9 + Vite 7 + UnoCSS
- **依赖**: 从 `@fss/ui` 和 `@fss/utils` 包导入共享代码
- **命名**: 使用 kebab-case 命名应用目录 (`admin-pc`, `toolbox`)
- **结构**: 每个应用都是独立的，包含自己的 `package.json`、`vite.config.ts` 和入口点
- **开发**: 在应用目录内运行 `pnpm dev`，不要从根目录运行
