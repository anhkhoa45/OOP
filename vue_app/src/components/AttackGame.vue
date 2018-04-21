<template>
  <div>
    <div class="row align-content-start">
      <div class="col-sm-3">
        <div class="card">
          <div class="card-body">
            <h5>Master</h5>
            <p>{{ master ? master.id : "" }}</p>
            <p>{{ masterCharacterType }}</p>
            <img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <label for="slcCharacter">Choose your character</label>
        <select v-model="character" id="slcCharacter">
          <option value="-1">Character</option>
          <option v-for="character in characters"
                  :value="character.id">
            {{ character.name }}
          </option>
        </select>

        <button type="button" @click="start" v-if="playingGame.isMaster" :disabled="!guestIsReady">Start</button>
        <button type="button" @click="ready" v-else :disabled="character === -1">Ready</button>
      </div>
      <div class="col-sm-3">
        <div class="card">
          <div class="card-body">
            <h5>Guest</h5>
            <p>{{ guest ? guest.id : "" }}</p>
            <p>{{ guestCharacterType }}</p>
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
  import GameStatus from '../helper/game_status'
  import KnightPlayer from "../classes/player/KnightPlayer";
  import MedusaPlayer from "../classes/player/MedusaPlayer";
  import DraculaPlayer from "../classes/player/DraculaPlayer";
  import HotGirlPlayer from "../classes/player/HotGirlPlayer";

  export default {
    data() {
      return {
        character: -1,
        characters: Characters
      }
    },
    watch: {
      character(){
        this.changeCharacter();
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
      }),
      master() {
        return this.playingGame.isMaster ? this.playingGame.me : this.playingGame.rival
      },
      guest() {
        return this.playingGame.isMaster ? this.playingGame.rival : this.playingGame.me
      },
      masterCharacterType() {
        return this.getCharacterName(this.master);
      },
      guestCharacterType() {
        return this.getCharacterName(this.guest);
      },
      guestIsReady(){
        return this.playingGame.status === GameStatus.GUEST_READY;
      }
    },
    methods: {
      getCharacterName(player){
        if (player) {
          if (player instanceof KnightPlayer) return Characters.KNIGHT.name;
          if (player instanceof MedusaPlayer) return Characters.MEDUSA.name;
          if (player instanceof DraculaPlayer) return Characters.DRACULA.name;
          if (player instanceof HotGirlPlayer) return Characters.HOT_GIRL.name;
        }
        return "";
      },
      changeCharacter() {
        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_CHARACTER,
          content: {
            game_id: this.playingGame.id,
            character: this.character
          }
        }));
      },
      start(){
        this.socketClient.send(JSON.stringify({
          action: Action.START_GAME,
          content: { game_id: this.playingGame.id }
        }))
      },
      ready(){
        this.socketClient.send(JSON.stringify({
          action: Action.GUEST_READY,
          content: { game_id: this.playingGame.id }
        }))
      }
    }
  }
</script>
