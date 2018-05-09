<template>
  <div class="container">
    <h1>List game</h1>
    <div v-if="isEmpty">Game List Empty</div>
    <div v-else>
      <div class="row" v-for="game in games">
        <div class="col-md-3" style="color : darkred">Game {{ game.id }}</div>
        <div class="col-md-3">
          <button class="btn btn-lg btn-default margin-top-10" @click="joinGame(game.id)">JOIN</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    import {mapState} from 'vuex'
    import Action from '../helper/game_actions'

    export default {

      computed : {
        ...mapState({
          socketClient: state => state.socketClient,
          games: state => state.games
        }),
        isEmpty() {
          return this.games.length === 0;
        }
      },
      methods : {
        getListGame() {
          this.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));
        },
        joinGame(gameId) {
          this.socketClient.send(JSON.stringify({
            action: Action.JOIN_GAME,
            content: {
              game_id: gameId
            }
          }));
        }
      },
      created() {
        this.getListGame();
      }
    }
</script>
