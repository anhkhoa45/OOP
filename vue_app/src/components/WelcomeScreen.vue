<template>
  <div class="welcome-screen row align-items-center">
    <div class="col-md-8 offset-md-2 text-center">
      <h1 class="margin-top-50">WordSmith</h1>
      <button class="btn btn-lg btn-danger margin-top-50" @click="toGameLobby">Press to start</button>
    </div>
  </div>
</template>

<script>
  export default {
    methods:{
      toGameLobby(){
        this.$router.push({name: 'gameLobby'})
      }
    }
  }
</script>
