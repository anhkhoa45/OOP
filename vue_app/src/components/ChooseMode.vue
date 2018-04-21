<template>
  <div class="row align-items-center">
    <div class="col-md-3">
      <h3>Friends</h3>
      <div class="media margin-top-10">
        <img class="mr-3 img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="Generic placeholder image">
        <div class="media-body">
          <h5 class="mt-0">User 1</h5>
          Playing game
        </div>
      </div>
      <div class="media margin-top-10">
        <img class="mr-3 img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="Generic placeholder image">
        <div class="media-body">
          <h5 class="mt-0">User 2</h5>
          Online
        </div>
      </div>
    </div>
    <div class="col-md-6 offset-md-1 text-center">
      <h3>Game mode</h3>
      <div class="btn-group d-flex justify-content-between" role="group">
        <button class="btn-game-mode btn btn-primary" type="button" @click="done(gameModes.NORMAL)">Normal</button>
        <button class="btn-game-mode btn btn-danger" type="button" @click="done(gameModes.ATTACK)">Attack</button>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import Mode from '../helper/game_modes'

  export default {
    data() {
      return {
        category: 0,
        gameModes: Mode
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        user: state => state.user,
      })
    },
    methods: {
      done(mode) {
        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_MODE,
          content: {
            game_id: this.playingGame.id,
            user_id: this.user.id,
            mode: this.mode
          }
        }));
      }
    }
  }
</script>
