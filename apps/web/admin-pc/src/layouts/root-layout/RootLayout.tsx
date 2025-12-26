import * as React from 'react'
import { Link, Outlet, useNavigation } from 'react-router'
import Loading from '@/components/loading/Loading.tsx'

const RootLayout: React.FC = () => {
  const navigation = useNavigation()
  const isGlobalLoading = navigation.state === 'loading'
  return (
    <div className="max-w-7xl mx-auto">
      {/* 导航栏：内边距 + 底部边框 + 底部外边距 */}
      <header className="py-5 border-b border-gray-200 mb-5">
        <h1 className="text-2xl font-bold m-0">用户管理系统</h1>
        <nav className="mt-3">
          <Link
            to="/"
            className="mr-6 no-underline text-gray-800 hover:text-blue-600"
          >
            首页
          </Link>
          <Link
            to="/user-list"
            className="mr-6 no-underline text-gray-800 hover:text-blue-600"
          >
            用户列表
          </Link>
          <Link
            to="/user-add"
            className="no-underline text-gray-800 hover:text-blue-600"
          >
            新增用户
          </Link>
        </nav>
      </header>
      <main className="min-h-[400px]">
        {isGlobalLoading ? <Loading /> : <Outlet />}
      </main>
      <footer className="mt-10 py-5 text-center border-t border-gray-200 text-gray-500">
        © 2025 React Router v7 + UnoCSS 工程化示例
      </footer>
    </div>
  )
}
export default RootLayout
