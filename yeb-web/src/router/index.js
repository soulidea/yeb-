import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
import FriendChat from "../views/chat/FriendChat";
import AdminInfo from "../views/AdminInfo";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      children:[
        {
          path: '/chat',
          name: 'friendChat',
          component:FriendChat
        },
        {
          path: '/userinfo',
          name: '个人中心',
          component: AdminInfo
        }
      ]
    }

  ]
})
