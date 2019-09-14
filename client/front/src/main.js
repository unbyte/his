import Vue from 'vue'
import App from './App.vue'
import router from './lib/router'
import store from './lib/store'
import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import request from './lib/request'

Vue.use(MuseUI)

Vue.config.productionTip = false
Vue.prototype.$request = request

new Vue({
    render: h => h(App),
    router,
    store
}).$mount('#app')
