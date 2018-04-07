import store from '../../store'

export function handler(to, from, next){
  if(store.state.token) {
    store.dispatch('fetchUser')
      .then(() => {
        next({ name: 'welcomeScreen' });
      })
      .catch(() => {
        store.commit('logout');
        next();
      });
  } else {
    next();
  }
}
