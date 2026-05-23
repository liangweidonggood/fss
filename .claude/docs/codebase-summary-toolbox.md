# 桌面工具箱 (Toolbox) 代码库总结

## 技术栈

- **前端**: React 19 + TypeScript 5.8 + Vite 7 + UnoCSS 66
- **Tauri**: v2, IPC 通过 `invoke()` + `listen()` 事件
- **Rust**: Tokio (full) + Serde, edition 2021
- **发布优化**: LTO, opt-level=z, panic=abort, strip=true

## 组件层级

```
App (useState activeTab)
  └── LogProvider (React Context: serverLogs + clientLogs)
      └── flex h-screen w-screen
          ├── Sidebar (w-64)
          │   ├── TCP Server (i-carbon-network-4)
          │   ├── TCP Client (i-carbon-network-2)
          │   └── Settings 占位 (i-carbon-settings)
          └── 内容区 (flex-1)
              ├── TcpServer 面板
              ├── TcpClient 面板
              └── Settings 占位页
```

## Tauri Commands (6个)

| 命令 | 功能 |
|------|------|
| `start_tcp_server(port)` | 启动 TCP 服务端 |
| `stop_tcp_server()` | 停止 TCP 服务端 |
| `start_tcp_client(ip, port)` | 连接 TCP 服务端 |
| `stop_tcp_client()` | 断开 TCP 连接 |
| `send_tcp_client_message(message)` | 发送消息 |

## Tauri Events (3个)

`server-log`, `client-log`, `client-disconnected`

## TCP 服务端实现

- 绑定 `0.0.0.0:{port}`，双重检查锁防竞态
- `broadcast::channel(1)` 关闭信号
- `tokio::select!` 优雅关闭
- 每连接 spawn 独立 Tokio 任务，1024 字节读缓冲

## TCP 客户端实现

- `TcpStream::connect` → `into_split()` 分离读写
- Writer 存入状态，`take()` 取出发送 → 放回
- 错误时发出 `client-disconnected` 事件

## 状态管理

LogContext 两层日志分离:
- `serverLogs / clientLogs` 独立数组
- `addServerLog / addClientLog` 追加条目
- `clearServerLogs / clearClientLogs` 清空

## UI 设计

- 深色模式 `dark:` 变体
- Carbon 图标集 `i-carbon-*`
- 按钮语义色：绿=启动、红=停止、蓝=连接
- 日志面板：`bg-gray-950 font-mono text-sm` 终端风格
- Server 日志 `text-green-400`，Client 日志 `text-blue-400`
- 全局隐藏滚动条
