<template>
  <div class="welcome-screen row align-items-center">
    <div class="col-md-8 offset-md-2 text-center">
      <h1 class="margin-top-50">WordSmith</h1>
      <input class="max-width-150 form-control d-inline margin-top-50" placeholder="Enter your name"
             v-model="userName" @keyup.enter.prevent="connectSocket">
      <br>
      <p class="text-danger" v-for="e in error">
        {{ e.message }}
      </p>
      <button type="button" class="btn btn-lg btn-danger margin-top-10" @click="connectSocket">
        Press to start
      </button>
    </div>
  </div>
</template>

<script>
  import User from "../classes/User";

  export default {
    data() {
      return {
        userName: '',
        error: []
      }
    },
    watch: {
      userName(){
        if(this.error.length !== 0) this.error = [];
      }
    },
    methods: {
      connectSocket() {
        if(this.userName.includes(' ')) {
          this.error.push({message: 'Username should not contain spaces!'});
          return;
        }

        this.$store.dispatch('connectSocket', this.userName)
          .then(()=> {
            this.$store.commit('saveUser', new User(this.userName));
            this.$store.commit('setCurrentComponent', 'game-lobby');
            // this.$router.push({name: 'gameLobby'});
          });
      }
    }
  }
</script>
