<template>
  <div>
    <div class="blurbg"></div>
    <div v-if="haveInvitation">
      <!-- Modal -->
      <div class="modal fade show" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: block; padding-right: 17px;">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Invitation</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <!-- <span aria-hidden="true">&times;</span> -->
              </button>
            </div>
            <div class="modal-body">
              You received an invitation from {{invitation.master.name}} for an {{invitation.game.mode}} game
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="acceptInvitation" data-dismiss="modal">Accept</button>
              <button type="button" class="btn btn-primary" @click="declineInvitation">Decline</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <h1 class="border-text middle"><strong>Choose game &#33;</strong></h1>
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div>
            <br>
            <h2 class="white-text">Friend list</h2>
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
  import Game from "../classes/game/Game"
  import User from "../classes/User"
  let game;
  export default {
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games,
        username: state => state.user.name,
        haveInvitation: state => state.haveInvitation,
        invitation: state => state.invitation,
      })
    },
    methods: {
      createNewGame() {
        this.socketClient.send(JSON.stringify({action: Action.CREATE_NEW_GAME}));
      },
      joinGame() {
        this.$router.push({name: 'waitingGameList'})
      },
      acceptInvitation(){
        this.socketClient.send(JSON.stringify({
          action: Action.JOIN_GAME,
          content: {
            game_id: this.invitation.game.id,
          }
        }));
      },
      declineInvitation(){
        this.$store.state.haveInvitation=false;
        this.socketClient.send(JSON.stringify({
          action: Action.DECLINE_INVITATION,
          content: {
            name: this.invitation.master.name,
          }
        }));
      }
    }
  }
</script>
