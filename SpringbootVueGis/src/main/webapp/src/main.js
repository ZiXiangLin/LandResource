// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

//MUSE UI
import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'

//VUEX
import store from './store'

//ELEMENT UI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

//VueI18N
import VueI18n from 'vue-i18n'

// Vue-tabs
import VueTaber from "vue-tabs";
import 'vue-tabs/vue-tabs.css';
import tabs from "./common/tabs/tabs";

//ICONFONT
import './assets/icon/iconfont.css'

//ECharts
import echarts from 'echarts'

Vue.use(MuseUI);
Vue.use(ElementUI);
Vue.use(router);
Vue.use(VueI18n);
Vue.use(VueTaber);
Vue.prototype.$echarts = echarts

const i18n = new VueI18n({
  locale:'zh',
  messages:{
    'zh':require('./common/lang/zh'),
    'en':require('./common/lang/en')
  }
});


// 设置反向代理，前端请求默认发送到 http://localhost:8443/api
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
      if (store.state.user.username) {
        next()
      } else {
        next({
          path: 'login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
  }
)

const vueTaber = new VueTaber({
  tabs,
  persist:false
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  taber:vueTaber,
  components: { App },
  template: '<App/>'
})
