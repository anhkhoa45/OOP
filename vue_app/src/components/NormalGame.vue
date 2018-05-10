<template>
  <div class="container">
    <div>
      <p v-if="isMaster" v-for="answer in master.answers">
        <span class="mr-10">{{ answer.word }}</span>
        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
        <span v-else class="negative-state">{{ answer.score }}</span>
      </p>
      <p v-if="!isMaster" v-for="answer in guest.answers">
        <span class="mr-10">{{ answer.word }}</span>
        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
        <span v-else class="negative-state">{{ answer.score }}</span>
      </p>
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
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import GameStatus from '../helper/game_status'

  export default {
    data() {
      return {
        answer: ''
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame
      }),
      master() {
        return this.playingGame.master.character;
      },
      guest() {
        return this.playingGame.guest.character;
      },
      canAnswer(){
        return this.playingGame.status !== GameStatus.GAME_OVER;
      },
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      }
    },
    methods: {
      sendAnswer() {
        if (!this.answer) return;

        this.socketClient.send(JSON.stringify({
          action: Action.ANSWER,
          content: {
            answer: this.answer,
            game_id: this.playingGame.id
          }
        }));
        this.answer = '';
      }
    }
  }
</script>
