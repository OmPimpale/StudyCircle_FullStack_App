import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss(),],
  server: {
    allowedHosts: [
      'localhost:5173',
      'https://afdc-2409-40c2-1150-633e-8e2-b59b-d91a-8e62.ngrok-free.app', // Add your ngrok host
    ],
    proxy: {
      '/api': {
        target: 'https://afdc-2409-40c2-1150-633e-8e2-b59b-d91a-8e62.ngrok-free.app', // Assuming your backend runs on port 8080
        changeOrigin: true,
        secure: false,
        // Optional: If your backend doesn't expect /api in the path, uncomment and adjust the rewrite rule:
        // rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
