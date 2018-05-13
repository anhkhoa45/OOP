<template>
  <div>
    <h1>Game result</h1>
    
    <!-- <div class="blur-bg"></div> -->
    <div v-if="isWin" class="alert alert-success" role="alert">
	  You win!!!
	</div>
	<div v-if="!isWin" class="alert alert-dark" role="alert">
	  You lose 
	</div>
    <div class="row">
		
    	<div class="col-md-3">
	    	
	    	<span>{{playingGame.master.name}}</span>
	    	<p v-for="answer in playingGame.master.character.answers">
	    	 
		        <span class="mr-10">{{ answer.word }}</span>
		        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
		        <span v-else class="negative-state">{{ answer.score }}</span>

      		</p>
      		<span>Total score: {{masterRes}}</span>
    	</div>

    	<div class="col-md-3">
    		<span>{{playingGame.guest.name}}</span>
	    	<p v-for="answer in playingGame.guest.character.answers">
	    	 
		        <span class="mr-10">{{ answer.word }}</span>
		        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
		        <span v-else class="negative-state">{{ answer.score }}</span>

	      	</p>
      		<span>Total score: {{guestRes}}</span>
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
        masterRes: state => state.masterResult,
        guestRes: state => state.guestResult,
      }),
      master() {
        return this.playingGame.master.character;
      },
      guest() {
        return this.playingGame.guest.character;
      },

      isMaster() {
        return this.$store.state.user.name === this.playingGame.master.name;
      },
      isWin(){
      	if(this.$store.state.user.name === this.playingGame.master.name){
      		if(this.masterRes>this.guestRes) return true;
      		else return false;
      	}
      	else{
      		if(this.masterRes<this.guestRes) return true;
      		else return false;
      	}
      },
    },
  }
</script>
