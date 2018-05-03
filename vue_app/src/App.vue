<template>
  <div class="container-fluid margin-top-50">
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
