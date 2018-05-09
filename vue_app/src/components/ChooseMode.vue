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
              <li v-for="user in onlineusers">
                <img src="https://scontent.fhan3-3.fna.fbcdn.net/v/t1.0-9/12717928_735444449925317_690561526535870400_n.jpg?_nc_cat=0&oh=188f6e38bcd098efc2f2a17e4543e8d6&oe=5B5D5D18" class="thumbnail">
                <span class="whitetext">{{user.name}}</span>
                <!-- <input type="text" v-model="onlineUserName" :value="user.name"> -->
                <button @click="invite(user.name)"><a href="#" class="action-button shadow animate green">Invite</a></button>
              </li>
              <!-- <li>
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
              </li> -->
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
          <select v-model="mode">
            <option value="-1" selected="selected" disabled="disabled">Select a mode</option>
            <option value="0" >Normal mode</option>
            <option value="1">Attack mode</option>
          </select>
          <img src="../assets/img/vs.png" alt="vs" class="versus">
          <div class="startbutton" @click.prevent="done" aria-disabled="true">
            <div class="outer">
              <div class="height">
                <div class="inner">START</div>
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
  var intervalObj;
  export default {
    data() {
      return {
        category: 0,
        mode: -1,
        gameModes: Mode,
        onlineUserName: '',
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        onlineusers: state => state.onlineusers,
      })
    },
    methods: {
      done() {
        if (this.mode === -1) return;

        this.socketClient.send(JSON.stringify({
          action: Action.SET_GAME_MODE,
          content: {
            game_id: this.playingGame.id,
            mode: this.mode
          }
        }));
      },
      invite(userName){
        this.socketClient.send(JSON.stringify({
          action: Action.INVITE,
          content: {
            game_id: this.playingGame.id,
            user_name: userName,
          }
        }));
      }
    },
   
    created() {
      intervalObj=setInterval(() => {
        this.socketClient.send(JSON.stringify({
          action: Action.GET_ONLINE_USERS,
        }));
      }, 5000);
    },
    beforeDestroy(){
      clearInterval(intervalObj);
    }
  }
</script>
