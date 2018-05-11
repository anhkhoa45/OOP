<template>
  <div>
    <h1>Game result</h1>
    <p>...</p>
    <div class="blurbg"></div>
    <div class="row">
    	<div class="col-md-3">
	    	<ul >
	    	<li v-for="answer in playingGame.master.character.answers">{{answer.word}} {{answer.score}}</li>
	    	</ul>
    	</div>
    	<div class="col-md-3">

    	</div>
	</div>
    
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  export default {
    name: "AttackGameResult",
  
  computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        masterRes: state=>state.masterResult,
        guestRes: state=>state.guestResult,
      }),
      master() {
        return this.playingGame.master.character;
      },
      guest() {
        return this.playingGame.guest.character;
      },
      
      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      }
    },
  }
</script>
