import { createBrowserRouter } from 'react-router'
import RootLayout from '@/layouts/root-layout/RootLayout.tsx'
import Home from '@/pages/home'
import UserList from '@/pages/user/user-list/UserList.tsx'
import LoginPage from '@/pages/login'
import PrivateRoute from '@/components/PrivateRoute/PrivateRoute.tsx'

const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/',
    element: <PrivateRoute />,
    children: [
      {
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
    ],
  },
])

export default router
