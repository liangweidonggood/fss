import { useState } from 'react'
import { Sidebar } from './components/Sidebar'
import { TcpServer } from './components/TcpServer'
import { TcpClient } from './components/TcpClient'
import { LogProvider } from './context/LogContext'
// import "./App.css"; // Disable default styles to let UnoCSS take over

function App() {
  const [activeTab, setActiveTab] = useState('tcp-server')

  return (
    <LogProvider>
      <div className="flex h-screen w-screen overflow-hidden bg-white text-gray-900 dark:bg-gray-900 dark:text-white font-sans">
        <Sidebar
          activeTab={activeTab}
          onTabChange={setActiveTab}
        />

        <div className="flex-1 h-full overflow-hidden flex flex-col">
          <div
            className={
              activeTab === 'tcp-server' ? 'h-full flex flex-col' : 'hidden'
            }
          >
            <TcpServer />
          </div>
          <div
            className={
              activeTab === 'tcp-client' ? 'h-full flex flex-col' : 'hidden'
            }
          >
            <TcpClient />
          </div>
          {activeTab === 'settings' && (
            <div className="flex-1 flex items-center justify-center text-gray-500">
              <div className="text-center">
                <div className="text-4xl mb-2 i-carbon-settings" />
                <p>Settings placeholder</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </LogProvider>
  )
}

export default App
