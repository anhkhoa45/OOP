<template>
  <div class="row text-center">
    <div class="blurbg"></div>
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div>
            <br>
            <h2 class="white-text">Friend list</h2>
            <ul>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="white-text">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card">
            <img
              src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/27867694_1566230860169928_418230993458335818_n.jpg?_nc_cat=0&oh=13d04ba97111c7989ed5c9a6d5797f58&oe=5B8D2FEC"
              alt="Avatar" class="ava">
          </div>
          <br><br><br>
          <h2 class="border-text">{{ playingGame.master.name }}</h2>
          <p class="border-text">Master</p>
        </div>
        <div class="col-md-3">
          <select v-if="isMaster" v-model="mode">
            <option value="-1" selected="selected" disabled="disabled">Select a mode</option>
            <option :value="0" >Normal mode</option>
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
        <div class="col-md-3">
          <div class="card">
            <img
              src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/31517242_2054158958156500_3197652428060355511_n.jpg?_nc_cat=0&oh=6616b19fb2acf843b8ddb549e6a47d3c&oe=5B579FC7"
              alt="Avatar" class="ava">
          </div>
          <br><br><br>
          <h2 class="border-text">{{ playingGame.guest ? playingGame.guest.name : '' }}</h2>
          <p class="border-text">Guest</p>
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

  export default {
    data() {
      return {
        category: 0,
        mode: -1,
        gameModes: Mode
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
      }),
      isMaster(){
        return this.$store.state.user.name === this.playingGame.master.name;
      },
      isGuest(){
        return (this.playingGame.guest) && (this.$store.state.user.name === this.playingGame.guest.name);
      },
      playingGameMode(){
        return this.playingGame.mode || '';
      },
      guestReady() {
        return this.playingGame.status === GameStatus.GUEST_READY;
      },
      isSelectedMode(){
        return this.playingGame.mode === Mode.ATTACK || this.playingGame.mode === Mode.NORMAL
      },
      canStart(){
        return this.guestReady;
      },
      canReady(){
        return this.isSelectedMode;
      },
    },
    watch: {
      mode() {
        this.setGameMode();
      }
    },
    methods: {
      setGameMode(){
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
        this.socketClient.send(JSON.stringify({
          action: Action.DONE_CHOOSE_MODE,
          content: {
            game_id: this.playingGame.id
          }
        }));
      },
      ready(){
        this.socketClient.send(JSON.stringify({
          action: Action.GUEST_READY,
          content: {game_id: this.playingGame.id}
        }))
      }
    }
  }
</script>
