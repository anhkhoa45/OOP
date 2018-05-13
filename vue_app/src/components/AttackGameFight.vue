<template>
    <div class="container margin-top-10 attack-game-box">
      <div class="row" id="content"></div>
      <div class="row">
        <div :class="{ 'blacked-out' : me.isBlackout }"></div>
        <div class="col-md-5">
          <h3>{{ me.name }}</h3>
            <div class="health-bar" :style="{width: me.character.maxHealth}">
            <div class="bar text-center" :style="{width: me.character.health / me.character.maxHealth * 100 + '%'}">
            {{ me.character.health }}
            <div class="hit"></div>
            </div>
          </div>
          <p v-if="me.character.isPowered">
            <strong class="text-danger">Powered &#33;</strong>
          </p>
        </div>
        <div class="col-md-2">
          <h1 class="text-center margin-top-10" style="margin-bottom: 0">{{ playingGame.timeLeft }}</h1>
        </div>
        <div class="col-md-5">
          <h3 class="text-right">{{ rival.name }}</h3>
            <div class="health-bar" :style="{width: rival.character.maxHealth}">
              <div class="bar text-center" :style="{width: rival.character.health / rival.character.maxHealth * 100 + '%'}">
              {{ rival.character.health }}
              </div>
            </div>
            <p v-if="rival.character.isPowered">
            <strong class="text-danger">Powered &#33;</strong>
          </p>
        </div>
      </div>
      <div class="row margin-top-10">
        <div class="col-md-4">
          <h2>99 (point)</h2>
        </div>
        <div class="col-md-4 text-center">
          <h2>Topic: {{ playingGame.topic }}</h2>
        </div>
        <div class="col-md-4 text-right">
          <h2>88 )point</h2>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-6 play-area text-left">
          <div class="row">
            <div class="col-md-6 answer-box left-box">
              <p v-for="answer in me.character.answers">
                <span class="mr-10">{{ answer.word }}</span>
                <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
                <span v-else class="negative-state">{{ answer.score }}</span>
              </p>
            </div>
            <div class="col-md-6 answer-box">
              <p v-for="answer in rival.character.answers">
                <span class="mr-10">{{ answer.word }}</span>
                <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
                <span v-else class="negative-state">{{ answer.score }}</span>
              </p>
            </div>
          </div>
          <div class="row margin-top-30">
        <div class="col-md-7">
          <input class="form-control" type="text" placeholder="Your answer"
                 v-model="answer" @keyup.enter="sendAnswer" :disabled="!canAnswer">
        </div>
        <div class="col-md-2">
          <button class="btn btn-primary" type="button"
                  @click="sendAnswer" :disabled="!canAnswer">
            Send
          </button>
        </div>
        <div class="col-md-3">
          <button class="btn btn-danger" type="button"
                  @click="power" :disabled="!canPower">
            Power  &#33;&#33;&#33;
          </button>
        </div>


      </div>
        </div>
        <div class="col-md-3 text-right">
        </div>
      </div>
    </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import GameStatus from '../helper/game_status'

  import Phaser from 'phaser'

  export default {
    data() {
      return {
        answer: '',
        gameInterval: null,
        gameAnimation: null,
        myCharacterAnimation: null,
        rivalCharacterAnimation: null,
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
      canAnswer() {
        return this.playingGame.status !== GameStatus.GAME_OVER && !this.me.character.isStunned;
      },
      canPower() {
        return this.me.character.numBeingAttacked >= 3;
      }
    },
    watch: {
      'me.character.health': function (newVal, oldVal) {
        if(newVal === 0) {
          this.myCharacterAnimation.animations.play('die');
        } else if(newVal < oldVal) {
          let anim1 = this.rivalCharacterAnimation.animations.play('attack');
          let anim2 = this.myCharacterAnimation.animations.play('hurt');

          anim1.onComplete.add(() => { this.rivalCharacterAnimation.animations.play('idle'); }, this.gameAnimation);
          anim2.onComplete.add(() => { this.myCharacterAnimation.animations.play('idle'); }, this.gameAnimation);
        }
      },
      'rival.character.health': function(newVal, oldVal) {
        if(newVal === 0) {
          this.rivalCharacterAnimation.animations.play('die');
        } else if(newVal < oldVal) {
          let anim1 = this.myCharacterAnimation.animations.play('attack');
          let anim2 = this.rivalCharacterAnimation.animations.play('hurt');

          anim1.onComplete.add(() => { this.myCharacterAnimation.animations.play('idle'); }, this.gameAnimation);
          anim2.onComplete.add(() => { this.rivalCharacterAnimation.animations.play('idle'); }, this.gameAnimation);
        }
      },
      'playingGame.status': function(newVal) {
        if(newVal === GameStatus.GAME_OVER){
          setTimeout(() => {
            this.$store.commit('setCurrentComponent', 'attack-game-result');
          }, 2000)
        }
      }
    },
    methods: {
      sendAnswer() {
        if (!this.answer) return;

        this.socketClient.send(JSON.stringify({
          action: Action.ANSWER,
          content: {
            answer: this.answer,
            game_id: this.playingGame.id,
          }
        }));
        this.answer = '';
      },
      power() {
        if (!this.canPower) return;

        this.socketClient.send(JSON.stringify({
          action: Action.POWER,
          content: {
            game_id: this.playingGame.id
          }
        }));
      },
      updateGameState() {
        this.socketClient.send(JSON.stringify({
          action: Action.GET_GAME_STATE,
          content: {
            game_id: this.playingGame.id
          }
        }));
      },
      preload() {
        let myAtlas = this.me.character.atlas;
        let rivalAtlas = this.rival.character.atlas;
        this.gameAnimation.load.atlas(myAtlas.name, myAtlas.sprite, myAtlas.spriteScript);
        this.gameAnimation.load.atlas(rivalAtlas.name, rivalAtlas.sprite, rivalAtlas.spriteScript);
      },
      create() {
        this.myCharacterAnimation = this.gameAnimation.add.sprite(0, this.gameAnimation.height - 350, this.me.character.atlas.name, 0);
        this.myCharacterAnimation.scale.setTo(0.7, 0.7);
        this.me.character.animations.forEach(animation => {
          this.myCharacterAnimation.animations.add(
            animation.name, animation.frames, animation.interval, animation.repeat, false
          );
        });

        this.rivalCharacterAnimation = this.gameAnimation.add.sprite(this.gameAnimation.width, this.gameAnimation.height - 350, this.rival.character.atlas.name, 0);
        this.rivalCharacterAnimation.scale.setTo(-0.7, 0.7);
        this.rival.character.animations.forEach(animation => {
          this.rivalCharacterAnimation.animations.add(
            animation.name, animation.frames, animation.interval, animation.repeat, false
          );
        });

        this.myCharacterAnimation.animations.play('idle');
        this.rivalCharacterAnimation.animations.play('idle');
      },
      initGame() {
        let w = 1140;
        let h = 665;

        this.gameAnimation = new Phaser.Game(w, h, Phaser.AUTO, 'content', {preload: this.preload, create: this.create}, true);
      }
    },
    created() {
      this.gameInterval = setInterval(() => {
        this.updateGameState();
      }, 50);

      this.initGame();
    },
    beforeDestroy() {
      clearInterval(this.gameInterval);
      this.gameAnimation.destroy();
    }
  }
</script>
