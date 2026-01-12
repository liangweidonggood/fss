import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import 'virtual:uno.css'
import './App.css' // Ensure CSS is imported

const GlobalStyles = () => (
  <style>{`
    ::-webkit-scrollbar {
      width: 0 !important;
      height: 0 !important;
      display: none !important;
      background: transparent !important;
    }
    html, body {
      margin: 0 !important;
      padding: 0 !important;
      width: 100vw !important;
      height: 100vh !important;
      overflow: hidden !important; /* Disable global scrolling */
      overscroll-behavior: none !important; /* Disable pull-to-refresh on touch devices */
    }
    #root {
      width: 100% !important;
      height: 100% !important;
      scrollbar-width: none !important;
      -ms-overflow-style: none !important;
    }
  `}</style>
)

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <GlobalStyles />
    <App />
  </React.StrictMode>,
)
