<template>
  <div>
    <div class="row align-content-start">
      <div class="col-sm-3">
        <div class="card">
          <div class="card-body">
            <img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">
            <!--<h5 class="card-title">Master</h5>-->
            <!--<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>-->
            <!--<a href="#" class="btn btn-primary">Go somewhere</a>-->
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
            <img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">
            <!--<h5 class="card-title">Master</h5>-->
            <!--<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>-->
            <!--<a href="#" class="btn btn-primary">Go somewhere</a>-->
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
        }))
      },
      ready(){}
    }
  }
</script>
