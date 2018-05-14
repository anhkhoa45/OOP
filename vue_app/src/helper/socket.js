import Action from '../helper/game_actions'
import GameStatus from '../helper/game_status'
import Mode from '../helper/game_modes'
import GameCharacters from '../helper/game_characters'
import store from '../store'
import router from '../router'
import Character from "../classes/character/Character";
import KnightCharacter from "../classes/character/KnightCharacter";
import WizardCharacter from "../classes/character/WizardCharacter";
import ArcherCharacter from "../classes/character/ArcherCharacter";
import HotGirlCharacter from "../classes/character/HotGirlCharacter";
import Game from "../classes/game/Game";
import User from "../classes/User";

export function onMessage(event) {
  let jsonObj = JSON.parse(event.data);

  if (jsonObj.status === 500) {
    store.commit('error', jsonObj.content.message);
  } else if (jsonObj.status === 200) {
    let content = jsonObj.content;
    switch (jsonObj.action) {
      case Action.LOGIN: // Game created
        store.commit('saveUser', new User(content.name, content.avatar));
        store.commit('setCurrentComponent', 'game-lobby');
        break;
      case Action.CREATE_NEW_GAME: // Game created
        onCreateGame(content);
        break;
      case Action.JOIN_GAME:
        onJoinGame(content);
        break;
      case Action.GUEST_JOIN_GAME:
        onGuestJoinGame(content);
        break;
      case Action.ANSWER:
        onAnswer(content);
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
      case Action.LEAVE_GAME:
        onLeaveGame(content);
        break;
      case Action.GUEST_LEAVE_GAME:
        onGuestLeaveGame(content);
        break;
      case Action.REPLY_INVITATION:
        onReply(content);
        break;
      case Action.DECLINE_INVITATION:
        store.state.isDeclined = true;
        break;
      case Action.GET_PLAYED_GAMES:
        onGetPlayedGames(content);
        break;
      case Action.REVEAL_CORRECT_WORDS:
        onReveal(content);
        break;
      case Action.PING:
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
    master: new User(data.game.master.name, data.game.master.avatar),
    status: data.game.status
  });

  store.commit('setPlayingGame', game);
  store.commit('setCurrentComponent', 'choose-mode');
  // router.push({name: 'chooseMode'});
}

function onGetListGame(data) {
  store.commit('setListGame', data.games);
}

function onGetOnlineUsers(data) {
  store.commit('setOnlineUser', data.users);
}

function onReveal(data) {
  store.commit('saveCorrectWords', data.correctWord);
}

function onJoinGame(data) {
  setLeavingAlert();

  let game = new Game({
    id: data.game.id,
    mode: data.game.mode,
    master: new User(data.game.master.name, data.game.master.avatar),
    guest: new User(data.game.guest.name, data.game.guest.avatar),
    status: data.game.status
  });

  store.commit('setPlayingGame', game);
  store.commit('setCurrentComponent', 'choose-mode');
  // router.push({name: 'chooseMode'});
}

function onReply(data) {
  store.commit('invite', data);
}

function onGuestJoinGame(data) {
  store.commit('setPlayingGameGuest', new User(data.game.guest.name, data.game.guest.avatar));
}

function onSetGameMode(data) {
  store.commit('setPlayingGameMode', data.game.mode);
}

function onDoneChooseMode(data) {
  switch (data.mode) {
    case Mode.NORMAL:
      store.commit('setGuestCharacter', new Character());
      store.commit('setMasterCharacter', new Character());
      store.commit('setCurrentComponent', 'normal-game');
      //router.push({name: 'normalGame'});
      break;
    case Mode.ATTACK:
      store.commit('setCurrentComponent', 'attack-game');
      // router.push({name: 'attackGame'});
      break;
  }
}

function createCharacter(data) {
  let health = data.character.health;
  let attack = data.character.attack;
  let characterType = data.character_type;

  switch (characterType) {
    case GameCharacters.KNIGHT.id:
      return new KnightCharacter(GameCharacters.KNIGHT.name, health, attack);
    case GameCharacters.WIZARD.id:
      return new WizardCharacter(GameCharacters.WIZARD.name, health, attack);
    case GameCharacters.HOT_GIRL.id:
      return new HotGirlCharacter(GameCharacters.HOT_GIRL.name, health, attack);
    case GameCharacters.ARCHER.id:
      return new ArcherCharacter(GameCharacters.ARCHER.name, health, attack);
  }
}

function onSetGameCharacter(data) {
  store.commit('setGameCharacter', createCharacter(data));
}

function onSetRivalCharacter(data) {
  store.commit('setRivalCharacter', createCharacter(data));
}

function onGuestReady(data) {
  store.commit('setGameStatus', GameStatus.GUEST_READY);
}

function onStartGame(data) {
  store.commit('setGameStatus', GameStatus.STARTED);
  store.commit('setGameTopic', data.game.topic);

  switch (data.game.mode) {
    case Mode.ATTACK:
      // router.push({name: 'attackGameFight'});
      store.commit('setCurrentComponent', 'attack-game-fight');
      break;
    case Mode.NORMAL:
      store.commit('setCurrentComponent', 'normal-game');
      // router.push({name: 'normalGame'});
      break;
  }
}

function onAnswer(data) {
  store.commit('addAnswer', data.answer);
}

function onGetGameState(data) {
  store.commit('setGameStatus', data.game.status);
  store.commit('setGameTimeLeft', data.game.timeLeft);
  store.commit('updateMasterCharacterInfo', data.game.master_character);
  store.commit('updateGuestCharacterInfo', data.game.guest_character);

  if (store.state.playingGame.mode === Mode.NORMAL && store.state.playingGame.status === GameStatus.GAME_OVER) {
    store.commit('setCurrentComponent', 'game-result');
  }
}

function onLeaveGame(data) {
  store.commit('setPlayingGame', null);
  store.commit('setCurrentComponent', 'game-lobby');
}

function onGuestLeaveGame(data) {
  store.commit('setPlayingGameGuest', null);
  store.commit('setGameStatus', GameStatus.INITIAL);
  store.commit('setCurrentComponent', 'choose-mode');
}

function onGetPlayedGames(data) {
  store.commit('setPlayedGames', data.played_games);
}

