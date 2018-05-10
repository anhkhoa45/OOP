import store from '../../store'

export function handler(to, from, next){
  if(store.state.playingGame && store.state.playingGame.id !== null) {
    next();
  } else {
    next({ name: 'welcomeScreen' });
  }
}
