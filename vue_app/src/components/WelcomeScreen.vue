<template>
  <div class="welcome-screen row align-items-center">
    <div class="col-md-8 offset-md-2 text-center">
      <h1 class="margin-top-50">WordSmith</h1>
      <input class="max-width-150 form-control d-inline margin-top-50" v-model="userName" placeholder="Enter your name">
      <br>
      <button class="btn btn-lg btn-danger margin-top-10" v-on:click="connectSocket">Press to start</button>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        userName: ''
      }
    },
    methods: {
      connectSocket() {
        this.$store.dispatch('connectSocket', this.userName)
          .then(()=> {
            this.$router.push({name: 'gameLobby'});
          });
      }
    }
  }
</script>
