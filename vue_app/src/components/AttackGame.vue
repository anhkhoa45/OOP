<template>
  <div>
    <div v-if="showHelp">
      <div class="modal fade show" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
           style="display: block; padding-right: 17px; max-height: 550px;overflow-y: auto">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Special Abilities</h5>
            </div>
            <div class="modal-body">
              <div class="row attack-game-box">
                <div class="col-md-12">
                  <h2>Knight</h2>
                  <p><strong>Power:</strong> the more correct word u guess && opponent cannot guess right, u get +2, +4,
                    +8,... on your atk</p>
                  <hr>

                  <h2>Wizard</h2>
                  <p><strong>Guard:</strong> if the opponent guess your words, he'll be stun in 5s</p>
                  <p><strong>Power:</strong> turn the opponent into stone for 10 sec, they cannot answer.</p>
                  <hr>

                  <h2>Hot girl</h2>
                  <p><strong>Guard:</strong> suck 3 health on attack, evasion rate 10%</p>
                  <p><strong>Power:</strong> blind the opponent</p>
                  <hr>

                  <h2>Archer</h2>
                  <p><strong>Guard:</strong> +2 when take damage from opponent attack</p>
                  <p><strong>Power:</strong> burn opponent 10 health every 5 sec.</p>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="showHelp = false">Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="container">
        <div class="row text-center">
          <h2 class="text-left">Room {{ playingGame.id }}</h2>
          <button type="button" class="btn btn-sm btn-info ml-auto" @click="showHelp = true">Help</button>
          <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
        </div>
        <h3 class="text-center">Choose your spokesman &#33;</h3>
        <div class="row margin-top-30">
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
                  <h4>def: evade life-steal</h4>
                  <h4>ss: Night shade</h4>
                  <h4>"Pretty and nice, devil by night"</h4>
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
                  <h4>"Cold and clever, put you in the freezer &#33;"</h4>
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
                  <h4>def: anger</h4>
                  <h4>ss: burn</h4>
                  <h4>"Hit me twice, you&#39;ll pay the price &#33;"</h4>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="container">
          <div class="row">
            <div class="col-sm-3 margin-top-10">
              <h4 class="text-center">MASTER</h4>
              <h4 class="text-left">{{ playingGame.master.name }}</h4>
              <h3 class="text-right">{{ masterCharacter }}</h3>
            </div>
            <div class="col-sm-6">
              <div class="start-button active" @click.prevent="start" v-if="isMaster && canStart">
                <div class="outer">
                  <div class="height">
                    <div class="inner">START</div>
                  </div>
                </div>
              </div>
              <h2 v-if="isMaster && !canStart" class="margin-top-50 text-center">
                Waiting ...
              </h2>
              <div class="start-button active" @click.prevent="ready" v-if="isGuest && canReady">
                <div class="outer">
                  <div class="height">
                    <div class="inner">READY</div>
                  </div>
                </div>
              </div>
              <h2 v-if="isGuest && !canStart" class="margin-top-50 text-center">
                Waiting ...
              </h2>
            </div>
            <div class="col-sm-3 margin-top-10">
              <h4 class="text-center">GUEST</h4>
              <h4 class="text-right">{{ playingGame.guest.name }}</h4>
              <h3 class="text-left">{{ guestCharacter }}</h3>
            </div>
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

  export default {
    data() {
      return {
        character: -1,
        characters: Characters,
        alreadyChosenCharacter: false,
        showHelp: false
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
        return this.playingGame.status === GameStatus.GUEST_READY
          && this.playingGame.guest != null
          && this.playingGame.guest.character != null
          && this.playingGame.master.character != null;
      },
      canReady() {
        return this.playingGame.guest.character != null;
      },
      masterCharacter() {
        if (this.playingGame.master.character) {
          return this.playingGame.master.character.name;
        }
        return "";
      },
      guestCharacter() {
        if (this.playingGame.guest.character) {
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
