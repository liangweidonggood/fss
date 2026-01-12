import { useContext } from 'react'
import { LogContext } from '../context/LogContext'
export function useLogs() {
  const context = useContext(LogContext)
  if (context === undefined) {
    throw new Error('useLogs must be used within a LogProvider')
  }
  return context
}
