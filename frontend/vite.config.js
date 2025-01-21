import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    include: ['src/**/*.test.jsx', 'src/**/*.test.js'],
    coverage: {
      provider: 'v8', // Use 'c8' or 'v8' for coverage
      reporter: ['text', 'lcov'],
    },
  },
});
