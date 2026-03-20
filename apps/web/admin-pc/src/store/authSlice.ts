import { createSlice, type PayloadAction } from '@reduxjs/toolkit'

interface AuthState {
  isLoggedIn: boolean
  username?: string
}

const initialState: AuthState = {
  isLoggedIn: false,
  username: undefined,
}

// 从 localStorage 初始化状态
const loadInitialState = (): AuthState => {
  try {
    const saved = localStorage.getItem('auth_state')
    if (saved) {
      return JSON.parse(saved)
    }
  } catch {
    // ignore
  }
  return initialState
}

const authSlice = createSlice({
  name: 'auth',
  initialState: loadInitialState(),
  reducers: {
    login: (state, action: PayloadAction<{ username: string }>) => {
      state.isLoggedIn = true
      state.username = action.payload.username
      // 持久化
      localStorage.setItem('auth_state', JSON.stringify(state))
    },
    logout: (state) => {
      state.isLoggedIn = false
      state.username = undefined
      localStorage.removeItem('auth_state')
    },
  },
})

export const { login, logout } = authSlice.actions
export default authSlice.reducer
