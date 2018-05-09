<template>
  <div class="row text-center">
    <div class="blurbg"></div>
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div>
            <br>
            <h2 class="whitetext">Friend list</h2>
            <ul>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
                <a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li>
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">ina mo</span>
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
          <h2 class="bordertext">put name player 1</h2>
        </div>
        <div class="col-md-3">
          <select v-model="mode" v-if="isMaster()">
            <option value="-1" selected="selected" disabled="disabled">Select a mode</option>
            <option value="0">Normal mode</option>
            <option value="1">Attack mode</option>
          </select>
          <img src="../assets/img/vs.png" alt="vs" class="versus">
          <div class="startbutton" @click.prevent="done" aria-disabled="true">
            <div class="outer">
              <div class="height">
                <div class="inner">{{ buttonContent }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="card">
            <img
              src="data:image/png;base64,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"
              alt="Avatar" class="ava">
          </div>
          <br><br><br>
          <h2 class="bordertext">put name player 2</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'
  import Mode from '../helper/game_modes'

  export default {
    data() {
      return {
        category: 0,
        mode: -1,
        gameModes: Mode,
        buttonContent : "CREATE"
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame
      })
    },
    methods: {
      done() {
        this.buttonContent = "WAITING";
        if (this.mode === -1) return;

        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_MODE,
          content: {
            game_id: this.playingGame.id,
            mode: this.mode
          }
        }));
      },
      isMaster() {
        return this.playingGame.isMaster;
      },
      alreadySetGameMode() {
        return this.playingGame.mode != null;
      },
      hadRival() {
        return this.playingGame.rival != null;
      },
      isInitial() {
        return this.playingGame.status = GameStatus.INITIAL;
      },
      setButtonContent() {
        if (!this.isMaster()) {
          this.buttonContent = "READY";
        }
      }
    },
    created() {
      this.setButtonContent();
    }
  }
</script>
