<template>
  <div class="row">
    <div class="blur-bg"></div>
    <div class="container margin-top-50">
      <div v-if="isDeclined">
        <!-- Modal -->
        <div class="modal fade show" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
             style="display: block; padding-right: 17px;">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Oops!!!</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <!-- <span aria-hidden="true">&times;</span> -->
                </button>
              </div>
              <div class="modal-body">
                Your invitation has been declined!
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" @click="$store.state.isDeclined = false">OK</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3 friend-list">
          <template v-if="isMaster">
            <h2 class="border-text">Online users</h2>
            <ul class="margin-top-50">
              <li v-for="user in onlineUsers">
                <img :src="`./assets/img/avatar/${user.avatar}.png`" class="thumbnail">
                <span class="white-text">{{user.name}}</span>
                <a @click.prevent="invite(user.name)" class="action-button shadow animate green">
                Invite
                </a>
              </li>
            </ul>
          </template>
        </div>
        <div class="col-md-9">
          <div class="row text-center">
            <h1 class="border-text">Room {{ playingGame.id }}</h1>
            <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
          </div>
          <div class="row">
            <div class="col-md-4">
              <div class="card">
                <img :src="masterAvatar" alt="Avatar" class="ava">
              </div>
              <br><br><br>
              <h2 class="border-text">{{ playingGame.master.name }}</h2>
              <p class="border-text">Master</p>
            </div>
            <div class="col-md-4">
              <select class="select-box" v-if="isMaster" v-model="mode">
                <option value="-1" selected="selected" disabled="disabled">Select a mode</option>
                <option :value="0">Normal mode</option>
                <option :value="1">Attack mode</option>
              </select>
              <h3 class="border-text margin-top-50" v-else>Game mode: {{ playingGameMode }}</h3>
              <img src="../assets/img/vs.png" alt="vs" class="versus">

              <h2 v-if="isMaster && !isSelectedMode" class="border-text margin-top-50">Please choose mode first!</h2>

              <div :class="{ 'start-button' : true, 'active' : canStart}"
                   v-if="isMaster && isSelectedMode"
                   @click.prevent="done">
                <div class="outer">
                  <div class="height">
                    <div class="inner">{{ canStart ? 'START' : 'WAITING' }}</div>
                  </div>
                </div>
              </div>

              <div :class="{ 'start-button' : true, 'active' : canReady}"
                   v-if="isGuest"
                   @click.prevent="ready">
                <div class="outer">
                  <div class="height">
                    <div class="inner">{{ canReady ? 'READY' : 'WAITING' }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card">
                <img :src="guestAvatar" alt="Avatar" class="ava">
              </div>
              <br><br><br>
              <h2 class="border-text">{{ playingGame.guest ? playingGame.guest.name : '' }}</h2>
              <p class="border-text">Guest</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import Mode from '../helper/game_modes'
  import GameStatus from '../helper/game_status'

  let intervalObj;

  export default {
    data() {
      return {
        category: 0,
        mode: -1,
        gameModes: Mode,
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        onlineUsers: state => state.onlineUsers,
        isDeclined: state => state.isDeclined,
        user: state => state.user,
      }),
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      },
      isGuest() {
        return (this.playingGame.guest) && (this.$store.state.user.name === this.playingGame.guest.name);
      },
      masterAvatar(){
        return `./assets/img/avatar/${this.playingGame.master.avatar}.png`;
      },
      guestAvatar(){
        return this.playingGame.guest ? `./assets/img/avatar/${this.playingGame.guest.avatar}.png` : `./assets/img/avatar/default.png`;
      },
      playingGameMode() {
        return this.playingGame.mode || '';
      },
      guestReady() {
        return this.playingGame.status === GameStatus.GUEST_READY;
      },
      isSelectedMode() {
        return this.playingGame.mode === Mode.ATTACK || this.playingGame.mode === Mode.NORMAL
      },
      canStart() {
        return this.guestReady;
      },
      canReady() {
        return this.isSelectedMode;
      },
    },
    watch: {
      mode() {
        this.setGameMode();
      }
    },
    methods: {
      setGameMode() {
        if (this.mode === -1) return;

        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_MODE,
          content: {
            game_id: this.playingGame.id,
            mode: this.mode
          }
        }));
      },
      done() {
        if (!this.canStart) return;
        this.socketClient.send(JSON.stringify({
          action: Action.DONE_CHOOSE_MODE,
          content: {
            game_id: this.playingGame.id
          }
        }));

      },
      ready() {
        if (!this.canReady) return;

        this.socketClient.send(JSON.stringify({
          action: Action.GUEST_READY,
          content: {game_id: this.playingGame.id}
        }))
      },
      invite(userName) {
        this.socketClient.send(JSON.stringify({
          action: Action.INVITE,
          content: {
            game_id: this.playingGame.id,
            user_name: userName,
          }
        }));
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
    created() {
      this.socketClient.send(JSON.stringify({
        action: Action.GET_ONLINE_USERS,
      }));

      intervalObj = setInterval(() => {
        this.socketClient.send(JSON.stringify({
          action: Action.GET_ONLINE_USERS,
        }));
      }, 5000);
    },
    beforeDestroy() {
      clearInterval(intervalObj);
    }
  }
</script>
