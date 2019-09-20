// io
import mock from './mock'

const io = {
    post(methodName, request) {
        if (!isDev) {
            return eval(`(${window.request.send(methodName, JSON.stringify(request))})`);
        } else {
            console.log(mock.queryCancelableByMedicalRecordID);
            switch (methodName) {
                case 'login':
                    return mock.loginSuccess;
                case 'front-desk-register-new':
                    return mock.registerWithNewMedicalRecordSuccess;
                case 'front-desk-register-exist':
                    return mock.registerWithExistMedicalRecordSuccess;
                case 'query-medical-record-by-id':
                    return mock.queryMedicalRecordByIDSuccess;
                case 'query-cancelable-by-medical-record-id':
                    return mock.queryCancelableByMedicalRecordID;
                case 'front-desk-cancel':
                    return mock.cancelSuccess;
            }

        }
    },
    receive(message) {
        // todo 根据具体message进行判断
        message = JSON.parse(message);
        return message
    }
}


export default io

