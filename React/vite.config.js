import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Configurație pentru rularea locală + proxy către microservicii
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      '/auth': {
        target: 'http://auth.localhost',
        changeOrigin: true,
      },
      '/users': {
        target: 'http://user.localhost',
        changeOrigin: true,
      },
      '/devices': {
        target: 'http://device.localhost',
        changeOrigin: true,
      },
    },
  },
});
