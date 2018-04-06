<template>
  <div class="row">
    <div class="col-md-8">
      <div class="row">
        <div class="col-md-12">
          <h1>Welcome screen</h1>
        </div>
      </div>
      <div class="row">
        <div class="col-md-4">
          <button type="button" class="btn btn-primary btn-lg btn-block" :disabled="socketClient === null"
                  @click="createNewGame">
            New Game
          </button>
        </div>
        <div class="col-md-4">
          <button type="button" class="btn btn-info btn-lg btn-block" :disabled="socketClient === null">
            List games
          </button>
        </div>
        <div class="col-md-4">
          <button type="button" class="btn btn-danger btn-lg btn-block" :disabled="socketClient === null">
            Random game
          </button>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <h3>Online players</h3>
      <ul id="listPlayers">
      </ul>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'

  export default {
    computed: {
      ...mapState({
        socketClient: state => state.socketClient
      })
    },
    methods: {
      createNewGame() {
        this.socketClient.send(JSON.stringify({action: Action.CREATE_NEW_GAME}));
      }
    },
    created() {
      this.$store.dispatch('connectSocket');
    }
  }
</script>
