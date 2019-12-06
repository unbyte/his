import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../pages/Login";
import FrontDesk from "../pages/FrontDesk";
import FrontDeskIndex from "../components/front-desk/FrontDeskIndex";
import FrontDeskRegister from "../components/front-desk/FrontDeskRegister";
import FrontDeskCancel from "../components/front-desk/FrontDeskCancel";
import FrontDeskCharge from "../components/front-desk/FrontDeskCharge";
import FrontDeskRefund from "../components/front-desk/FrontDeskRefund";
import FrontDeskQuery from "../components/front-desk/FrontDeskQuery";
import Outpatient from "../pages/Outpatient";
import OutpatientIndex from "../components/outpatient/OutpatientIndex";
import OutpatientMain from "../components/outpatient/OutpatientMain";
import OutpatientInfo from "../components/outpatient/OutpatientInfo";
import OutpatientDiagnosis from "../components/outpatient/OutpatientDiagnosis";
import OutpatientTech from "../components/outpatient/OutpatientTech";
import OutpatientPrescription from "../components/outpatient/OutpatientPrescription";
import Pharmacy from "../pages/Pharmacy";
import PharmacyDispense from "../components/pharmacy/PharmacyDispense";
import PharmacyWithdrawal from "../components/pharmacy/PharmacyWithdrawal";
import PharmacyQuery from "../components/pharmacy/PharmacyQuery";
import Admin from "../pages/Admin";
import AdminIndex from "../components/admin/AdminIndex";
import AdminQuery from "../components/admin/AdminQuery";

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
                    path: 'index',
                    component: FrontDeskIndex
                },
                {
                    path: 'register',
                    component: FrontDeskRegister
                },
                {
                    path: 'cancel',
                    component: FrontDeskCancel
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
        }, {
            path: '/outpatient',
            component: Outpatient,
            children: [
                {
                    path: 'index',
                    component: OutpatientIndex
                },
                {
                    path: ':medicalRecord(\\d+)',
                    component: OutpatientMain,
                    children: [
                        {
                            path: 'info',
                            component: OutpatientInfo
                        },
                        {
                            path: 'diagnosis',
                            component: OutpatientDiagnosis
                        },
                        {
                            path: 'tech',
                            component: OutpatientTech
                        },
                        {
                            path: 'prescription',
                            component: OutpatientPrescription
                        }
                    ]
                },
            ]
        }, {
            path: '/pharmacy',
            component: Pharmacy,
            children: [
                {
                    path: 'index',
                    component: PharmacyDispense
                },
                {
                    path: 'withdrawal',
                    component: PharmacyWithdrawal
                },
                {
                    path: 'query',
                    component: PharmacyQuery
                }
            ]
        },
        {
            path: '/admin',
            component: Admin,
            children: [
                {
                    path: 'index',
                    component: AdminIndex
                },
                {
                    path: 'query',
                    component: AdminQuery
                }
            ]
        }
    ]
});

router.beforeEach((to, from, next) => {
    next()
});

export default router
