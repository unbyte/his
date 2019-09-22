import Vue from 'vue'
import App from './App.vue'
import router from './lib/router'
import store from './lib/store'
import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import io from './lib/io'
import utils from './lib/utils'
import Toast from 'muse-ui-toast'

Vue.use(MuseUI);
Vue.use(Toast, {
    position: 'bottom',
    time: 2000,
    close: false,
    successIcon: '',
    infoIcon: '',
    warningIcon: '',
    errorIcon: ''
});

Vue.config.productionTip = false;

Vue.prototype.$utils = utils;

window['mode'] = 'dev'; // or product

window['isDev'] = window.mode === 'dev';

// 几个bridge
window['io'] = io;

// 接收推送
window['inform'] = message => {
    io.receive(message)
};

new Vue({
    render: h => h(App),
    router,
    store
}).$mount('#app');
