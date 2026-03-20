// apps/web/admin-pc/src/pages/login/index.tsx
import * as React from 'react'
import { useState, useEffect } from 'react'
import { useNavigate, Navigate } from 'react-router'
import { UserOutlined, LockOutlined, ReloadOutlined } from '@ant-design/icons'
import { Form, Input, Button, Checkbox, Card, message } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import type { RootState } from '@/store'
import { login } from '@/store/authSlice'
import { generateCaptchaText, generateCaptchaImage } from '@/utils/captcha'

interface LoginFormValues {
  username: string
  password: string
  captcha?: string
  remember?: boolean
}

const LoginPage: React.FC = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn)

  // 状态
  const [showCaptcha, setShowCaptcha] = useState(false)
  const [captchaText, setCaptchaText] = useState('')
  const [captchaImage, setCaptchaImage] = useState(() => {
    const text = generateCaptchaText(4)
    setCaptchaText(text)
    return generateCaptchaImage(text)
  })
  const [loading, setLoading] = useState(false)
  const [form] = Form.useForm<LoginFormValues>()

  // 刷新验证码
  const refreshCaptcha = () => {
    const text = generateCaptchaText(4)
    const image = generateCaptchaImage(text)
    setCaptchaText(text)
    setCaptchaImage(image)
  }

  // 从存储恢复用户名
  useEffect(() => {
    const savedUsername =
      localStorage.getItem('remembered_username') ||
      sessionStorage.getItem('remembered_username')
    if (savedUsername) {
      form.setFieldValue('username', savedUsername)
    }
  }, [form])

  // 默认账号密码
  const DEFAULT_USER = 'admin'
  const DEFAULT_PASSWORD = '123'

  // 表单提交
  const handleFinish = async (values: LoginFormValues) => {
    setLoading(true)

    // 模拟异步登录请求
    await new Promise((resolve) => setTimeout(resolve, 800))

    // 如果需要验证码但校验失败
    if (
      showCaptcha &&
      values.captcha?.toLowerCase() !== captchaText.toLowerCase()
    ) {
      message.error('验证码错误，请重新输入')
      refreshCaptcha()
      setLoading(false)
      return
    }

    // 验证用户名密码
    const isValid =
      values.username === DEFAULT_USER &&
      values.password === DEFAULT_PASSWORD

    if (isValid) {
      // 登录成功
      // 处理记住我
      if (form.getFieldValue('remember')) {
        localStorage.setItem('remembered_username', values.username)
      } else {
        localStorage.removeItem('remembered_username')
        sessionStorage.setItem('remembered_username', values.username)
      }

      // 更新登录状态
      dispatch(login({ username: values.username }))
      message.success('登录成功')
      navigate('/', { replace: true })
    } else {
      // 登录失败，显示验证码
      if (!showCaptcha) {
        setShowCaptcha(true)
      }
      refreshCaptcha()
      message.error('用户名或密码错误')
      setLoading(false)
    }
  }

  // 已登录跳转到首页
  if (isLoggedIn) {
    return (
      <Navigate
        to="/"
        replace
      />
    )
  }

  return (
    <div className="min-h-screen flex w-full">
      {/* 左侧品牌区域 */}
      <div className="hidden lg:flex lg:w-[60%] bg-gradient-to-br from-blue-500 via-purple-500 to-indigo-600 flex-col justify-center items-center text-white">
        <div className="text-center px-8">
          <h1 className="text-5xl font-bold mb-6 tracking-tight">
            FSS 管理后台
          </h1>
          <p className="text-xl text-blue-100">Full-Stack Scaffolding</p>
          <p className="text-lg text-blue-100 mt-2">现代化全栈开发脚手架</p>
        </div>
      </div>

      {/* 右侧登录表单区域 */}
      <div className="w-full lg:w-[40%] flex items-center justify-center bg-white px-4 py-8">
        <Card
          className="w-full max-w-md shadow-none border-0"
          title={
            <div className="text-center pb-4">
              <h2 className="text-3xl font-bold text-gray-800 m-0">欢迎登录</h2>
              <p className="text-gray-500 mt-2">请输入您的账号信息</p>
            </div>
          }
        >
          <Form
            form={form}
            name="login"
            onFinish={handleFinish}
            size="large"
            autoComplete="on"
            layout="vertical"
          >
            <Form.Item
              name="username"
              rules={[{ required: true, message: '请输入用户名' }]}
            >
              <Input
                prefix={<UserOutlined className="text-gray-400" />}
                placeholder="用户名"
              />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{ required: true, message: '请输入密码' }]}
            >
              <Input.Password
                prefix={<LockOutlined className="text-gray-400" />}
                placeholder="密码"
              />
            </Form.Item>

            {showCaptcha && (
              <div className="flex gap-3">
                <Form.Item
                  name="captcha"
                  rules={[{ required: true, message: '请输入验证码' }]}
                  className="flex-1 mb-4"
                >
                  <Input
                    placeholder="图形验证码"
                    maxLength={4}
                  />
                </Form.Item>
                <div className="w-[120px] h-[40px]">
                  <img
                    src={captchaImage}
                    alt="验证码"
                    onClick={refreshCaptcha}
                    className="w-full h-full cursor-pointer rounded border border-gray-200 object-cover"
                    title="点击刷新"
                  />
                </div>
                <Button
                  icon={<ReloadOutlined />}
                  onClick={refreshCaptcha}
                  className="h-10"
                />
              </div>
            )}

            <div className="flex justify-between items-center mb-6">
              <Form.Item
                name="remember"
                valuePropName="checked"
                noStyle
              >
                <Checkbox>记住我</Checkbox>
              </Form.Item>
              <a
                className="text-blue-600 hover:text-blue-500 text-sm"
                href="#"
              >
                忘记密码?
              </a>
            </div>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                block
                size="large"
              >
                登录
              </Button>
            </Form.Item>

            {/* 移动端显示品牌名称 */}
            <div className="text-center mt-6 lg:hidden">
              <p className="text-gray-400 text-sm">FSS 管理后台</p>
            </div>
          </Form>
        </Card>
      </div>
    </div>
  )
}

export default LoginPage
