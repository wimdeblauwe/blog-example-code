import { defineAppSetup } from '@slidev/types'

export default defineAppSetup(({ app, router }) => {
  // Load Twitter widgets script when the app is mounted
  router.afterEach(() => {
    const script = document.createElement('script')
    script.src = 'https://platform.twitter.com/widgets.js'
    script.async = true
    document.head.appendChild(script)
  })
})

// To use in the slides
// 1. Get the tweet URL
// 2. Use https://publish.twitter.com/ to generate the code
// 3. Wrap the code in a `<div class="tweet-container">` element
// 4. Remove the `<script>` from the generated code (since that is globally handled here)