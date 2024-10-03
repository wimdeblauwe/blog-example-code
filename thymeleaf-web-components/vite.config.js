import {defineConfig} from 'vite';
import path from 'path';
import springBoot from '@wim.deblauwe/vite-plugin-spring-boot';

export default defineConfig({
    plugins: [
        springBoot(),
    ],
    root: path.join(__dirname, './src/main/resources'),
    build: {
        manifest: true,
        rollupOptions: {
            input: [
                '/static/css/application.css'
            ]
        },
        outDir: path.join(__dirname, `./target/classes/static`),
        copyPublicDir: false,
        emptyOutDir: true
    }
});
