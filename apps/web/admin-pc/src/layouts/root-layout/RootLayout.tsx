import * as React from 'react'
import { Link, Outlet, useNavigate, useNavigation } from 'react-router'
import { UserOutlined, LogoutOutlined } from '@ant-design/icons'
import { Dropdown, Avatar } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import type { RootState } from '@/store'
import { logout } from '@/store/authSlice'
import Loading from '@/components/loading/Loading.tsx'
import type { MenuProps } from 'antd'

const RootLayout: React.FC = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const navigation = useNavigation()
  const isGlobalLoading = navigation.state === 'loading'
  const username = useSelector((state: RootState) => state.auth.username)

  const handleLogout = () => {
    dispatch(logout())
    navigate('/login')
  }

  const menuItems: MenuProps['items'] = [
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: handleLogout,
    },
  ]

  return (
    <div className="min-h-screen bg-gray-50">
      {/* 导航栏 */}
      <header className="bg-white shadow-sm sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-8 py-4 flex items-center justify-between">
          <div>
            <Link
              to="/"
              className="no-underline text-2xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-500 to-purple-600"
            >
              FSS 管理后台
            </Link>
          </div>
          <nav className="flex items-center gap-6">
            <Link
              to="/"
              className="no-underline text-gray-600 hover:text-blue-600 font-medium transition-colors"
            >
              首页
            </Link>
            <Link
              to="/user-list"
              className="no-underline text-gray-600 hover:text-blue-600 font-medium transition-colors"
            >
              用户列表
            </Link>
            <Dropdown
              menu={{ items: menuItems }}
              placement="bottomRight"
            >
              <div className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded-lg transition-colors">
                <Avatar
                  icon={<UserOutlined />}
                  className="bg-blue-500"
                />
                <span className="text-gray-700 font-medium">
                  {username || '用户'}
                </span>
              </div>
            </Dropdown>
          </nav>
        </div>
      </header>
      <main className="max-w-7xl mx-auto px-8 py-8">
        {isGlobalLoading ? <Loading /> : <Outlet />}
      </main>
      <footer className="bg-white border-t border-gray-200 mt-12 py-6 text-center text-gray-500">
        <div className="max-w-7xl mx-auto px-8">
          <p>© 2025 FSS (Full-Stack Scaffolding) 管理后台</p>
        </div>
      </footer>
    </div>
  )
}
export default RootLayout
