# 共享包知识库

**生成时间:** 2026-03-18
**范围:** 前端共享包

## 概述

FSS monorepo 前端的共享包。包含 `apps/` 目录下应用使用的可复用 UI 组件和工具函数。通过 pnpm workspaces 作为内部包发布。

## 目录结构

```
packages/
├── ui/          # 可复用 UI 组件（React + Ant Design）
└── utils/       # 共享工具函数和助手
```

## 开发导航

| 任务 | 包位置 | 说明 |
|------|---------|-------|
| 添加共享组件 | `ui/` | 以 Ant Design 6 为基础 |
| 添加工具函数 | `utils/` | 保持框架无关 |
| 更新导出 | `*/index.ts` | 维护公共 API 接口 |
| 组件样式 | `ui/src/styles/` | 使用 UnoCSS 类 |

## 包开发规范

- **ui 包**: React 19 + Ant Design 6 + UnoCSS 样式
- **utils 包**: 纯 TypeScript，无框架依赖
- 从 `index.ts` 导出所有公共 API
- 使用 TypeScript strict 模式
- 保持 peer dependencies 最小化
- 版本更新使用 changeset

## 常用命令

```bash
# 构建包
pnpm --filter ./packages/* build

# 测试包
pnpm --filter ./packages/* test

# 代码检查
pnpm --filter ./packages/* lint
```
