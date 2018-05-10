import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import {get} from '../helper/request'
import {onMessage} from '../helper/socket'
import Action from '../helper/game_actions'

const store = new Vuex.Store({
  state: {
    token: window.localStorage.getItem('token'),
    socketClient: null,
    playingGame: null,
    games: [],
    onlineusers: [],
    haveInvitation: false,
    invitation: [],
    isDeclined: false,
  },
  mutations: {
    invite(state, invitation){
      state.haveInvitation=true;
      state.invitation=invitation;
    },
    saveToken(state, token) {
      window.localStorage.setItem('token', token);
      state.token = token;
    },
    
    saveUser(state, user) {
      state.user = user;
    },
    setSocketClient(state, socketClient) {
      state.socketClient = socketClient;
    },
    logout(state) {
      window.localStorage.removeItem('token');
      state.token = null;
      state.user = null;
      state.socketClient = null;
    },
    resetPlayingGame(state) {
      state.playingGame = null;
    },
    setPlayingGame(state, game) {
      state.playingGame = game;
    },
    setPlayingGameMode(state, mode) {
      state.playingGame.mode = mode;
    },
    setPlayingGameGuest(state, guest) {
      state.playingGame.guest = guest;
    },
    setMasterCharacter(state, character) {
      state.playingGame.master.character = character;
    },
    setGuestCharacter(state, character) {
      state.playingGame.guest.character = character;
    },
    setGameCharacter(state, character) {
      if(state.user.name === state.playingGame.master.name) {
        state.playingGame.master.character = character;
      } else if(state.user.name === state.playingGame.guest.name) {
        state.playingGame.guest.character = character;
      }
    },
    setRivalCharacter(state, character) {
      if(state.user.name === state.playingGame.master.name) {
        state.playingGame.guest.character = character;
      } else if(state.user.name === state.playingGame.guest.name) {
        state.playingGame.master.character = character;
      }
    },
    setGameStatus(state, status) {
      state.playingGame.status = status;
    },
    setGameTopic(state, question) {
      state.playingGame.question = question;
    },
    updateMyInfo(state, info) {
      state.playingGame.me.updateState(info);
    },
    updateRivalInfo(state, info) {
      state.playingGame.rival.updateState(info);
    },
    setListGame(state, games) {
      state.games = games;
    },
    setOnlineUser(state, onlineusers) {
      state.onlineusers = onlineusers;
    }
  },
  actions: {
    fetchUser({commit}) {
      return new Promise((res, rej) => {
        get('/api/user')
          .then(({data}) => {
            //this.$store.user.id++;
            commit('saveUser', data);
            res();
          })
          .catch(() => {
            rej();
          })
      })
    },
    connectSocket({commit}, userName) {
      return new Promise((res, rej) => {
        let endPointURL = `ws://${window.location.host}/game-server/${userName}`;
        let socketClient = new WebSocket(endPointURL);
        socketClient.onopen = function () {
          socketClient.onmessage = onMessage;
          res();
        };
        socketClient.onerror = function () {
          socketClient.close();
          rej();
        };

        commit('setSocketClient', socketClient);
      });
    },
    fetchListGame({state}) {
      state.socketClient.send(JSON.stringify({action: Action.GET_LIST_GAME}));
    }
  }
});

export default store;
