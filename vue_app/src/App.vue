<template>
  <div class="container-fluid">
    <component :is="currentComponent"></component>
    <!--<router-view></router-view>-->
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
        currentComponent: state => state.currentComponent
      })
    },
    beforeDestroy() {
      this.$store.state.socketClient.close();
    },
    components: {
      WelcomeScreen, GameLobby, WaitingGameList, ChooseMode, AttackGame, NormalGame, AttackGameFight, AttackGameResult
    }
  }
</script>
