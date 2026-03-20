# Tauri 工具箱应用知识库

**生成时间:** 2026-03-18
**应用:** 桌面端工具盒

## 概述

Tauri v2 桌面应用，TCP 网络工具箱。前后端分离架构：
- 前端：React 19 + TypeScript + Vite 7 + UnoCSS
- 后端：Rust + Tauri 2 + Tokio 异步运行时
- IPC：Tauri Commands (invoke) + Events (emit/listen)

核心功能：TCP Server（监听端口、接收连接）和 TCP Client（连接服务端、收发消息）。

## 目录结构

```
toolbox/
├── src/                    # 前端 React 代码
│   ├── main.tsx           # React 入口
│   ├── App.tsx            # 主布局（Tab 切换）
│   ├── components/        # UI 组件
│   │   ├── Sidebar.tsx   # 侧边栏导航
│   │   ├── TcpServer.tsx # TCP Server 面板
│   │   └── TcpClient.tsx # TCP Client 面板
│   ├── context/           # React Context
│   │   └── LogContext.tsx # 日志全局状态
│   └── hooks/             # 自定义 Hooks
│       └── useLogs.tsx
├── src-tauri/             # Rust 后端代码
│   ├── src/
│   │   ├── main.rs        # 二进制入口
│   │   ├── lib.rs         # Tauri Builder + 命令注册
│   │   ├── tcp_server.rs  # TCP Server 实现
│   │   └── tcp_client.rs  # TCP Client 实现
│   ├── tauri.conf.json    # Tauri 配置
│   ├── Cargo.toml         # Rust 依赖
│   └── capabilities/      # Tauri 权限配置
├── vite.config.ts         # Vite 配置
├── uno.config.ts          # UnoCSS 配置
└── package.json
```

## 开发导航

| 任务 | 文件路径 | 说明 |
|------|----------|------|
| 添加 Tauri Command | `src-tauri/src/lib.rs` | 添加 `#[tauri::command]` 函数并注册到 `invoke_handler` |
| 调用 Rust 函数 | `src/components/*.tsx` | 使用 `invoke('command_name', { args })` |
| 监听后端事件 | `src/components/*.tsx` | 使用 `listen('event_name', callback)` |
| 修改 TCP Server 逻辑 | `src-tauri/src/tcp_server.rs` | Tokio 异步 TCP 实现 |
| 修改 TCP Client 逻辑 | `src-tauri/src/tcp_client.rs` | Tokio 异步 TCP 实现 |
| 添加 UI 组件 | `src/components/` | React + TypeScript + UnoCSS |
| 修改日志系统 | `src/context/LogContext.tsx` | React Context 全局状态 |
| 添加图标 | 使用 `i-carbon-*` 类 | UnoCSS preset-icons |

## 开发规范

- **IPC 模式**: Commands 用于请求-响应，Events 用于服务端推送（如日志实时更新）
- **状态管理**: Rust 用 `tauri::State<Mutex<T>>`，React 用 Context API
- **样式**: UnoCSS 原子类，图标用 `i-carbon-*` 前缀
- **异步**: Rust 命令函数标记 `async`，Tokio 处理 TCP 并发
- **错误处理**: Rust 返回 `Result<T, String>`，前端 `try/catch` 包裹 `invoke`
- **日志流**: Rust 通过 `app.emit("server-log", payload)` 推送，React 用 `listen()` 订阅

## 禁止模式 (本应用)

- **禁止阻塞主线程**: Rust 命令必须使用 `async` + Tokio，严禁同步阻塞操作
- **禁止直接操作 DOM**: React 单向数据流，禁止绕过 React 直接修改 DOM
- **禁止在组件内定义 Command**: Tauri Commands 必须定义在 `lib.rs`，前端仅调用
- **禁止混用同步/异步 State**: Rust State 统一用 `Mutex<T>` 包装，禁止裸读写
- **禁止硬编码端口/IP**: TCP 配置从 UI 输入，禁止代码里写死地址
- **禁止忽略 Tokio 任务句柄**: 开启 TCP Server/Client 必须保存 JoinHandle，用于优雅关闭
- **禁止在 React render 里调用 invoke**: 必须在 `useEffect` 或事件处理器里调用
