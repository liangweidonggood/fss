import { createBrowserRouter } from 'react-router'
import Home from '@/pages/Home'

const router = createBrowserRouter([
  {
    path: '/',
    element: <Home />,
  },
  // 可以在这里添加更多路由配置
])
export default router
