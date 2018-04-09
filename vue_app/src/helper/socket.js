import Action from '../helper/game_actions'
import Mode from '../helper/game_modes'
import Character from '../helper/game_characters'
import store from '../store'
import router from '../router'
import KnightPlayer from "../classes/KnightPlayer";
import MedusaPlayer from "../classes/MedusaPlayer";
import HotGirlPlayer from "../classes/HotGirlPlayer";
import DraculaPlayer from "../classes/DraculaPlayer";

export function onMessage(event) {
  let jsonObj = JSON.parse(event.data);

  if (jsonObj.status === 200) {
    let content = jsonObj.content;
    switch (jsonObj.action) {
      case Action.CREATE_NEW_GAME: // Game created
        onCreateGame(content);
        break;
      case Action.JOIN_GAME:
        break;
      case Action.ANSWER_QUESTION:
        break;
      case Action.GET_LIST_GAME:
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
    }
  }
}

function onCreateGame(data){
  let leavingAlert = function () {
    return "Leave current game ?";
  };
  window.onbeforeunload = leavingAlert;
  window.onblur = leavingAlert;
  store.commit('setPlayingGame', {
    id: data.game_id,
    mode: null,
    me: {},
    rival: {},
    isMaster: true
  });
  router.push({name: 'chooseMode'});
}

function onSetGameMode(data){
  store.commit('setPlayingGameMode', data.game.mode);
  switch (data.game.mode){
    case Mode.ATTACK:
      router.push({name: 'attackGame'});
      break;
    case Mode.NORMAL:
      router.push({name: 'normalGame'});
      break;
  }
}

function onSetGameCharacter(data){
  let character;
  let health = data.character.health;
  let attack = data.character.attack;

  switch (data.character_type) {
    case Character.KNIGHT.id:
      character = new KnightPlayer(health, attack);
      break;
    case Character.MEDUSA.id:
      character = new MedusaPlayer(health, attack);
      break;
    case Character.HOT_GIRL.id:
      character = new HotGirlPlayer(health, attack);
      break;
    case Character.DRACULA.id:
      character = new DraculaPlayer(health, attack);
      break;
    default:
      console.log('NOT OK');
      break;
  }

  store.commit('setGameCharacter', character);
}

function onSetRivalCharacter(data){
  let character;
  let health = data.character.health;
  let attack = data.character.attack;

  switch (data.character_type) {
    case Character.KNIGHT:
      character = new KnightPlayer(health, attack);
      break;
    case Character.MEDUSA:
      character = new MedusaPlayer(health, attack);
      break;
    case Character.HOT_GIRL:
      character = new HotGirlPlayer(health, attack);
      break;
    case Character.DRACULA:
      character = new DraculaPlayer(health, attack);
      break;
  }

  store.commit('setGameCharacter', character);
}
