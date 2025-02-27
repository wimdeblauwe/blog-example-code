import {defineConfig} from 'vite';
import path from 'path';
import springBoot from '@wim.deblauwe/vite-plugin-spring-boot';
import react from "@vitejs/plugin-react";

export default defineConfig({
    plugins: [
        springBoot(),
        react()
    ],
    root: path.join(__dirname, './src/main/resources'),
    build: {
        manifest: true,
        rollupOptions: {
            input: [
                '/static/css/application.css',
                '/static/react/ListExample.tsx',
                '/static/react/FlowbiteExample.tsx',
                '/static/react/ButtonBar.tsx',
                '/static/react/OccurrencesTimeline.tsx'
            ]
        },
        outDir: path.join(__dirname, `./target/classes/static`),
        copyPublicDir: false,
        emptyOutDir: true
    },
    server: {
        proxy: {
            // Proxy all backend requests to Spring Boot except for static assets
            '^/(?!static|assets|@|.*\\.(js|css|png|svg|jpg|jpeg|gif|ico|woff|woff2)$)': {
                target: 'http://localhost:8080',  // Proxy to Spring Boot backend
                changeOrigin: true,
                secure: false
            }
        },
        watch: {
            ignored: ['target/**']
        }
    }
});
