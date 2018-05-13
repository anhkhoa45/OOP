<template>
  <div class="row align-items-center">
    <div class="col-md-8 offset-md-2 text-center">
      <h1 class="margin-top-10">WordSmith</h1>
      <h3 class="margin-top-50">Choose your avatar</h3>
      <carousel :per-page="7" :space-padding="0" :loop="true">
        <slide v-for="i in 13" :key="i">
          <div class="avatar" @click.prevent="chooseAvatar(i)">
            <img :data-index="i" :src="`./assets/img/avatar/${i}.png`"
                 alt="Avatar" class="img-fluid rounded-circle">
            <div class="overlay" :style="{'opacity': avatar === i ? 1 : 0}"></div>
          </div>
        </slide>
      </carousel>
      <input type="text" class="max-width-160 form-control d-inline margin-top-30" placeholder="Enter your name"
             v-model="userName">
      <input type="password" class="max-width-160 form-control d-inline margin-top-30" placeholder="Password"
             v-model="password" @keyup.enter.prevent="connectSocket">
      <br>
      <p class="text-danger" >
        <span v-for="e in error">{{ e.message }}</span>
      </p>
      <button type="button" class="btn btn-lg btn-danger margin-top-10" @click="connectSocket">
        Press to start
      </button>
    </div>
  </div>
</template>

<script>
  import { Carousel, Slide } from 'vue-carousel';

  export default {
    data() {
      return {
        avatar: '',
        userName: '',
        password: '',
        error: []
      }
    },
    watch: {
      userName(){
        if(this.error.length !== 0) this.error = [];
      },
      avatar(){
        if(this.error.length !== 0) this.error = [];
      },
      password(){
        if(this.error.length !== 0) this.error = [];
      },
    },
    methods: {
      connectSocket: function () {
        if (this.userName.includes(' ')) {
          this.error.push({message: 'Username should not contain spaces! '});
        }
        if (this.password.includes(' ')) {
          this.error.push({message: 'Password should not contain spaces! '});
        }
        if (!this.avatar) {
          this.error.push({message: 'You must choose avatar! '});
        }
        else if (!this.userName || !this.password) {
          this.error.push({message: 'You must enter your user name and password! '});
        }
        if(this.error.length > 0) return;

        this.$store.dispatch('connectSocket', {
          userName: this.userName,
          password: this.password,
          avatar: this.avatar
        });
      },
      chooseAvatar(slideIndex){
        this.avatar = slideIndex;
      }
    },
    components: {
      Carousel,
      Slide
    }
  }
</script>
