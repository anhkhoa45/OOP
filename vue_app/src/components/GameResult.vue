<template>
  <div>
    <div v-if="showCorrectWords">
      <div class="modal fade show game-detail" tabindex="-1" role="dialog" aria-labelledby="cwModalLabel" aria-hidden="true"
           style="display: block; padding-right: 17px; max-height: 500px;overflow-y: auto">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="cwModalLabel">List of correct words</h5>
            </div>
            <div class="modal-body">
              <div class="row attack-game-box">
                <div class="col-md-12">
                  <p v-for="(score, word) in correctWords">
                    {{ word }} : {{ score }}
                  </p>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click.prevent="showCorrectWords = false">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showResult">
      <div class="modal fade show game-detail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
           style="display: block; padding-right: 17px; max-height: 500px;overflow-y: auto">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div v-if="isWin">
            	<div class="modal-header">
	              <h5 class="modal-title" id="exampleModalLabel">Congratulations</h5>
	            </div>
	            <div class="modal-body">
	              You win!!!!
	            </div>
            </div>

            <div v-if="!isWin">
            	<div class="modal-header">
	              <h5 class="modal-title" id="exampleModalLabel">Oops!!!</h5>
	            </div>
	            <div class="modal-body">
	              You lose!
	            </div>
            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click.prevent="showResult = false">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <h1>Game result</h1>
    <div class="row attack-game-box">
      <div class="col-md-6 offset-md-3">
        <div class="row play-area margin-top-30">
          <div class="col-md-6 answer-box left-box">
            <h3>{{ me.name }}</h3>
            <div v-if="isAttackMode" class="health-bar" :style="{width: me.character.maxHealth}">
              <div class="bar text-center" :style="{width: me.character.health / me.character.maxHealth * 100 + '%'}">
                <h5>{{ me.character.health }}</h5>
                <div class="hit"></div>
              </div>
            </div>

            <p v-if="isAttackMode"><strong>Attack: </strong>{{ me.character.attack }}</p>
            <h5 style="text-decoration: underline;color: aqua;">Total score: {{myTotalScore}}</h5>
            <hr>
            <p v-for="answer in me.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
          <div class="col-md-6 answer-box">
            <h3 class="text-right">{{ rival.name }}</h3>
            <div v-if="isAttackMode" class="health-bar" :style="{width: rival.character.maxHealth}">
              <div class="bar text-center"
                   :style="{width: rival.character.health / rival.character.maxHealth * 100 + '%'}">
                <h5>{{ rival.character.health }}</h5>
                <div class="hit"></div>
              </div>
            </div>
            <p v-if="isAttackMode"><strong>Attack: </strong>{{ rival.character.attack }}</p>
            <h5 style="text-decoration: underline;color: aqua;">Total score: {{rivalTotalScore}}</h5>
            <hr>
            <p v-for="answer in rival.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
        </div>
        <div class="row margin-top-50 text-center">
          <div class="col-md-6 text-center">
            <button type="button" class="btn btn-primary" @click="toGameLobby">Return to game lobby</button>
          </div>
          <div class="col-md-6 text-center">
            <button type="button" class="btn btn-warning" @click="revealCorrectWords">Reveal correct words</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Mode from '../helper/game_modes'
  import Action from '../helper/game_actions'

  export default {
    data() {
      return {
        showCorrectWords: false,
        showResult: true,
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        user: state => state.user,
        correctWords: state => state.correctWords,
        haveCorrectWords: state => state.haveCorrectWords,
      }),
      me() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.master : this.playingGame.guest;
      },
      rival() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.guest : this.playingGame.master;
      },
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      },
      isAttackMode() {
        return this.playingGame.mode === Mode.ATTACK;
      },
      isWin() {
        return this.myTotalScore > this.rivalTotalScore;
      },
      myTotalScore() {
        return this.me.character.answers.reduce((a, c) => a + c.score, 0);
      },
      rivalTotalScore() {
        return this.rival.character.answers.reduce((a, c) => a + c.score, 0);
      },
    },
    methods: {
      toGameLobby() {
        this.socketClient.send(JSON.stringify({
          action: Action.LEAVE_GAME,
          content: {
            game_id: this.playingGame.id
          }
        }));
      },
      revealCorrectWords() {
        this.showCorrectWords = true;
        this.socketClient.send(JSON.stringify({
          action: Action.REVEAL_CORRECT_WORDS,
          content: {
            game_id: this.playingGame.id
          }
        }));
      }
    }
  }
</script>
