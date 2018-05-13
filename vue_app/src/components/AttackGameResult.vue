<template>
  <div>
<!-- <<<<<<< HEAD
    <h1>Game result</h1>
    
    <div class="blur-bg"></div> 
    <div v-if="isWin" class="alert alert-success" role="alert">
	  You win!!!
	</div>
	<div v-if="!isWin" class="alert alert-dark" role="alert">
	  You lose 
	</div>
    <div class="row">
		
    	<div class="col-md-3">
	    	
	    	<h4>{{playingGame.master.name}}</h4>
	    	<p v-for="answer in playingGame.master.character.answers">
	    	 
		        <span class="mr-10">{{ answer.word }}</span>
		        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
		        <span v-else class="negative-state">{{ answer.score }}</span>

      		</p>
      		<span>Total score: {{masterRes}}</span>
    	</div>

    	<div class="col-md-3">
    		<h4>{{playingGame.guest.name}}</h4>
	    	<p v-for="answer in playingGame.guest.character.answers">
	    	 
		        <span class="mr-10">{{ answer.word }}</span>
		        <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
		        <span v-else class="negative-state">{{ answer.score }}</span>

	      	</p>
      		<span>Total score: {{guestRes}}</span>
    	</div>
	</div>

======= -->
    <div class="row attack-game-box">
      <div class="col-md-12">
        <div id="content"></div>
      </div>
      <div class="col-md-6 offset-md-3">
        <div class="row play-area margin-top-50">
          <div class="col-md-6 answer-box left-box">
            <h3>{{ me.name }}</h3>
            <div class="health-bar" :style="{width: me.character.maxHealth}">
              <div class="bar text-center" :style="{width: me.character.health / me.character.maxHealth * 100 + '%'}">
                {{ me.character.health }}
                <div class="hit"></div>
              </div>
            </div>
            <p><strong>Attack: </strong>{{ me.character.attack }}</p>
            <p v-if="me.character.isPowered">
              <strong class="text-danger">Powered!</strong>
            </p>
            <hr>
            <p v-for="answer in me.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
          <div class="col-md-6 answer-box">
            <h3 class="text-right">{{ rival.name }}</h3>
            <div class="health-bar" :style="{width: rival.character.maxHealth}">
              <div class="bar text-center"
                   :style="{width: rival.character.health / rival.character.maxHealth * 100 + '%'}">
                {{ rival.character.health }}
                <div class="hit"></div>
              </div>
            </div>
            <p><strong>Attack: </strong>{{ rival.character.attack }}</p>
            <p v-if="rival.character.isPowered">
              <strong class="text-danger">Powered!</strong>
            </p>
            <hr>
            <p v-for="answer in rival.character.answers">
              <span class="mr-10">{{ answer.word }}</span>
              <span v-if="answer.score > 0" class="positive-state">{{ answer.score }}</span>
              <span v-else class="negative-state">{{ answer.score }}</span>
            </p>
          </div>
        </div>
        <div class="row margin-top-50 text-center">
          <div class="col-md-12 text-center">
            <button type="button" class="btn btn-primary" @click="toGameLobby">Return to game lobby</button>
          </div>
        </div>
      </div>
    </div>
<!-- >>>>>>> d4c04e5396e7411f2587e57c3e6e8febd6b91108 -->
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    computed: {
      ...mapState({
        socketClient: state => state.socketClient,
        playingGame: state => state.playingGame,
        user: state => state.user,
      }),
      me() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.master : this.playingGame.guest;
      },
      rival() {
        return this.playingGame.master.name === this.user.name ? this.playingGame.guest : this.playingGame.master;
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
    methods: {
      toGameLobby(){
        this.$store.commit('setCurrentComponent', 'game-lobby');
        this.$store.commit('setPlayingGame', null);
      }
    }
  }
</script>
