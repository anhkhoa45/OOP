import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import {get} from '../helper/request'
import {onMessage} from '../helper/socket'
import Action from '../helper/game_actions'

const store = new Vuex.Store({
  state: {
    token: window.localStorage.getItem('token'),
    user: {
      id: null,
      email: null,
      name: null,
      avatar: null
    },
    socketClient: {},
    playingGame: {
      id: null,
      mode: null,
      me: {},
      rival: {},
      isMaster: false,
      status: 0
    },
    games: []
  },
  mutations: {
    saveToken(state, token) {
      window.localStorage.setItem('token', token);
      state.token = token;
    },
    saveUser(state, user) {
      state.user = user;
    },
    setSocketClient(state, socketClient){
      state.socketClient = socketClient;
    },
    logout(state){
      window.localStorage.removeItem('token');
      state.token = null;
      state.user = {
        id: null,
          email: null,
          name: null,
          avatar: null
      };
      state.socketClient = {};
    },
    resetPlayingGame(state){
      state.playingGame = {
        id: null,
        mode: null,
        question: '',
        me: {},
        rival: {},
        isMaster: false
      }
    },
    setPlayingGameId(state, gameId){
      state.playingGame.id = gameId;
    },
    setPlayingGameMode(state, mode){
      state.playingGame.mode = mode;
    },
    setGameCharacter(state, character){
      state.playingGame.me = character;
    },
    setRivalCharacter(state, character){
      state.playingGame.rival = character;
    },
    setGameStatus(state, status){
      state.playingGame.status = status;
    },
    setGameQuestion(state, question){
      state.playingGame.question = question;
    },
    updateMyInfo(state, info){
      state.playingGame.me.updateState(info);
    },
    updateRivalInfo(state, info){
      state.playingGame.rival.updateState(info);
    },
    setPlayingGame(state, game){
      state.playingGame = game;
    },
    setListGame(state, games){
      state.games = games;
    }
  },
  actions: {
    fetchUser({ commit }){
      return new Promise((res, rej) => {
        get('/api/user')
          .then(({data}) => {
            commit('saveUser', data);
            res();
          })
          .catch(() => {
            rej();
          })
      })
    },
    connectSocket({ state, commit }, onOpen){
      let endPointURL = `ws://${window.location.host}/game-server/${state.user.id}`;
      let socketClient = new WebSocket(endPointURL);
      socketClient.onopen = function(){
        socketClient.onmessage = onMessage;
        onOpen();
      };
      socketClient.onerror = function(){
        socketClient.close();
      };

      commit('setSocketClient', socketClient);
    },
    fetchListGame({ state }){
      state.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));
    }
  }
});

export default store;
