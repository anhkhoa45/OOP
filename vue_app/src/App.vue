<template>
  <div class="container-fluid">
      <router-view></router-view>
  </div>
</template>

<script>
  export default {
    beforeDestroy() {
      this.$store.state.socketClient.close();
    }
  }
</script>
