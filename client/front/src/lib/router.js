import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../pages/Login";

Vue.use(VueRouter)

const router = new VueRouter({
    routes: [
        {
            path:'/',
            name:'login',
            component:Login
        }

    ]
})

router.beforeEach((to, from, next) => {
    next()
})

export default router
