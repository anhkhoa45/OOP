import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import {get} from '../helper/request'
import {onMessage} from '../helper/socket'
import Action from '../helper/game_actions'
import Character from "../classes/character/Character";

const store = new Vuex.Store({
  state: {
    token: window.localStorage.getItem('token'),
    socketClient: null,
    playingGame: null,
    games: [],
    onlineUsers: [],
    haveInvitation: false,
    invitation: [],
    isDeclined: false,
    currentComponent: 'welcome-screen'
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
    setDoneChooseMode(state) {
      state.playingGame.master.character = new Character();
      state.playingGame.guest.character = new Character();
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
      state.playingGame.topic = question;
    },
    updateMasterCharacterInfo(state, info) {
      state.playingGame.master.character.updateState(info);
    },
    updateGuestCharacterInfo(state, info) {
      state.playingGame.guest.character.updateState(info);
    },
    setListGame(state, games) {
      state.games = games;
    },

    addAnswer(state, answer) {
      if (state.user.name === state.playingGame.master.name) {
        state.playingGame.master.character.answers.push(answer);
      } else if (state.user.name === state.playingGame.guest.name) {
        state.playingGame.guest.character.answers.push(answer);
      }
    },

    setOnlineUser(state, onlineUsers) {
      state.onlineUsers = onlineUsers;
    },
    setCurrentComponent(state, component){
      state.currentComponent = component;
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
