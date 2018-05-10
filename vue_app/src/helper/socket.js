import Action from '../helper/game_actions'
import GameStatus from '../helper/game_status'
import Mode from '../helper/game_modes'
import Character from '../helper/game_characters'
import store from '../store'
import router from '../router'
import KnightCharacter from "../classes/character/KnightCharacter";
import MedusaCharacter from "../classes/character/MedusaCharacter";
import HotGirlCharacter from "../classes/character/HotGirlCharacter";
import DraculaCharacter from "../classes/character/DraculaCharacter";
import Game from "../classes/game/Game";
import User from "../classes/User";

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
      case Action.GUEST_JOIN_GAME:
        onGuestJoinGame(content);
        break;
      case Action.ANSWER_QUESTION:
        break;
      case Action.GET_LIST_GAME:
        onGetListGame(content);
        break;
      case Action.GET_ONLINE_USERS:
        onGetOnlineUsers(content);
        break;
      case Action.SET_GAME_MODE:
        onSetGameMode(content);
        break;
      case Action.DONE_CHOOSE_MODE:
        onDoneChooseMode(content);
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
      case Action.INVITE:
        alert("Want to join us?");
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

  let game = new Game({
    id: data.game.id,
    mode: data.game.mode,
    master: new User(data.game.master.name),
    status: data.game.status
  });

  store.commit('setPlayingGame', game);
  router.push({name: 'chooseMode'});
}

function onGetListGame(data) {
  store.commit('setListGame', data);
}

function onGetOnlineUsers(data) {
  store.commit('setOnlineUser', data);
}

function onJoinGame(data) {
  setLeavingAlert();

  let game = new Game({
    id: data.game.id,
    mode: data.game.mode,
    master: new User(data.game.master.name),
    guest: new User(data.game.guest.name),
    status: data.game.status
  });

  store.commit('setPlayingGame', game);
  router.push({name: 'chooseMode'});
}

function onGuestJoinGame(data) {
  store.commit('setPlayingGameGuest', new User(data.game.guest.name));
}

function onSetGameMode(data) {
  store.commit('setPlayingGameMode', data.game.mode);
}

function onDoneChooseMode(data) {
  switch (data.mode) {
    case Mode.NORMAL:
      router.push({name: 'normalGame'});
      break;
    case Mode.ATTACK:
      router.push({name: 'attackGame'});
      break;
  }
}

function createCharacter(data) {
  let id = data.character.id;
  let name = data.character.name;
  let health = data.character.health;
  let attack = data.character.attack;
  let characterType = data.character_type;

  switch (characterType) {
    case Character.KNIGHT.id:
      return new KnightCharacter(id, name, health, attack);
    case Character.MEDUSA.id:
      return new MedusaCharacter(id, name, health, attack);
    case Character.HOT_GIRL.id:
      return new HotGirlCharacter(id, name, health, attack);
    case Character.DRACULA.id:
      return new DraculaCharacter(id, name, health, attack);
  }
}

function onSetGameCharacter(data) {
  let character = createCharacter(data);
  store.commit('setGameCharacter', character);
}

function onSetRivalCharacter(data) {
  let character = createCharacter(data);
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

function onGetGameState(data) {
  store.commit('setGameStatus', data.game.status);

  let me, rival;
  if (store.state.playingGame.me.id === data.game.master.id) {
    me = data.game.master;
    rival = data.game.guest;
  } else {
    rival = data.game.master;
    me = data.game.guest;
  }

  store.commit('updateMyInfo', me);
  store.commit('updateRivalInfo', rival);
}
