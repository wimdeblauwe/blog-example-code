<template>
  <footer
      v-if="$slidev.nav.currentLayout !== 'cover'
      && $slidev.nav.currentLayout !== 'center'
      && $slidev.nav.currentPage !== $slidev.nav.total"
      class="absolute bottom-0 right-1 p-2"
  >
    <div class="flex items-center text-gray-600" >
      <div>Wim Deblauwe</div>
    </div>
  </footer>
</template>
<script setup lang="ts">
</script>