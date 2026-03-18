# 工具函数包知识库

**用途:** 所有前端应用使用的共享工具函数包

## 概述

此包包含所有前端应用使用的纯工具函数、助手和共享逻辑。尽可能保持框架无关；这里不放置 React、Vue 或 UI 特定代码。

## 目录结构

```
utils/
├── src/
│   ├── string/          # 字符串操作助手
│   ├── number/          # 数学、格式化、验证
│   ├── date/            # 日期/时间工具
│   ├── object/          # 深克隆、合并、pick/omit
│   ├── array/           # 数组助手、去重、groupBy
│   ├── type/            # 类型守卫、断言
│   └── http/            # URL 解析、查询字符串助手
├── index.ts             # 公共导出
└── package.json
```

## 开发导航

| 任务 | 位置 | 说明 |
|------|------|------|
| 添加字符串工具 | `src/string/` | 使用 kebab-case 文件名 |
| 添加类型守卫 | `src/type/` | 按照 `isXxx()` 模式导出 |
| 重新导出全部 | `index.ts` | 保持公共 API 精简 |

## 开发规范

- **仅纯函数** — 无副作用，无 DOM 访问
- **全部类型化** — 所有导出明确返回类型
- **测试同位置** — `foo.ts` + `foo.test.ts` 放在一起
- **公共 API 使用 JSDoc** — 描述参数和返回值
- **无外部运行时依赖** — 开发依赖可以，保持包体积小

示例:
```ts
/**
 * 检查值是否为非空字符串
 * @param val - 要检查的值
 * @returns 非空字符串返回 true
 */
export function isNonEmptyString(val: unknown): val is string {
  return typeof val === 'string' && val.length > 0;
}
```
