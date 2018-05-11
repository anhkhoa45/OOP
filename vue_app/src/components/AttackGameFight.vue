<template>
  <div class="row attack-game-box">
    <div :class="{ 'blacked-out' : me.isBlackout }"></div>
    <div class="col-md-6 offset-md-3">
      <div class="row">
        <div class="col-md-12 text-center">
          Time left: {{ playingGame.timeLeft }}
        </div>
        <div class="col-md-12 text-center">
          Topic: {{ playingGame.topic }}
        </div>
        <div class="col-md-6 answer-box left-box">
          <h1>Master</h1>
          <p><strong>Health: </strong>{{ playingGame.master.character.health }}</p>
          <p><strong>Attack: </strong>{{ playingGame.master.character.attack }}</p>
          <hr>
          <p v-for="answer in playingGame.master.character.answers">
            <span class="mr-10">{{ answer.word }}</span>
            <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
            <span v-else class="negative-state">{{ answer.score }}</span>
          </p>
        </div>
        <div class="col-md-6 answer-box">
          <h1>Guest</h1>
          <p><strong>Health: </strong>{{ playingGame.guest.character.health }}</p>
          <p><strong>Attack: </strong>{{ playingGame.guest.character.attack }}</p>
          <hr>
          <p v-for="answer in playingGame.guest.character.answers">
            <span class="mr-10">{{ answer.word }}</span>
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
        gameInterval: null,
        gameScreen: null
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        user: state => state.user,
      }),
      me() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.master : this.playingGame.guest;
      },
      rival() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.guest : this.playingGame.master;
      },
      canAnswer(){
        return this.playingGame.status !== GameStatus.GAME_OVER && !this.me.character.isStunned;
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
