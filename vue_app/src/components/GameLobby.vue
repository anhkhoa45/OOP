<template>
  <div>
    <div class="blurbg"></div>
    <h1 class="bordertext middle"><strong>Choose game &#33;</strong></h1>
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div>
            <br>
            <h2 class="whitetext">Friend list</h2>
            <ul>
              <li><img src="knightbig.png" class="thumbnail">Dm<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">Khoa<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">bobo<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">putang<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">ina mo<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">Dm<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">Khoa<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">bobo<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">putang<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">ina mo<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">Dm<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">Khoa<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">bobo<a href="#" class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">putang<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
              <li><img src="knightbig.png" class="thumbnail">ina mo<a href="#"
                                                                      class="action-button shadow animate green">Invite</a>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <a href="#" class="push_button red" @click.prevent="createNewGame">CREATE GAME</a>
        </div>
        <div class="col-md-3">
          <a href="#" class="push_button blue" @click.prevent="joinGame">JOIN GAME</a>
        </div>
        <div class="col-md-3">
          <a href="#" class="push_button yellow" @click.prevent="joinGame">RANDOM GAME</a>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import Action from '../helper/game_actions'

  export default {
    data() {
      return {
        showListGame: false
      }
    },
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games
      })
    },
    methods: {
      createNewGame() {
        this.socketClient.send(JSON.stringify({action: Action.CREATE_NEW_GAME}));
      },
      getListGame() {
        this.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));
      },
      joinGame(gameId) {
        // this.socketClient.send(JSON.stringify({
        //   action: Action.JOIN_GAME,
        //   content: {
        //     game_id: gameId
        //   }
        // }));
      }
    },
    created() {
      // this.$store.dispatch('connectSocket', this.getListGame);
    }
  }
</script>
