/** @type {import('tailwindcss').Config} */
const flowbite = require("flowbite-react/tailwind");

export default {
  content: ['./src/main/resources/templates/**/*.html',
  './src/main/resources/static/react/**',
  flowbite.content()],
  theme: {
    extend: {},
  },
  plugins: [flowbite.plugin()],
}

