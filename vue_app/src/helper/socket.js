import Action from '../helper/game_actions'
import GameStatus from '../helper/game_status'
import Mode from '../helper/game_modes'
import Character from '../helper/game_characters'
import store from '../store'
import router from '../router'
import KnightPlayer from "../classes/player/KnightPlayer";
import MedusaPlayer from "../classes/player/MedusaPlayer";
import HotGirlPlayer from "../classes/player/HotGirlPlayer";
import DraculaPlayer from "../classes/player/DraculaPlayer";

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
    }
  }
}

function setLeavingAlert() {
  let leavingAlert = function () {
    return "Leave current game ?";
  };
  window.onbeforeunload = leavingAlert;
  window.onblur = leavingAlert;
}

function onCreateGame(data) {
  setLeavingAlert();
  store.commit('setPlayingGame', {
    id: data.game.id,
    mode: data.game.mode,
    me: data.game.master,
    rival: {},
    isMaster: true,
    status: GameStatus.INITIAL
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
    router.push({name: 'normalGame'});
  }
}

function onSetGameMode(data) {
  store.commit('setPlayingGameMode', data.game.mode);
  switch (data.game.mode) {
    case Mode.ATTACK:
      router.push({name: 'attackGame'});
      break;
    case Mode.NORMAL:
      router.push({name: 'normalGame'});
      break;
  }
}

function createPlayer(data) {
  let id = data.character.id;
  let name = data.character.name;
  let health = data.character.health;
  let attack = data.character.attack;
  let characterType = data.character_type;

  switch (characterType) {
    case Character.KNIGHT.id:
      return new KnightPlayer(id, name, health, attack);
    case Character.MEDUSA.id:
      return new MedusaPlayer(id, name, health, attack);
    case Character.HOT_GIRL.id:
      return new HotGirlPlayer(id, name, health, attack);
    case Character.DRACULA.id:
      return new DraculaPlayer(id, name, health, attack);
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
  router.push({name: 'attackGameFight'});
}
