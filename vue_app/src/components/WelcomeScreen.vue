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
          <button type="button" class="btn btn-info btn-lg btn-block" :disabled="socketClient === null"
                  @click="showListGame = !showListGame">
            List games
          </button>
          <ul v-show="showListGame">
            <li v-for="game in games">
              <a href="#" @click.prevent="joinGame(game.id)">
              {{ game.id }} - {{ game.guest ? '2/2' : '1/2' }}
              </a>
            </li>
          </ul>
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
  var interval_obj;
  export default {
    data(){
      return {
        showListGame: false
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games
      })
    },
    methods: {
      createNewGame() {
        this.socketClient.send(JSON.stringify({action: Action.CREATE_NEW_GAME}));
      },
      getListGame(){        
        interval_obj = setInterval(() => {
    		this.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));		
    		}, 3000);
      },
      joinGame(gameId){
        this.socketClient.send(JSON.stringify({
          action: Action.JOIN_GAME,
          content: {
            game_id: gameId
          }
        }));
      }
    },
    created() {
      this.$store.dispatch('connectSocket', this.getListGame);
    },
    beforeDestroy(){
    	clearInterval(interval_obj);
    }
  }
</script>
