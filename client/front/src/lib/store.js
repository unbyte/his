import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        global: {}
    },
    mutations: {
        setGlobalData(state, globalData) {
            state.global = globalData;
        }
    },
    actions: {}
})

export default store
