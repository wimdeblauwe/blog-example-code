module.exports = {
  content: ["./src/main/resources/**/*.{html,js}"],
  safelist: [
    'underline'
  ],
  theme: {
    extend: {},
    container: {
      center: true,
    }
  },
  plugins: [
    require('@tailwindcss/forms')
  ]
}
