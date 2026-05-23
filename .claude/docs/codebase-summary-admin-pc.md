# Web 管理后台 (admin-pc) 代码库总结

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 框架 | React | ^19.2.0 |
| 构建 | Vite | ^7.2.4 |
| 语言 | TypeScript | ~5.9.3 |
| UI 组件 | Ant Design (antd) | ^6.1.1 |
| 图标 | @ant-design/icons | ^6.1.0 |
| CSS | UnoCSS + preset-wind3 | ^66.5.10 |
| 状态管理 | Redux Toolkit + react-redux | ^2.11.2 / ^9.2.0 |
| 路由 | react-router | ^7.11.0 |

## 目录结构

```
src/
├── main.tsx                          # 入口：多层 Provider 嵌套
├── router/index.tsx                  # 路由集中定义
├── store/index.ts + authSlice.ts     # Redux 状态管理
├── utils/captcha.ts                  # Canvas 验证码生成
├── components/
│   ├── PrivateRoute/                 # 路由守卫
│   └── loading/                      # 加载占位组件
├── layouts/root-layout/              # 根布局：导航栏 + Outlet + 页脚
└── pages/
    ├── home/                         # 首页（欢迎横幅 + 功能卡片 + 数据概览）
    ├── login/                        # 登录页（双栏布局 + 验证码）
    └── user/user-list/               # 用户列表（骨架占位）
```

## Provider 嵌套层级

```
StrictMode
  └── ConfigProvider locale={zhCN}
      └── AppContainer
          └── Provider store={store}
              └── RouterProvider
```

## 路由设计

```
/login          → LoginPage (公开)
/               → PrivateRoute (守卫)
  /             → RootLayout > Home (首页)
  /user-list    → RootLayout > UserList (用户列表)
```

## 认证流程

- **状态**: Redux `authSlice` (isLoggedIn + username)
- **持久化**: localStorage `auth_state`，每次变更同步写入
- **登录**: 硬编码 admin/123，模拟 800ms 延迟
- **验证码**: 纯前端 Canvas 生成（干扰线 + 噪点 + 旋转文字），首次登录失败后显示
- **登出**: 清除状态 + localStorage
- **记住我**: 勾选 → localStorage，否则 sessionStorage

## UI 模式

- 全站 UnoCSS 原子类，禁止内联样式对象
- Ant Design ConfigProvider 全局中文 locale
- 渐变文字 Logo: `bg-gradient-to-r from-blue-500 to-purple-600`
- 首页：Card 组件 + Row/Col 响应式网格
- 路由切换：`useNavigation().state === 'loading'` 显示 Loading 组件

## 当前开发阶段

早期阶段：用户列表为骨架，认证为纯前端模拟，首页数据为静态占位。整体架构骨架已就绪。
