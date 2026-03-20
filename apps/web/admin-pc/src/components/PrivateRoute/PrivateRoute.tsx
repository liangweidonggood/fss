import * as React from 'react'
import { Navigate, Outlet } from 'react-router'
import { useSelector } from 'react-redux'
import type { RootState } from '@/store'

/**
 * 私有路由守卫
 * 未登录用户访问会自动跳转到登录页
 */
const PrivateRoute: React.FC = () => {
  const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn)

  if (!isLoggedIn) {
    return <Navigate to="/login" replace />
  }

  return <Outlet />
}

export default PrivateRoute
