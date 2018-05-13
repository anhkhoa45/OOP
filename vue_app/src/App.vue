<template>
  <div class="container-fluid">
    <div class="container margin-top-10 attack-game-box">
      <div class="row" id="content"></div>
    </div>
    <component :is="currentComponent"></component>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import WelcomeScreen from './components/WelcomeScreen'
  import GameLobby from './components/GameLobby'
  import WaitingGameList from './components/WaitingGameList'
  import ChooseMode from './components/ChooseMode'
  import AttackGame from './components/AttackGame'
  import NormalGame from './components/NormalGame'
  import AttackGameFight from './components/AttackGameFight'
  import AttackGameResult from './components/AttackGameResult'

  export default {
    computed: {
      ...mapState({
        currentComponent: state => state.currentComponent,
        error: state => state.error
      })
    },
    watch: {
      error(){
        if(this.error !== null){
          alert('Some error occurred \n' + this.error);
          this.$store.state.error = null;
        }
      }
    },
    beforeDestroy() {
      this.$store.state.socketClient.close();
    },
    components: {
      WelcomeScreen, GameLobby, WaitingGameList, ChooseMode, AttackGame, NormalGame, AttackGameFight, AttackGameResult
    }
  }
</script>
