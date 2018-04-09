import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import {get} from '../helper/request'
import {onMessage} from '../helper/socket'

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
      isMaster: false
    }
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
    setPlayingGame(state, game){
      state.playingGame = game;
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
    connectSocket({ state, commit }){
      let endPointURL = `ws://${window.location.host}/game-server/${state.user.id}`;
      let socketClient = new WebSocket(endPointURL);
      socketClient.onopen = function(){
        socketClient.onmessage = onMessage;
      };
      socketClient.onerror = function(){
        socketClient.close();
      };

      commit('setSocketClient', socketClient);
    }
  }
});

export default store;
