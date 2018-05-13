<template>
  <div class="row">
    <div class="container">
      <div class="row text-center">
        <h1 class="text-left">Room {{ playingGame.id }}</h1>
        <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
      </div>
      <h1 class="text-center">Choose your spokesman &#33;</h1>
      <div class="row">
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.KNIGHT.id)">
            <img src="../assets/img/knight.png" alt="Knight" class="image knightbg">
            <div class="overlay">
              <div class="text">
                <h3>Knight</h3>
                <h4>Health: 120</h4>
                <h4>atk: 12</h4>
                <h4>ss: WomboCombo</h4>
                <h4>"Don't be shock, i'm as hard as rock."</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.HOT_GIRL.id)">
            <img src="../assets/img/girl.png" alt="girl" class="image girlbg">
            <div class="overlay">
              <div class="text">
                <h3>Hot girl</h3>
                <h4>Health: 100</h4>
                <h4>atk: 6</h4>
                <h4>def: anger</h4>
                <h4>ss: burn</h4>
                <h4>"Hit me twice, you&#39;ll pay the price &#33;"</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.WIZARD.id)">
            <img src="../assets/img/wizard.png" alt="wizard" class="image wizardbg">
            <div class="overlay">
              <div class="text">
                <h3>Wizard</h3>
                <h4>Health: 80</h4>
                <h4>atk: 10</h4>
                <h4>def: stun</h4>
                <h4>ss: Freeze</h4>
                <h4>"Cold and clever, there's no one else better &#33;"</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="card" @click.prevent="setCharacter(characters.ARCHER.id)">
            <img src="../assets/img/archer.png" alt="archer" class="image archerbg">
            <div class="overlay">
              <div class="text">
                <h3>archer</h3>
                <h4>Health: 100</h4>
                <h4>atk: 6</h4>
                <h4>def: life drain</h4>
                <h4>ss: Night shade</h4>
                <h4>What do you mean&#63; You can't see the screen&#63;</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row">
          <div class="col-sm-3 border-text margin-top-10">
            <h4>MASTER</h4>
            <h4>{{ playingGame.master.name }}</h4>
            <h3>{{ masterCharacter }}</h3>
          </div>
          <div class="col-sm-6">
            <div class="start-button active" @click.prevent="start" v-if="isMaster && canStart">
              <div class="outer">
                <div class="height">
                  <div class="inner">START</div>
                </div>
              </div>
            </div>
            <h2 v-if="isMaster && !canStart" class="border-text margin-top-50 text-center">
              Waiting ...
            </h2>
            <div class="start-button active" @click.prevent="ready" v-if="isGuest && canReady">
              <div class="outer">
                <div class="height">
                  <div class="inner">READY</div>
                </div>
              </div>
            </div>
            <h2 v-if="isGuest && !canStart" class="border-text margin-top-50 text-center">
              Waiting ...
            </h2>
          </div>
          <div class="col-sm-3 border-text margin-top-10">
            <h4>GUEST</h4>
            <h4>{{ playingGame.guest.name }}</h4>
            <h3>{{ guestCharacter }}</h3>
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
