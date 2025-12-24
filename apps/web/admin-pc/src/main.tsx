import { createRoot } from 'react-dom/client'
import '@unocss/reset/tailwind-compat.css' // 重置样式
import 'virtual:uno.css' // UnoCSS生成的样式
import { StrictMode } from 'react'
import zhCN from 'antd/locale/zh_CN'
import { App as AppContainer, ConfigProvider } from 'antd'
import { RouterProvider } from 'react-router'
import router from '@/router'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ConfigProvider locale={zhCN}>
      <AppContainer component={false}>
        <RouterProvider router={router} />
      </AppContainer>
    </ConfigProvider>
  </StrictMode>,
)
