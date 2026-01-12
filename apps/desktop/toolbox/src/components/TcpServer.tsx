import { useState, useEffect, useRef } from 'react'
import { invoke } from '@tauri-apps/api/core'
import { listen } from '@tauri-apps/api/event'
import { useLogs } from '../context/LogContext'

export function TcpServer() {
  const [port, setPort] = useState('8080')
  const [isRunning, setIsRunning] = useState(false)
  const { serverLogs: logs, addServerLog, clearServerLogs } = useLogs()
  const logsEndRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    // Listen for logs
    const unlistenPromise = listen<string>('server-log', (event) => {
      addServerLog(event.payload)
    })

    return () => {
      unlistenPromise.then((unlisten) => unlisten())
    }
  }, [addServerLog])

  useEffect(() => {
    // Scroll to bottom
    logsEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [logs])

  const handleStart = async () => {
    try {
      await invoke('start_tcp_server', { port: parseInt(port) })
      setIsRunning(true)
      addServerLog(`System: Starting server on port ${port}...`)
    } catch (error) {
      addServerLog(`Error: ${error}`)
    }
  }

  const handleStop = async () => {
    try {
      await invoke('stop_tcp_server')
      setIsRunning(false)
      addServerLog('System: Stopping server...')
    } catch (error) {
      addServerLog(`Error: ${error}`)
    }
  }

  const handleClearLogs = () => {
    clearServerLogs()
  }

  return (
    <div className="flex flex-col h-full w-full bg-white dark:bg-gray-900">
      {/* Control Panel */}
      <div className="flex items-center gap-4 p-4 border-b border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800">
        <div className="flex items-center gap-2">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-300">
            Port:
          </label>
          <input
            type="number"
            value={port}
            onChange={(e) => setPort(e.target.value)}
            disabled={isRunning}
            className="px-3 py-1.5 border rounded-md w-24 bg-white dark:bg-gray-700 dark:border-gray-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex gap-2">
          {!isRunning ? (
            <button
              onClick={handleStart}
              className="flex items-center gap-2 px-4 py-1.5 bg-green-600 hover:bg-green-700 text-white rounded-md transition-colors font-medium cursor-pointer"
            >
              <div className="i-carbon-play-filled" />
              Start
            </button>
          ) : (
            <button
              onClick={handleStop}
              className="flex items-center gap-2 px-4 py-1.5 bg-red-600 hover:bg-red-700 text-white rounded-md transition-colors font-medium cursor-pointer"
            >
              <div className="i-carbon-stop-filled" />
              Stop
            </button>
          )}
        </div>
        <div className="flex-1" /> {/* Spacer */}
        <button
          onClick={handleClearLogs}
          className="flex items-center gap-2 px-3 py-1.5 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 dark:hover:bg-gray-700 transition-colors shadow-sm cursor-pointer"
          title="Clear Logs"
        >
          <div className="i-carbon-clean text-lg" />
          <span>Clear Logs</span>
        </button>
      </div>

      {/* Log Area */}
      <div
        className="flex-1 overflow-auto bg-gray-950 font-mono text-sm min-h-0 m-4 rounded-lg shadow-lg"
        style={{ scrollbarWidth: 'none', msOverflowStyle: 'none' }}
      >
        <style>{`
          .log-scroll-container::-webkit-scrollbar { display: none; }
        `}</style>
        <div
          className="log-scroll-container"
          style={{ padding: '16px' }}
        >
          {logs.length === 0 && (
            <div className="text-gray-600 italic">No logs yet...</div>
          )}
          {logs.map((log, index) => (
            <div
              key={index}
              className="text-green-400 border-b border-gray-900/50 pb-0.5 mb-0.5 break-all"
            >
              <span className="opacity-50 mr-2">[{log.timestamp}]</span>
              {log.message}
            </div>
          ))}
          <div ref={logsEndRef} />
        </div>
      </div>
    </div>
  )
}
