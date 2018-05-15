<template>
  <div class="row">
    <div class="container">
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
            <div class="col-md-12">
            <h2 class="margin-top-30">Online users</h2>
            </div>
            <div class="input-group col-md-12">
              <input placeholder="Search user" class="form-control form-control-sm py-2 border-right-0 border" type="search"
                     v-model="searchUser">
            </div>
            <div class="col-md-12">
            <ul class="margin-top-50">
              <li v-for="user in filteredUsers">
                <div class="row">
                  <div class="col-md-8">
                    <img :src="`./assets/img/avatar/${user.avatar}.png`" class="thumbnail">
                    <span>{{user.name}}</span>
                  </div>
                  <div class="col-md-4">
                    <a @click.prevent="invite(user.name)" class="action-button shadow animate green">
                    Invite
                    </a>
                  </div>
                </div>
              </li>
            </ul>
            </div>
          </template>
        </div>
        <div class="col-md-9">
          <div class="row text-center">
            <h1>Room {{ playingGame.id }}</h1>
            <button type="button" class="btn btn-sm btn-danger ml-auto" @click="leaveGame">Leave game</button>
          </div>
          <div class="row margin-top-30 text-center">
            <div class="col-md-4">
                <img :src="masterAvatar" alt="Avatar" class="ava">
              <br><br>
              <h2>{{ playingGame.master.name }}</h2>
              <p>Master</p>
            </div>
            <div class="col-md-4">
              <select class="select-box" v-if="isMaster" v-model="mode">
                <option value="-1" selected="selected" disabled="disabled">Select a mode</option>
                <option :value="0">Normal mode</option>
                <option :value="1">Attack mode</option>
              </select>
              <h3 v-else>Game mode: <strong>{{ playingGameMode }}</strong></h3>
              <img src="../assets/img/vs.png" alt="vs" class="versus">

              <h2 v-if="isMaster && !isSelectedMode" class="margin-top-10">Please choose mode first!</h2>

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
                <img :src="guestAvatar" alt="Avatar" class="ava" style="margin-right: auto;">
              <br><br>
              <h2>{{ playingGame.guest ? playingGame.guest.name : '' }}</h2>
              <p>Guest</p>
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
        searchUser: ''
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
      filteredUsers(){
        return this.onlineUsers.filter(user => user.name.toLowerCase().startsWith(this.searchUser.toLowerCase()));
      }
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

      if(this.isMaster){
        intervalObj = setInterval(() => {
          this.socketClient.send(JSON.stringify({
            action: Action.GET_ONLINE_USERS,
          }));
        }, 2000);
      }
    },
    beforeDestroy() {
      clearInterval(intervalObj);
    }
  }
</script>
