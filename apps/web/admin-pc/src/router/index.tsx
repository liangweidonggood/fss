import { createBrowserRouter } from 'react-router'
import RootLayout from '@/layouts/root-layout/RootLayout.tsx'
import Home from '@/pages/home'
import UserList from '@/pages/user/user-list/UserList.tsx'

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/user-list',
        element: <UserList />,
      },
    ],
  },
  // 可以在这里添加更多路由配置
])
export default router
