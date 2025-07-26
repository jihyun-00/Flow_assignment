import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    {/* App 전체를 StrictMode로 감싸 개발 중 문제를 조기에 해결 */}
    <App />
  </StrictMode>,
)
