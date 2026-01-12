interface SidebarProps {
  activeTab: string
  onTabChange: (tab: string) => void
}

export function Sidebar({ activeTab, onTabChange }: SidebarProps) {
  const menuItems = [
    { id: 'tcp-server', label: 'TCP Server', icon: 'i-carbon-network-4' }, // Using UnoCSS icon preset
    { id: 'tcp-client', label: 'TCP Client', icon: 'i-carbon-network-2' },
    { id: 'settings', label: 'Settings', icon: 'i-carbon-settings' },
  ]

  return (
    <div className="w-64 bg-gray-100 dark:bg-gray-800 h-full flex flex-col border-r border-gray-200 dark:border-gray-700">
      <div className="p-4 border-b border-gray-200 dark:border-gray-700 font-bold text-xl text-gray-700 dark:text-gray-200">
        Toolbox
      </div>
      <div className="flex-1 p-2 gap-1 flex flex-col">
        {menuItems.map((item) => (
          <button
            key={item.id}
            onClick={() => onTabChange(item.id)}
            className={`
              flex items-center gap-3 px-4 py-3 rounded-lg text-left transition-colors
              ${
                activeTab === item.id
                  ? 'bg-blue-500 text-white shadow-md'
                  : 'hover:bg-gray-200 dark:hover:bg-gray-700 text-gray-700 dark:text-gray-300'
              }
            `}
          >
            <div className={`${item.icon} text-xl`} />
            <span>{item.label}</span>
          </button>
        ))}
      </div>
    </div>
  )
}
