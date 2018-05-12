<template>
  <div class="text-center game-list">
    <div class="blur-bg"></div>
    <div class="container margin-top-50">
      <div class="row">
        <div class="col-md-12">
          <div class="row">
            <h1 class="white-text">
              <strong class="ml-auto">ROOM LIST</strong>
            </h1>
            <button type="button" class="btn btn-danger ml-auto" @click="back">Back</button>
          </div>
          <!--<div class="row">-->
            <!--<input type="checkbox"><span class="big-text">Normal mode</span>-->
            <!--<input type="checkbox"><span class="big-text"><em class="fire">At</em><em class="fire" style="animation-delay: .2s; -webkit-animation-delay: .2s; -moz-animation-delay: .2s; -ms-animation-delay: .2s;">ta</em><em class="fire" style="animation-delay: .4s; -webkit-animation-delay: .4s; -moz-animation-delay: .4s; -ms-animation-delay: .4s;">ck </em><em class="fire" style="animation-delay: .6s; -webkit-animation-delay: .6s; -moz-animation-delay: .6s; -ms-animation-delay: .6s;">mo</em><em class="fire" style="animation-delay: 1s; -webkit-animation-delay: 1s; -moz-animation-delay: 1s; -ms-animation-delay: 1s;">de</em></span>-->
          <!--</div>-->
          <div class=" row scrollable">
            <a v-for="game in games" @click.prevent="joinGame(game.id)"
               :class="{push_button: true, blue: game.mode === 'NORMAL', red: game.mode === 'ATTACK', reset:true}">
              <h4><b>Room {{ game.id }}</b></h4>
              <img :src="`./assets/img/avatar/${game.master.avatar}.png`" class="thumbnail">{{game.master.name}}
              <p>Mode: {{ game.mode }}</p>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'

  export default {
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games
      }),
      isEmpty() {
        return this.games.length === 0;
      }
    },
    methods: {
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
      },
      back() {
        this.$store.commit('setCurrentComponent', 'game-lobby');
      }
    },
    created() {
      this.getListGame();
    }
  }
</script>
