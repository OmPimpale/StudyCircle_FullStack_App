import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss(),],
  server: {
    allowedHosts: ['9259-2409-40c2-2052-b709-64f7-8a2f-92fa-2dbf.ngrok-free.app'],
  },
})
