import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
    },
    navUrl:'',
    mapUrl:'',
  },
  mutations: {
    login (state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    changeNavUrl(state, url){
      state.navUrl = url
    },
    changeMapUrl(state,url){
      state.mapUrl = url
    }
  }
})
