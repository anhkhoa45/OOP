import Vue from 'vue'
import VueRouter from 'vue-router'

import Middleware from './middleware'

import Login from '../components/Login'
import WelcomeScreen from '../components/WelcomeScreen'
import GameLobby from '../components/GameLobby'
import ChooseMode from '../components/ChooseMode'
import AttackGame from '../components/AttackGame'
import NormalGame from '../components/NormalGame'

Vue.use(VueRouter);

let routes = [
  {path: '/', name: 'login', component: Login, beforeEnter: Middleware.redirectIfAuthenticated},
  {path: '/welcome', name: 'welcomeScreen', component: WelcomeScreen, beforeEnter: Middleware.requireLogin},
  {path: '/game-lobby', name: 'gameLobby', component: GameLobby, beforeEnter: Middleware.requireLogin},
  {path: '/choose-mode', name: 'chooseMode', component: ChooseMode, beforeEnter: Middleware.joinedGame},
  {path: '/game-box/attack', name: 'attackGame', component: AttackGame, beforeEnter: Middleware.joinedGame},
  {path: '/game-box/normal', name: 'normalGame', component: NormalGame, beforeEnter: Middleware.joinedGame},
];

const router = new VueRouter({
  routes
});

export default router;
