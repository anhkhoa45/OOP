<template>
  <div class="container">
    <h1>List game</h1>
    <div v-if="isEmpty">Game List Empty</div>
    <ul v-else>
      <li v-for="game in games">
        <div>{{ game.id }}</div>
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
        }),
        isEmpty() {
          if (this.games.length == 0)
            return true;
          return false;
        }
      },
      methods : {
        joinGame(gameId) {
          this.socketClient.send(JSON.stringify({
            action: Action.JOIN_GAME,
            content: {
              game_id: gameId
            }
          }));
          this.$router.push({name: 'attackGame'})
        }
      }
    }
</script>
