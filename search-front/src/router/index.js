import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/',
      name: 'Search',
      component: () => import('@/views/Search')
    },
    {
      path: '/manage',
      name: 'Manage',
      component: () => import('@/views/mq/Manage')
    }
  ]
})
