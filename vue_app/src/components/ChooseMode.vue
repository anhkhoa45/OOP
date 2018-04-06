<template>
  <div>
    <form>
      <label for="gameModeOpt">Game mode</label>
      <select id="gameModeOpt" class="form-control" v-model="mode">
        <option :value="gameModes.NORMAL">Normal</option>
        <option :value="gameModes.ATTACK">Attack</option>
      </select>
      <label for="categoryOpt">Category</label>
      <select id="categoryOpt" class="form-control" v-model="category">
        <option value="0">Animal</option>
        <option value="1">Social</option>
        <option value="2">...</option>
      </select>
      <button type="button" class="btn btn-primary" @click.prevent="done">
        Done
      </button>
    </form>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import Mode from '../helper/game_modes'

  export default {
    data(){
      return {
        mode: 0,
        category: 0,
        gameModes: Mode
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
      })
    },
    methods: {
      done(){
        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_MODE,
          content: {
            game_id: this.playingGame.id,
            mode: this.mode
          }
        }));
      }
    }
  }
</script>
