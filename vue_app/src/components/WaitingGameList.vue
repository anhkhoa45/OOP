<template>
  <div>
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

    <div class="text-center game-list">
      <div class="container margin-top-50">
        <div class="row">
          <div class="col-md-12">
            <div class="row">
              <h1>
                <strong class="ml-auto">ROOM LIST</strong>
              </h1>
              <button type="button" class="btn btn-danger ml-auto" @click="back">Back</button>
            </div>
            <div class="row scrollable">
              <a v-for="game in games" @click.prevent="joinGame(game.id)"
                 :class="{push_button: true, blue: game.mode === 'NORMAL', red: game.mode === 'ATTACK', reset:true}">
                <h4><b>Room {{ game.id }}</b></h4>
                <img :src="`./assets/img/avatar/${game.master.avatar}.png`" class="thumbnail">{{game.master.name}}
                <p>Mode: {{ game.mode }}</p>
              </a>
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

  export default {
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        games: state => state.games,
        haveInvitation: state => state.haveInvitation,
        invitation: state => state.invitation,
      }),
    },
    methods: {
      getListGame() {
        this.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));
      },
      joinGame(gameId) {
        this.socketClient.send(JSON.stringify({
          action: Action.JOIN_GAME,
          content: {
            game_id: gameId
          }
        }));
      },
      back() {
        this.$store.commit('setCurrentComponent', 'game-lobby');
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
      },
    },
    created() {
      this.getListGame();
    }
  }
</script>
