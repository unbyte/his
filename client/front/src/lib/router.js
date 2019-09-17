import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../pages/Login";
import FrontDesk from "../pages/FrontDesk";
import FrontDeskIndex from "../components/front-desk/FrontDeskIndex";
import FrontDeskRegister from "../components/front-desk/FrontDeskRegister";
import FrontDeskUnregister from "../components/front-desk/FrontDeskUnregister";
import FrontDeskCharge from "../components/front-desk/FrontDeskCharge";
import FrontDeskRefund from "../components/front-desk/FrontDeskRefund";
import FrontDeskQuery from "../components/front-desk/FrontDeskQuery";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/',
            name: 'login',
            component: Login
        },/* todo 添加新页面*/
        {
            path: '/front-desk',
            component: FrontDesk,
            children: [
                {
                    path: 'main',
                    component: FrontDeskIndex
                },
                {
                    path: 'register',
                    component: FrontDeskRegister
                },
                {
                    path: 'unregister',
                    component: FrontDeskUnregister
                },
                {
                    path: 'charge',
                    component: FrontDeskCharge
                },
                {
                    path: 'refund',
                    component: FrontDeskRefund
                },
                {
                    path: 'query',
                    component: FrontDeskQuery
                }
            ]
        }
    ]
});

router.beforeEach((to, from, next) => {
    next()
});

export default router
