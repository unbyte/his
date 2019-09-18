// io
import mock from './mock'

const io = {
    post(methodName, request) {
        if (!isDev) {
            return eval(`(${window.request.send(methodName, request)})`);
        } else {
            if (methodName === 'login')
                return mock.loginSuccess;
        }
    },
    receive(message) {
        // todo 根据具体message进行判断
        message = JSON.parse(message);
        return message
    }
}


export default io

