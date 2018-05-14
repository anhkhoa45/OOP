<template>
  <div class="game-list">
    <div v-if="showGameDetail">
      <div class="modal fade show game-detail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
           style="display: block; padding-right: 17px;">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Game {{ showingGame.id }}</h5>
            </div>
            <div class="modal-body">
              <div class="row attack-game-box">
                <div class="col-md-12">
                  <div class="row play-area">
                    <div class="col-md-12 text-center"><p>Topic: {{ showingGame.topic }}</p></div>
                    <div class="col-md-6 answer-box left-box text-left">
                      <h3>{{ showingGame.master.name }}</h3>
                      <hr>
                      <p v-for="answer in showingGame.master_character.answers">
                        <span class="mr-10">{{ answer.word }}</span>
                        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
                        <span v-else class="negative-state">{{ answer.score }}</span>
                      </p>
                    </div>
                    <div class="col-md-6 answer-box text-right">
                      <h3>{{ showingGame.guest.name }}</h3>
                      <hr>
                      <p v-for="answer in showingGame.guest_character.answers">
                        <span class="mr-10">{{ answer.word }}</span>
                        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
                        <span v-else class="negative-state">{{ answer.score }}</span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click.prevent="close">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container text-center margin-top-50">
      <div class="row">
        <div class="col-md-12">
          <div class="row">
            <h1>
              <strong class="ml-auto">PLAYED GAMES</strong>
            </h1>
            <button type="button" class="btn btn-danger ml-auto" @click="back">Back</button>
          </div>
          <div class="row scrollable">
            <a v-for="game in playedGames" @click.prevent="viewGameInfo(game)"
               :class="{push_button: true, blue: game.mode === 'NORMAL', red: game.mode === 'ATTACK', reset:true}">
              <h4><b>Room {{ game.id }}</b></h4>
              <p>Mode: {{ game.mode }}</p>
              <p><small>Topic: {{ game.topic.length > 6 ? game.topic.substring(0,6) + '...' : game.topic}}</small></p>
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
    data() {
      return {
        showGameDetail: false,
        showingGame: null
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playedGames: state => state.playedGames
      })
    },
    methods: {
      getListGame() {
        this.socketClient.send(JSON.stringify({action: Action.GET_PLAYED_GAMES}));
      },
      viewGameInfo(game) {
        this.showingGame = game;
        this.showGameDetail = true;
      },
      close() {
        this.showGameDetail = false;
        this.showingGame = null;
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
