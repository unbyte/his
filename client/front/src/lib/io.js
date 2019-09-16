// io

const io = {
    post(methodName, request) {
        return JSON.parse(window.request.send(methodName, request));
    },
    receive(message) {
        // todo 根据具体message进行判断
        message = JSON.parse(message)
        return message
    }
}


export default io

