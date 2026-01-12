import { createContext, useState, ReactNode } from 'react'

export interface LogEntry {
  message: string
  timestamp: string
}

interface LogContextType {
  serverLogs: LogEntry[]
  clientLogs: LogEntry[]
  addServerLog: (log: string) => void
  addClientLog: (log: string) => void
  clearServerLogs: () => void
  clearClientLogs: () => void
}

export const LogContext = createContext<LogContextType | undefined>(undefined)

export function LogProvider({ children }: { children: ReactNode }) {
  const [serverLogs, setServerLogs] = useState<LogEntry[]>([])
  const [clientLogs, setClientLogs] = useState<LogEntry[]>([])

  const addServerLog = (message: string) => {
    const timestamp = new Date().toLocaleTimeString()
    setServerLogs((prev) => [...prev, { message, timestamp }])
  }

  const addClientLog = (message: string) => {
    const timestamp = new Date().toLocaleTimeString()
    setClientLogs((prev) => [...prev, { message, timestamp }])
  }

  const clearServerLogs = () => {
    setServerLogs([])
  }

  const clearClientLogs = () => {
    setClientLogs([])
  }

  return (
    <LogContext.Provider
      value={{
        serverLogs,
        clientLogs,
        addServerLog,
        addClientLog,
        clearServerLogs,
        clearClientLogs,
      }}
    >
      {children}
    </LogContext.Provider>
  )
}
