import Action from '../helper/game_actions'
import GameStatus from '../helper/game_status'
import Mode from '../helper/game_modes'
import Character from '../helper/game_characters'
import store from '../store'
import router from '../router'
import KnightPlayer from "../classes/character/KnightCharacter";
import MedusaPlayer from "../classes/character/MedusaCharacter";
import HotGirlPlayer from "../classes/character/HotGirlCharacter";
import DraculaPlayer from "../classes/character/DraculaCharacter";

export function onMessage(event) {
  let jsonObj = JSON.parse(event.data);

  if (jsonObj.status === 200) {
    let content = jsonObj.content;
    switch (jsonObj.action) {
      case Action.CREATE_NEW_GAME: // Game created
        onCreateGame(content);
        break;
      case Action.JOIN_GAME:
        onJoinGame(content);
        break;
      case Action.ANSWER_QUESTION:
        break;
      case Action.GET_LIST_GAME:
        onGetListGame(content);
        break;
      case Action.SET_GAME_MODE:
        onSetGameMode(content);
        break;
      case Action.SET_GAME_CHARACTER:
        onSetGameCharacter(content);
        break;
      case Action.SET_RIVAL_CHARACTER:
        onSetRivalCharacter(content);
        break;
      case Action.GUEST_READY:
        onGuestReady(content);
        break;
      case Action.START_GAME:
        onStartGame(content);
        break;
      case Action.GET_GAME_STATE:
        onGetGameState(content);
        break;
    }
  }
}

function setLeavingAlert() {
  window.onbeforeunload = function () {
    return "Leave current game ?";
  };
}

function onCreateGame(data) {
  setLeavingAlert();
  store.commit('setPlayingGame', {
    id: data.game.id,
    mode: null,
    me: data.game.master,
    rival: null,
    isMaster: true,
    status: data.game.status
  });
  router.push({name: 'chooseMode'});
}

function onGetListGame(data) {
  store.commit('setListGame', data);
}

function onJoinGame(data) {
  setLeavingAlert();
  store.commit('setPlayingGame', {
    id: data.game.id,
    mode: data.game.mode,
    me: data.game.guest,
    rival: data.game.master,
    isMaster: false,
    status: GameStatus.INITIAL
  });

  if (data.game.mode === Mode.ATTACK) {
    router.push({name: 'attackGame'});
  } else {
    router.push({name: 'chooseMode'});
  }
}

function onSetGameMode(data) {
  store.commit('setPlayingGameMode', data.game.mode);
  // switch (data.game.mode) {
  //   case Mode.ATTACK:
  //     router.push({name: 'attackGame'});
  //     break;
  //   case Mode.NORMAL:
  //     router.push({name: 'normalGame'});
  //     break;
  // }
}

function createPlayer(data) {
  let id = data.character.id;
  let name = data.character.name;
  let avatar = data.character.avatar;
  let health = data.character.health;
  let attack = data.character.attack;
  let characterType = data.character_type;

  switch (characterType) {
    case Character.KNIGHT.id:
      return new KnightPlayer(id, name, avatar, health, attack);
    case Character.MEDUSA.id:
      return new MedusaPlayer(id, name, avatar, health, attack);
    case Character.HOT_GIRL.id:
      return new HotGirlPlayer(id, name, avatar, health, attack);
    case Character.DRACULA.id:
      return new DraculaPlayer(id, name, avatar, health, attack);
  }
}

function onSetGameCharacter(data) {
  let character = createPlayer(data);
  store.commit('setGameCharacter', character);
}

function onSetRivalCharacter(data) {
  let character = createPlayer(data);
  store.commit('setRivalCharacter', character);
}

function onGuestReady(data) {
  store.commit('setGameStatus', GameStatus.GUEST_READY);
}

function onStartGame(data) {
  store.commit('setGameStatus', GameStatus.STARTED);
  store.commit('setGameQuestion', data.game.question);

  switch (data.game.mode) {
    case Mode.ATTACK:
      router.push({name: 'attackGameFight'});
      break;
    case Mode.NORMAL:
      router.push({name: 'normalGame'});
      break;
  }
}

function onGetGameState(data){
  store.commit('setGameStatus', data.game.status);

  let me, rival;
  if(store.state.playingGame.me.id === data.game.master.id){
    me = data.game.master;
    rival = data.game.guest;
  } else {
    rival = data.game.master;
    me = data.game.guest;
  }

  store.commit('updateMyInfo', me);
  store.commit('updateRivalInfo', rival);
}
