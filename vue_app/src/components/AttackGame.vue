<template>
  <div class="row">
    <div class="blurbg"></div>
    <div class="container">
      <h1 class="bordertext middle"><strong>Choose your spokesman &#33;</strong></h1>
      <div class="row">
        <div class="col-sm-3">
          <div class="card" @click.prevent="character = characters.KNIGHT.id">
            <img src="../assets/img/knight.png" alt="knight" class="image knightbg">
            <div class="overlay">
              <div class="text">
                <h3>Knight</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
        <br>
        <div class="col-sm-3">
          <div class="card" @click.prevent="character = characters.HOT_GIRL.id">
            <img src="../assets/img/wizard.png" alt="wizard" class="image wizardbg">
            <div class="overlay">
              <div class="text">
                <h3>Hot girl</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="character = characters.MEDUSA.id">
            <img src="../assets/img/knight.png" alt="girl" height="50px" class="image girlbg">
            <div class="overlay">
              <div class="text">
                <h3>Medusa</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
        <br>
        <div class="col-sm-3">
          <div class="card" @click.prevent="character = characters.DRACULA.id">
            <img src="../assets/img/knight.png" alt="Avatar" class="image draculabg">
            <div class="overlay">
              <div class="text">
                <h3>Dracula</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br>
      <div class="container">
        <div class="row">
          <div class="col-sm-3">
            <p>{{ master ? master.id : "" }}</p>
            <p>{{ masterCharacterType }}</p>
          </div>
          <div class="col-sm-6">
            <div class="startbutton" @click.prevent="start" v-if="playingGame.isMaster" :disabled="!guestIsReady">
              <div class="outer">
                <div class="height">
                  <div class="inner">START</div>
                </div>
              </div>
            </div>
            <div class="startbutton" @click.prevent="ready" v-else>
              <div class="outer">
                <div class="height">
                  <div class="inner">READY</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3">
            <p>{{ guest ? guest.id : "" }}</p>
            <p>{{ guestCharacterType }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--<div>-->
  <!--<div class="row align-content-start">-->
  <!--<div class="col-sm-3">-->
  <!--<div class="card">-->
  <!--<div class="card-body">-->
  <!--<h5>Master</h5>-->
  <!--<img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">-->
  <!--<p>{{ master ? master.id : "" }}</p>-->
  <!--<p>{{ masterCharacterType }}</p>-->
  <!--</div>-->
  <!--</div>-->
  <!--</div>-->
  <!--<div class="col-sm-6">-->
  <!--<label for="slcCharacter">Choose your character</label>-->
  <!--<select v-model="character" id="slcCharacter" :disabled="!guest.id">-->
  <!--<option value="-1">Character</option>-->
  <!--<option v-for="character in characters"-->
  <!--:value="character.id">-->
  <!--{{ character.name }}-->
  <!--</option>-->
  <!--</select>-->

  <!--<button type="button" @click="start" v-if="playingGame.isMaster" :disabled="!guestIsReady">Start</button>-->
  <!--<button type="button" @click="ready" v-else :disabled="character === -1">Ready</button>-->
  <!--</div>-->
  <!--<div class="col-sm-3">-->
  <!--<div class="card">-->
  <!--<div class="card-body">-->
  <!--<h5>Guest</h5>-->
  <!--<img class="img-fluid rounded-circle" src="../assets/img/50x50.svg" alt="">-->
  <!--<p>{{ guest ? guest.id : "" }}</p>-->
  <!--<p>{{ guestCharacterType }}</p>-->
  <!--<p v-show="guestIsReady">Ready!</p>-->
  <!--</div>-->
  <!--</div>-->
  <!--</div>-->
  <!--</div>-->
  <!--</div>-->
</template>

<script>
  import {mapState} from 'vuex'
  import Characters from '../helper/game_characters'
  import Action from '../helper/game_actions'
  import GameStatus from '../helper/game_status'
  import KnightCharacter from "../classes/character/KnightCharacter";
  import MedusaCharacter from "../classes/character/MedusaCharacter";
  import DraculaCharacter from "../classes/character/DraculaCharacter";
  import HotGirlCharacter from "../classes/character/HotGirlCharacter";

  export default {
    data() {
      return {
        character: -1,
        characters: Characters
      }
    },
    watch: {
      character() {
        this.changeCharacter();
      },
      guest() {
        if (!this.guest) {
          this.character = -1;
        }
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
      guestIsReady() {
        return this.playingGame.status === GameStatus.GUEST_READY;
      }
    },
    methods: {
      getCharacterName(character) {
        if (character) {
          if (character instanceof KnightCharacter) return Characters.KNIGHT.name;
          if (character instanceof MedusaCharacter) return Characters.MEDUSA.name;
          if (character instanceof DraculaCharacter) return Characters.DRACULA.name;
          if (character instanceof HotGirlCharacter) return Characters.HOT_GIRL.name;
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
      start() {
        this.socketClient.send(JSON.stringify({
          action: Action.START_GAME,
          content: {game_id: this.playingGame.id}
        }))
        if (this.playingGame.status === GameStatus.GUEST_READY) {
          this.$router.push({name : 'attackGameFight'});
        }
      },
      ready() {
        this.socketClient.send(JSON.stringify({
          action: Action.GUEST_READY,
          content: {game_id: this.playingGame.id}
        }))
      },
    },
    // beforeDestroy(){
    //     this.socketClient.send(JSON.stringify({
    //       action: Action.LEAVE_GAME,
    //       content: {game_id: this.playingGame.id}
    //     }))
    // },
  }
</script>
