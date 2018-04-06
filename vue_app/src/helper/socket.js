import Action from '../helper/game_actions'
import Mode from '../helper/game_modes'
import store from '../store'
import router from '../router'

export function onMessage(event) {
  let jsonObj = JSON.parse(event.data);

  if (jsonObj.status === 200) {
    let content = jsonObj.content;
    switch (jsonObj.action) {
      case Action.CREATE_NEW_GAME: // Game created
        let leavingAlert = function () {
          return "Leave current game ?";
        };
        window.onbeforeunload = leavingAlert;
        window.onblur = leavingAlert;
        store.commit('setPlayingGame', {
          id: content.game_id,
          mode: null,
          me: {},
          rival: {},
          isMaster: true
        });
        router.push({name: 'chooseMode'});
        break;
      case Action.JOIN_GAME:
        break;
      case Action.ANSWER_QUESTION:
        break;
      case Action.GET_LIST_GAME:
        break;
      case Action.SET_GAME_MODE:
        store.commit('setPlayingGameMode', content.game.mode);
        switch (content.game.mode){
          case Mode.ATTACK:
            router.push({name: 'attackGame'});
            break;
          case Mode.NORMAL:
            router.push({name: 'normalGame'});
            break;
        }
        break;
    }
  }
}
