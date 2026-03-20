# 管理后台知识库

**生成时间:** 2026-03-18
**分支:** main

## 概述

React 19 管理后台前端应用（admin-pc），基于 Vite 构建，采用 Ant Design 6 + UnoCSS 技术栈。支持现代化开发体验，包含路由管理、布局系统、组件复用等完整基础设施。

## 目录结构

```
src/
├── main.tsx              # 应用入口：ReactDOM.createRoot +  providers
├── router/
│   └── index.tsx         # 路由定义：createBrowserRouter
├── layouts/
│   └── root-layout/      # 根布局：导航 + <Outlet />
├── pages/
│   ├── home/             # 首页
│   └── user/             # 用户模块（示例）
└── components/           # 可复用组件
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 新增页面 | `src/pages/{module}/` | 按模块组织页面组件 |
| 添加路由 | `src/router/index.tsx` | 在 routes 数组中追加 |
| 修改布局 | `src/layouts/root-layout/` | 调整导航、侧边栏等 |
| 复用组件 | `src/components/` | 跨页面通用组件 |
| 样式调整 | 使用 UnoCSS 类名 | 如 `className="flex gap-4"` |

## 开发规范

- **组件**: 使用默认导出 `export default`，命名采用 PascalCase
- **路径别名**: `@/` 指向 `src/`，如 `import X from '@/components/X'`
- **样式方案**: UnoCSS 原子化 CSS，Tailwind 风格类名
- **路由**: React Router v7，使用 `createBrowserRouter` 定义
- **UI 组件**: Ant Design 6.x，中文 locale 自动配置
- **状态管理**: 预留 Redux Toolkit（已安装未使用），按需启用

## 禁止模式 (本应用)

- 禁止在组件内直接写样式对象，统一使用 UnoCSS 类名
- 不要把页面组件放在 `src/components/`，区分页面 vs 复用组件
- 禁止绕过 `@/` 别名使用相对路径 `../`
- 不要把路由定义分散到各页面文件，统一在 `router/index.tsx` 管理
- 不要提交包含真实 API 密钥的 `.env` 文件
