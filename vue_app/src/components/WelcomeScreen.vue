<template>
  <div class="welcome-screen row align-items-center">
    <div class="col-md-8 offset-md-2 text-center">
      <h1 class="margin-top-50">WordSmith</h1>
      <button class="btn btn-lg btn-danger margin-top-50" v-on:click="seen=!seen">Press to start</button>
      <p v-if="seen">
        <input v-model="message" placeholder="Enter your name">
        <button @click="toGameLobby">OK</button>
        <p>Message is: {{ message }}</p>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        seen: false,
        message: '',
      }
    },
    methods:{
      toGameLobby(){
        this.$store.dispatch('connectSocket', this.message);
        this.$router.push({name: 'gameLobby'})
      }
    }
  }
</script>
