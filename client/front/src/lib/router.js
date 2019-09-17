import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../pages/Login";
import FrontDesk from "../pages/FrontDesk";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/',
            name: 'login',
            component: Login
        },/* todo 添加新页面*/
        {
            path: '/front-desk/main',
            name: 'front-desk-main',
            component: FrontDesk
        }
    ]
});

router.beforeEach((to, from, next) => {
    next()
});

export default router
