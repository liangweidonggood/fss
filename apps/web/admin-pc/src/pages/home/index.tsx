import * as React from 'react'
import { useSelector } from 'react-redux'
import {
  UserOutlined,
  TeamOutlined,
  TrophyOutlined,
  RocketOutlined,
} from '@ant-design/icons'
import { Card, Row, Col } from 'antd'
import type { RootState } from '@/store'

const HomePage: React.FC = () => {
  const username = useSelector((state: RootState) => state.auth.username)

  const features = [
    {
      icon: <UserOutlined className="text-4xl text-blue-500" />,
      title: '用户管理',
      description: '高效管理用户信息，支持增删改查操作',
      bg: 'bg-blue-50',
    },
    {
      icon: <TeamOutlined className="text-4xl text-purple-500" />,
      title: '团队协作',
      description: '支持多用户协同工作，提升团队效率',
      bg: 'bg-purple-50',
    },
    {
      icon: <TrophyOutlined className="text-4xl text-amber-500" />,
      title: '权限控制',
      description: '细粒度的权限管理，保障数据安全',
      bg: 'bg-amber-50',
    },
    {
      icon: <RocketOutlined className="text-4xl text-indigo-500" />,
      title: '极速体验',
      description: '基于现代化技术栈，提供流畅体验',
      bg: 'bg-indigo-50',
    },
  ]

  return (
    <div className="min-h-screen">
      {/* 欢迎横幅 */}
      <div className="bg-gradient-to-r from-blue-500 via-purple-500 to-indigo-600 text-white py-12 px-8 mb-8">
        <div className="max-w-7xl mx-auto">
          <h1 className="text-4xl font-bold mb-4">
            欢迎回来, {username || '用户'}!
          </h1>
          <p className="text-xl text-blue-100">
            Full-Stack Scaffolding 管理后台
          </p>
        </div>
      </div>

      {/* 功能卡片 */}
      <div className="max-w-7xl mx-auto px-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">核心功能</h2>
        <Row gutter={[24, 24]}>
          {features.map((feature, index) => () => (
            <Col
              xs={24}
              sm={12}
              lg={6}
              key={index}
            >
              <Card
                className={`h-full hover:shadow-lg transition-shadow ${feature.bg}`}
                bordered={false}
              >
                <div className="text-center">
                  <div className="mb-4">{feature.icon}</div>
                  <h3 className="text-xl font-semibold text-gray-800 mb-3">
                    {feature.title}
                  </h3>
                  <p className="text-gray-600">{feature.description}</p>
                </div>
              </Card>
            </Col>
          ))}
        </Row>
      </div>

      {/* 快速统计 */}
      <div className="max-w-7xl mx-auto px-8 mt-12">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">数据概览</h2>
        <Row gutter={[24, 24]}>
          <Col
            xs={24}
            sm={12}
            lg={8}
          >
            <Card
              className="bg-gradient-to-br from-blue-50 to-blue-100"
              bordered={false}
            >
              <div className="text-center py-6">
                <div className="text-4xl font-bold text-blue-600 mb-2">
                  1,234
                </div>
                <div className="text-gray-600">总用户数</div>
              </div>
            </Card>
          </Col>
          <Col
            xs={24}
            sm={12}
            lg={8}
          >
            <Card
              className="bg-gradient-to-br from-purple-50 to-purple-100"
              bordered={false}
            >
              <div className="text-center py-6">
                <div className="text-4xl font-bold text-purple-600 mb-2">
                  567
                </div>
                <div className="text-gray-600">今日访问</div>
              </div>
            </Card>
          </Col>
          <Col
            xs={24}
            sm={12}
            lg={8}
          >
            <Card
              className="bg-gradient-to-br from-indigo-50 to-indigo-100"
              bordered={false}
            >
              <div className="text-center py-6">
                <div className="text-4xl font-bold text-indigo-600 mb-2">
                  89
                </div>
                <div className="text-gray-600">活跃会话</div>
              </div>
            </Card>
          </Col>
        </Row>
      </div>
    </div>
  )
}

export default HomePage
