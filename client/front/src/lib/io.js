// io
import mock from './mock'

const io = {
    post(methodName, request) {
        if (!isDev) {
            return eval(`(${window.request.send(methodName, request)})`);
        } else {
            switch (methodName) {
                case 'login':
                    return mock.loginSuccess;
                case 'front-desk-register-new':
                    return mock.registerWithNewMedicalRecordSuccess;
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

