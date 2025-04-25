import { defineConfig } from 'vite';
import { VitePluginNode } from 'vite-plugin-node';
import { resolve } from 'path';

export default defineConfig({
  server: {
    port: 3000,
  },
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
    },
  },
  plugins: [
    ...VitePluginNode({
      adapter: 'express',
      appPath: './src/app.ts',
      exportName: 'app',
      tsCompiler: 'typescript',
    }),
  ],
  optimizeDeps: {
    exclude: ['pg-native'],
  },
}); 