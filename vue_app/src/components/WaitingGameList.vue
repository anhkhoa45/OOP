<template>
  <div class="container">
    <h1>List game</h1>
    <ul>
      <li v-for="game in games">
        <div>{{ game }}</div>
        <button>
          <a href="#" @click.prevent="joinGame">JOIN</a>
        </button>
      </li>
    </ul>
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
        })
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
        this.$store.dispatch('connectSocket', this.getListGame);
      }
    }
</script>
