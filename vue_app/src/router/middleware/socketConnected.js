import store from '../../store'

export function handler(to, from, next) {
  if (store.state.socketClient) {
    next();
  } else {
    next({name: '/'});
  }
}
