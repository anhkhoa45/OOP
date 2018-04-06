import store from '../../store'

export function handler(to, from, next){
  if(store.state.token) {
    store.dispatch('fetchUser')
      .then(() => {
        next();
      })
      .catch(() => {
        store.commit('logout');
        next({ name: 'login' });
      });
  } else {
    next({ name: 'login' });
  }
}
