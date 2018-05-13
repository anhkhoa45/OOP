<template>
  <div>
    <div class="row attack-game-box">
      <div class="col-md-12">
        <div id="content"></div>
      </div>
      <div class="col-md-6 offset-md-3">
        <div class="row play-area margin-top-50">
          <div class="col-md-6 answer-box left-box">
            <div class="health-bar" :style="{width: me.character.maxHealth}">
              <div class="bar text-center" :style="{width: me.character.health / me.character.maxHealth * 100 + '%'}">
                {{ me.character.health }}
                <div class="hit"></div>
              </div>
            </div>
            <p><strong>Attack: </strong>{{ me.character.attack }}</p>
            <p v-if="me.character.isPowered">
              <strong class="text-danger">Powered!</strong>
            </p>
            <hr>
            <p v-for="answer in me.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
          <div class="col-md-6 answer-box">
            <div class="health-bar" :style="{width: rival.character.maxHealth}">
              <div class="bar text-center"
                   :style="{width: rival.character.health / rival.character.maxHealth * 100 + '%'}">
                {{ rival.character.health }}
                <div class="hit"></div>
              </div>
            </div>
            <p><strong>Attack: </strong>{{ rival.character.attack }}</p>
            <p v-if="rival.character.isPowered">
              <strong class="text-danger">Powered!</strong>
            </p>
            <hr>
            <p v-for="answer in rival.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
        </div>
        <div class="row margin-top-50 text-center">
          <div class="col-md-12 text-center">
            <button type="button" class="btn btn-primary" @click="toGameLobby">Return to game lobby</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
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
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      }
    },
    methods: {
      toGameLobby(){
        this.$store.commit('setCurrentComponent', 'game-lobby');
        this.$store.commit('setPlayingGame', null);
      }
    }
  }
</script>
