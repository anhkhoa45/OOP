<template>
  <div class="container margin-top-50">
    <transition name="slide-fade">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>

  export default {
    beforeDestroy() {
      this.$store.state.socketClient.close();
    }
  }
</script>
