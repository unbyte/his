// io
//todo mock
// import mock from './mock'
import store from './store'

const io = {
    post(methodName, request) {
        // if (!isDev) {
            return eval(`(${window.request.send(methodName, JSON.stringify(request))})`);
        // } else {
        //     switch (methodName) {
        //         case 'login':
        //             // return mock.loginSuccess;
        //             return mock.loginDoctorSuccess;
        //         // return mock.loginAdminSuccess;
        //         // return mock.loginTechniqueSuccess;
        //         case 'front-desk-register-new':
        //             return mock.registerWithNewMedicalRecordSuccess;
        //         case 'front-desk-register-exist':
        //             return mock.registerWithExistMedicalRecordSuccess;
        //         case 'query-medical-record-by-id':
        //             // return mock.queryMedicalRecordByIDSuccess;
        //             return mock.queryMedicalRecordInfo;
        //         case 'query-cancelable-by-medical-record-id':
        //             return mock.queryCancelableByMedicalRecordID;
        //         case 'front-desk-cancel':
        //             return mock.cancelSuccess;
        //         case 'outpatient-update-medical-record-info':
        //             return mock.updateMedicalRecordInfoSuccess;
        //         case 'query-diagnosis-by-registration-id':
        //             return {status: 1, msg: ''};
        //         case 'outpatient-complete':
        //             return {status: 0, msg: '成功'};
        //         case 'query-prescriptions-by-registration-id':
        //             return mock.fetchPrescriptionSuccess;
        //         case 'query-registrations-by-medical-record-id':
        //             return mock.queryAllRegistrationSuccess;
        //         case 'query-medical-record-by-department-id':
        //             return mock.searchSuccess;
        //     }
        // }
        // todo 打包前注释掉
    },
    receive(message) {
        // todo 根据具体message进行判断
        let pushContent = eval(`(${message})`);
        switch (pushContent.type) {
            case 'new registration':
                // store.commit("addWaitList", pushContent.msg);
                store.commit("setWaitList", pushContent.msg);
                break;
            case 'cancel registration':
                store.commit("removeWaitList", pushContent.msg.id);
                break;
            case 'disconnected':
                store.commit('disconnected');
        }
    }
}


export default io

