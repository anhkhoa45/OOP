<template>
  <div>
    <div class="row align-content-start">
      <div class="col-sm-3">
        <div class="card">
          <div class="card-body">
            <h5>Master</h5>
            <p>{{ playingGame.isMaster ? playingGame.me.name : playingGame.rival.name }}</p>
            <img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <label for="slcCharacter">Choose your character</label>
        <select v-model="character" id="slcCharacter">
          <option v-for="character in characters"
                  :value="character.id">
            {{ character.name }}
          </option>
        </select>

        <button type="button" @click="start" v-if="playingGame.isMaster">Start</button>
        <button type="button" @click="ready" v-else>Ready</button>
      </div>
      <div class="col-sm-3">
        <div class="card">
          <div class="card-body">
            <h5>Guest</h5>
            <p>{{ playingGame.isMaster ? playingGame.rival.name : playingGame.me.name }}</p>
            <img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Characters from '../helper/game_characters'
  import Action from '../helper/game_actions'

  export default {
    data(){
      return {
        character: 0,
        characters: Characters
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
      })
    },
    methods: {
      start(){
        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_CHARACTER,
          content: {
            game_id: this.playingGame.id,
            character: this.character
          }
        }));
      },
      ready(){
        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_CHARACTER,
          content: {
            game_id: this.playingGame.id,
            character: this.character
          }
        }));
      }
    }
  }
</script>
