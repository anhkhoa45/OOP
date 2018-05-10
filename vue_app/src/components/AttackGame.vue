<template>
  <div class="row">
    <div class="blurbg"></div>
    <div class="container">
      <div class="row text-center">
        <h1 class="border-text middle">Room {{ playingGame.id }}</h1>
        <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
      </div>
      <h1 class="border-text middle"><strong>Choose your spokesman &#33;</strong></h1>
      <div class="row">
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.KNIGHT.id)">
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
          <div class="card" @click.prevent="setCharacter(characters.HOT_GIRL.id)">
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
          <div class="card" @click.prevent="setCharacter(characters.MEDUSA.id)">
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
          <div class="card" @click.prevent="setCharacter(characters.DRACULA.id)">
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
          <div class="col-sm-3" style="color:white">
            <p>MASTER</p>
            <p>{{ playingGame.master.name }}</p>
            <p>{{ masterCharacter }}</p>
          </div>
          <div class="col-sm-6" v-if="alreadyChosenCharacter">
            <div class="start-button" @click.prevent="start" v-if="isMaster" :disabled="!guestIsReady">
              <div class="outer">
                <div class="height">
                  <div class="inner">START</div>
                </div>
              </div>
            </div>
            <div class="start-button" @click.prevent="ready" v-else>
              <div class="outer">
                <div class="height">
                  <div class="inner">READY</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6" v-else>
          </div>
          <div class="col-sm-3" style="color:white">
            <p>GUEST</p>
            <p>{{ playingGame.guest.name }}</p>
            <p>{{ guestCharacter }}</p>
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
  import KnightCharacter from "../classes/character/KnightCharacter";
  import MedusaCharacter from "../classes/character/MedusaCharacter";
  import DraculaCharacter from "../classes/character/DraculaCharacter";
  import HotGirlCharacter from "../classes/character/HotGirlCharacter";

  export default {
    data() {
      return {
        character: -1,
        characters: Characters,
        alreadyChosenCharacter: false
      }
    },
    watch: {
      character() {
        this.changeCharacter();
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame
      }),
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      },
      isGuest() {
        return (this.playingGame.guest) && (this.$store.state.user.name === this.playingGame.guest.name);
      },
      guestIsReady() {
        return this.playingGame.status === GameStatus.GUEST_READY;
      },
      masterCharacter(){
        if (this.playingGame.master.character){
          return this.playingGame.master.character.name;
        }
        return "";
      },
      guestCharacter(){
        if (this.playingGame.guest.character){
          return this.playingGame.guest.character.name;
        }
        return "";
      }
    },
    methods: {
      setCharacter(characterID) {
        this.character = characterID;
      },
      getCharacterName(characterID) {
          if (characterID == Characters.KNIGHT.id) return Characters.KNIGHT.name;
          if (characterID == Characters.MEDUSA.id) return Characters.MEDUSA.name;
          if (characterID == Characters.DRACULA.id) return Characters.DRACULA.name;
          if (characterID == Characters.HOT_GIRL.id) return Characters.HOT_GIRL.name;
        return "";
      },
      changeCharacter() {
        this.alreadyChosenCharacter = true;
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
        }));
        if (this.playingGame.status === GameStatus.GUEST_READY) {
          this.$router.push({name: 'attackGameFight'});
        }
      },
      ready() {
        this.socketClient.send(JSON.stringify({
          action: Action.GUEST_READY,
          content: {game_id: this.playingGame.id}
        }))
      },
      leaveGame() {
        this.socketClient.send(JSON.stringify({
          action: Action.LEAVE_GAME,
          content: {
            game_id: this.playingGame.id,
          }
        }));
      }
    },
    // beforeDestroy(){
    //     this.socketClient.send(JSON.stringify({
    //       action: Action.LEAVE_GAME,
    //       content: {game_id: this.playingGame.id}
    //     }))
    // },
  }
</script>
