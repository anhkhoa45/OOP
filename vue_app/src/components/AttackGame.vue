<template>
  <div class="row">
    <div class="blurbg"></div>
    <div class="container">
      <div class="row text-center margin-top-50">
        <h1 class="border-text middle">Room {{ playingGame.id }}</h1>
        <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
      </div>
      <h1 class="border-text middle"><strong>Choose your spokesman &#33;</strong></h1>
      <div class="row">
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.KNIGHT.id)">
            <img src="../assets/img/knight.png" alt="Knight" class="image knightbg">
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
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.ARCHER.id)">
            <img src="../assets/img/archer.png" alt="Archer" class="image draculabg">
            <div class="overlay">
              <div class="text">
                <h3>Archer</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.WIZARD.id)">
            <img src="../assets/img/wizard.png" alt="Wizard" height="50px" class="image wizardbg">
            <div class="overlay">
              <div class="text">
                <h3>Wizard</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: Combo</h4>
                <h4>Capable of getting hits</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.HOT_GIRL.id)">
            <img src="../assets/img/girl.png" alt="Hot girl" class="image girlbg">
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
      </div>
      <div class="container">
        <div class="row">
          <div class="col-sm-3 border-text">
            <p>MASTER</p>
            <p>{{ playingGame.master.name }}</p>
            <p>{{ masterCharacter }}</p>
          </div>
          <div class="col-sm-6">
            <div class="start-button active" @click.prevent="start" v-if="isMaster && canStart">
              <div class="outer">
                <div class="height">
                  <div class="inner">START</div>
                </div>
              </div>
            </div>
            <h2 v-if="isMaster && !canStart" class="border-text margin-top-50">
              Waiting ...
            </h2>
            <div class="start-button active" @click.prevent="ready" v-if="isGuest && canReady">
              <div class="outer">
                <div class="height">
                  <div class="inner">READY</div>
                </div>
              </div>
            </div>
            <h2 v-if="isGuest && !canStart" class="border-text margin-top-50">
              Waiting ...
            </h2>
          </div>
          <div class="col-sm-3 border-text">
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
        return (this.playingGame.guest != null) && (this.$store.state.user.name === this.playingGame.guest.name);
      },
      canStart() {
        return this.playingGame.guest != null
          && this.playingGame.guest.character != null
          && this.playingGame.master.character != null;
      },
      canReady() {
        return this.playingGame.guest.character != null;
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
  }
</script>
