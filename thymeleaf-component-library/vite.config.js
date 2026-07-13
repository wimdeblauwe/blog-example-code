import {defineConfig} from 'vite';
import path from 'path';

export default defineConfig({
  root: __dirname,
  server: {
    port: 5174,
    strictPort: true,
    cors: true,
    origin: 'http://localhost:5174'
  },
  build: {
    manifest: true,
    rolldownOptions: {
      input: {
        'tcl-css': path.join(__dirname, 'src/main/resources/static/css/tcl.css'),
        'tcl-js': path.join(__dirname, 'src/main/resources/static/js/tcl.js'),
      }
    },
    outDir: path.join(__dirname, 'target/classes/META-INF/resources/tcl'),
    copyPublicDir: false
  },
});