import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss(),],
  server: {
    allowedHosts: [
      'localhost',
      '9259-2409-40c2-2052-b709-64f7-8a2f-92fa-2dbf.ngrok-free.app', // Add your ngrok host
    ],
    proxy: {
      '/api': {
        target: 'https://9259-2409-40c2-2052-b709-64f7-8a2f-92fa-2dbf.ngrok-free.app', // Assuming your backend runs on port 8080
        changeOrigin: true,
        secure: false,
        // Optional: If your backend doesn't expect /api in the path, uncomment and adjust the rewrite rule:
        // rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
