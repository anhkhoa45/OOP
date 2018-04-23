<template>
  <div class="row attack-game-box">
    <div :class="{ 'blacked-out' : playingGame.me.isBlackout }"></div>
    <div class="col-md-6 offset-md-3">
      <div class="row">
        <div class="col-md-6 answer-box left-box">
          <h1>Master</h1>
          <p><strong>Health: </strong>{{ master.health }}</p>
          <p><strong>Attack: </strong>{{ master.attack }}</p>
          <hr>
          <p v-for="answer in master.answers">
            <span class="mr-10">{{ answer.answer }}</span>
            <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
            <span v-else class="negative-state">{{ answer.score }}</span>
          </p>
        </div>
        <div class="col-md-6 answer-box">
          <h1>Guest</h1>
          <p><strong>Health: </strong>{{ guest.health }}</p>
          <p><strong>Attack: </strong>{{ guest.attack }}</p>
          <hr>
          <p v-for="answer in guest.answers">
            <span class="mr-10">{{ answer.answer }}</span>
            <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
            <span v-else class="negative-state">{{ answer.score }}</span>
          </p>
        </div>
      </div>
      <div class="row margin-top-50">
        <div class="col-md-9">
          <input class="form-control" type="text" placeholder="Your answer"
                 v-model="answer" @keyup.enter="sendAnswer" :disabled="!canAnswer">
        </div>
        <div class="col-md-2">
          <button class="btn btn-primary" type="button"
                  @click="sendAnswer" :disabled="!canAnswer">
            Send
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import GameStatus from '../helper/game_status'

  export default {
    data() {
      return {
        answer: '',
        gameInterval: null
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
      }),
      master() {
        return this.playingGame.isMaster ? this.playingGame.me : this.playingGame.rival;
      },
      guest() {
        return this.playingGame.isMaster ? this.playingGame.rival : this.playingGame.me;
      },
      canAnswer(){
        return this.playingGame.status !== GameStatus.GAME_OVER && !this.playingGame.me.isStunned;
      }
    },
    methods: {
      sendAnswer() {
        if(!this.answer) return;

        this.socketClient.send(JSON.stringify({
          action: Action.ANSWER_QUESTION,
          content: {
            answer: this.answer,
            game_id: this.playingGame.id
          }
        }));
        this.answer = '';
      },
      updateGameState() {
        this.socketClient.send(JSON.stringify({
          action: Action.GET_GAME_STATE,
          content: {
            game_id: this.playingGame.id
          }
        }));

        if(this.playingGame.status === GameStatus.GAME_OVER){
          this.$router.push({name: 'attackGameResult'})
        }
      }
    },
    created() {
      this.gameInterval = setInterval(() => {
        this.updateGameState();
      }, 50);
    },
    beforeDestroy() {
      clearInterval(this.gameInterval);
    }
  }
</script>
