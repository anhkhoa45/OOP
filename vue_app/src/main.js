import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'

import router from './router'
import store from './store'

require('./assets/css/variables.scss');
require('./assets/css/style.scss');

Vue.config.productionTip = false;
Vue.config.devtools = true;
Vue.config.debug = true;

Vue.use(VueRouter);

new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app');
