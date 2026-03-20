# UI 组件库知识库

**生成时间:** 2026-03-18
**包名:** @fss/ui

## 概述

这是 FSS monorepo 的共享 UI 组件库。导出包装 Ant Design 的可复用 React 组件，并使用项目特定的样式和行为模式。所有前端应用都从此包导入以确保产品 UI 一致性。

## 目录结构

```
packages/ui/
├── src/
│   ├── components/       # 组件定义
│   ├── hooks/            # 自定义 React hooks
│   ├── theme/            # 主题令牌和覆盖
│   └── index.ts          # 公共导出
├── package.json
└── tsconfig.json
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 添加新组件 | `src/components/` | 从 `index.ts` 导出 |
| 修改主题 | `src/theme/` | Ant Design 令牌覆盖 |
| 共享 hooks | `src/hooks/` | UI 相关 React hooks |
| 检查导出 | `src/index.ts` | 公共 API 接口 |

## 组件开发规范

- **命名:** 组件使用 PascalCase（例如 `DataTable`, `SearchForm`）
- **Props:** 包装时扩展 Ant Design props，自定义 props 添加 `fss` 前缀
- **样式:** 使用 UnoCSS 工具类，避免内联样式
- **导出:** 组件文件同时导出命名导出和默认导出
- **文档:** 公共 props 和复杂逻辑添加 JSDoc
- **测试:** 测试与组件同位置（`.test.tsx`）
- **依赖:** 最小化外部依赖，优先使用 Ant Design 原语
