<template>
  <div id="gameCanvas"></div>
</template>

<script>
  import Game from '../classes/game/attack'

  export default {
    data() {
      return {
        msg: 'Welcome to Your Vue.js App',
      }
    },
    created() {
      window.game = new Game()
    },
    destroyed() {
      window.game.destroy()
    },
    methods: {},
  }
</script>
