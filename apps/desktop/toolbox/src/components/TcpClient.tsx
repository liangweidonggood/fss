import { useState, useEffect, useRef } from 'react'
import { invoke } from '@tauri-apps/api/core'
import { listen } from '@tauri-apps/api/event'
import { useLogs } from '../context/LogContext'

export function TcpClient() {
  const [ip, setIp] = useState('127.0.0.1')
  const [port, setPort] = useState('8080')
  const [isConnected, setIsConnected] = useState(false)
  const { clientLogs: logs, addClientLog, clearClientLogs } = useLogs()
  const [message, setMessage] = useState('')
  const logsEndRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    // Listen for logs
    const unlistenLogPromise = listen<string>('client-log', (event) => {
      addClientLog(event.payload)
    })

    // Listen for disconnection
    const unlistenDisconnectPromise = listen(
      'client-disconnected',
      async () => {
        setIsConnected(false)
        // Ensure backend state is cleaned up so we can reconnect later
        try {
          await invoke('stop_tcp_client')
        } catch (e) {
          // Ignore error if already stopped
          console.error(e)
        }
      },
    )

    return () => {
      unlistenLogPromise.then((unlisten) => unlisten())
      unlistenDisconnectPromise.then((unlisten) => unlisten())
    }
  }, [addClientLog])

  useEffect(() => {
    // Scroll to bottom
    logsEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [logs])

  const handleConnect = async () => {
    try {
      await invoke('start_tcp_client', { ip, port: parseInt(port) })
      setIsConnected(true)
      addClientLog(`System: Connecting to ${ip}:${port}...`)
    } catch (error) {
      addClientLog(`Error: ${error}`)
    }
  }

  const handleDisconnect = async () => {
    try {
      await invoke('stop_tcp_client')
      setIsConnected(false)
      addClientLog('System: Disconnecting...')
    } catch (error) {
      addClientLog(`Error: ${error}`)
    }
  }

  const handleSend = async () => {
    if (!message) return
    try {
      await invoke('send_tcp_client_message', { message })
      addClientLog(`Sent: ${message}`)
      setMessage('')
    } catch (error) {
      addClientLog(`Error sending: ${error}`)
    }
  }

  const handleClearLogs = () => {
    clearClientLogs()
  }

  return (
    <div className="flex flex-col h-full w-full bg-white dark:bg-gray-900">
      {/* Control Panel */}
      <div className="flex items-center gap-4 p-4 border-b border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800">
        <div className="flex items-center gap-2">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-300">
            IP:
          </label>
          <input
            type="text"
            value={ip}
            onChange={(e) => setIp(e.target.value)}
            disabled={isConnected}
            className="px-3 py-1.5 border rounded-md w-32 bg-white dark:bg-gray-700 dark:border-gray-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex items-center gap-2">
          <label className="text-sm font-medium text-gray-700 dark:text-gray-300">
            Port:
          </label>
          <input
            type="number"
            value={port}
            onChange={(e) => setPort(e.target.value)}
            disabled={isConnected}
            className="px-3 py-1.5 border rounded-md w-24 bg-white dark:bg-gray-700 dark:border-gray-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex gap-2">
          {!isConnected ? (
            <button
              onClick={handleConnect}
              className="flex items-center gap-2 px-4 py-1.5 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors font-medium cursor-pointer"
            >
              <div className="i-carbon-plug-filled" />
              Connect
            </button>
          ) : (
            <button
              onClick={handleDisconnect}
              className="flex items-center gap-2 px-4 py-1.5 bg-red-600 hover:bg-red-700 text-white rounded-md transition-colors font-medium cursor-pointer"
            >
              <div className="i-carbon-plug-filled" />
              Disconnect
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
              className="text-blue-400 border-b border-gray-900/50 pb-0.5 mb-0.5 break-all"
            >
              <span className="opacity-50 mr-2">[{log.timestamp}]</span>
              {log.message}
            </div>
          ))}
          <div ref={logsEndRef} />
        </div>
      </div>

      {/* Send Message Area */}
      <div className="p-4 border-t border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 flex gap-2">
        <textarea
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter' && e.ctrlKey) {
              handleSend()
            }
          }}
          placeholder="Type a message... (Ctrl + Enter to send)"
          disabled={!isConnected}
          className="flex-1 px-3 py-2 border rounded-md bg-white dark:bg-gray-700 dark:border-gray-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 resize-none h-20"
        />
        <button
          onClick={handleSend}
          disabled={!isConnected || !message}
          className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors font-medium cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2 h-20 justify-center"
        >
          <div className="i-carbon-send-filled" />
          Send
        </button>
      </div>
    </div>
  )
}
