<template>
  <div class="slidev-layout">
    <slot name="default"></slot>
    <div class='grid grid-cols-3 grid-rows-1 gap-20 mt-32'>
      <div class="flex flex-col justify-center items-center gap-4">
        <div class="text-6xl" :class="$attrs.firstIcon"/>
        <slot name="first"></slot>
      </div>
      <div v-click class="flex flex-col justify-center items-center gap-4">
        <div class="text-6xl" :class="$attrs.secondIcon"/>
        <slot name="second"></slot>
      </div>
      <div v-click class="flex flex-col justify-center items-center gap-4">
        <div class="text-6xl" :class="$attrs.thirdIcon"/>
        <slot name="third"></slot>
      </div>
    </div>
  </div>
</template>