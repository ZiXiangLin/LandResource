import Vue from 'vue'
import Router from 'vue-router'

import index from '../components/IndexPage'
import Login from "../components/Login";
import Register from "../components/Register";
import UserInfo from "../components/UserInfo";
import manage from "../components/manage";

// For TEST
import test from "../components/test";
import Drawer from "../components/PageParts/Drawer"


Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/index',
      component:index,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path:'/',
      name: 'Login',
      component: Login
    },
    {
      path:'/register',
      name:'Register',
      component:Register
    },
    {
      path:'/userInfo',
      name:'UserInfo',
      component:UserInfo
    },
    {
      path:'/manage',
      name:'manage',
      component:manage
    },
    {
      path:'/test',
      name: 'test',
      component: test
    },
    {
      path:'/test2',
      component: Drawer
    }
  ]
})
