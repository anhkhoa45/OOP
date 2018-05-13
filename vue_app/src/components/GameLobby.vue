<template>
  <div>
    <div class="blur-bg"></div>
    <div v-if="haveInvitation">
      <div class="modal fade show" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
           style="display: block; padding-right: 17px;">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Invitation</h5>
            </div>
            <div class="modal-body">
              You received an invitation from {{invitation.master.name}} for an {{invitation.game.mode}} game
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="acceptInvitation" data-dismiss="modal">Accept
              </button>
              <button type="button" class="btn btn-primary" @click="declineInvitation">Decline</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="container margin-top-100">
      <div class="row">
        <div class="col-md-4">
          <a href="#" class="push_button red" @click.prevent="createNewGame">CREATE GAME</a>
        </div>
        <div class="col-md-4">
          <a href="#" class="push_button blue" @click.prevent="joinGame">JOIN GAME</a>
        </div>
        <div class="col-md-4">
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
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games,
        haveInvitation: state => state.haveInvitation,
        invitation: state => state.invitation,
      })
    },
    methods: {
      createNewGame() {
        this.socketClient.send(JSON.stringify({action: Action.CREATE_NEW_GAME}));
      },
      joinGame() {
        this.$store.commit('setCurrentComponent', 'waiting-game-list')
      },
      acceptInvitation() {
        this.socketClient.send(JSON.stringify({
          action: Action.JOIN_GAME,
          content: {
            game_id: this.invitation.game.id,
          }
        }));

        this.$store.state.haveInvitation = false;
      },
      declineInvitation() {
        this.$store.state.haveInvitation = false;
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
