import Vue from 'vue'
import VueRouter from 'vue-router'

import Middleware from './middleware'

import Login from '../components/Login'
import WelcomeScreen from '../components/WelcomeScreen'
import GameLobby from '../components/GameLobby'
import WaitingGameList from '../components/WaitingGameList'
import ChooseMode from '../components/ChooseMode'
import AttackGame from '../components/AttackGame'
import NormalGame from '../components/NormalGame'
import AttackGameFight from '../components/AttackGameFight'
import AttackGameResult from '../components/AttackGameResult'

Vue.use(VueRouter);

let routes = [
  // {path: '/', name: 'login', component: Login, beforeEnter: Middleware.redirectIfAuthenticated},
  {path: '/', name: 'welcomeScreen', component: WelcomeScreen},
  {path: '/game-lobby', name: 'gameLobby', component: GameLobby, beforeEnter: Middleware.socketConnected},
  {path: '/waiting-game-list', name: 'waitingGameList', component: WaitingGameList},
  {path: '/choose-mode', name: 'chooseMode', component: ChooseMode, beforeEnter: Middleware.joinedGame},
  {path: '/attack-game', name: 'attackGame', component: AttackGame, beforeEnter: Middleware.joinedGame},
  {path: '/normal-game', name: 'normalGame', component: NormalGame, beforeEnter: Middleware.joinedGame},
  {path: '/attack-game/play', name: 'attackGameFight', component: AttackGameFight, beforeEnter: Middleware.joinedGame},
  {path: '/attack-game/result', name: 'attackGameResult', component: AttackGameResult, beforeEnter: Middleware.joinedGame},
];

const router = new VueRouter({
  routes
});

export default router;
