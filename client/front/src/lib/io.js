// io

const io = {
    post(methodName, request) {
        if (!isDev)
            return JSON.parse(window.request.send(methodName, request));
        else {
            if (methodName === 'login')
                return JSON.parse("{\"msg\":{\"medicines\":\"{}\",\"registrationLevels\":\"{    0:{\"fee\":999.0,\"id\":0,\"limit\":999,\"name\":\"测试等级\"}}\",\"patients\":\"[]\",\"diseases\":\"{}\",\"departments\":\"{    0:{\"clazz\":3,\"id\":0,\"name\":\"测试\"}}\",\"titles\":\"{    0:{\"id\":0,\"name\":\"测试头衔\",\"registrationLevel\":0}}\",\"user\":{\"name\":\"白\",\"title\":0,\"department\":0},\"inspectionItems\":\"{}\"},\"status\":0}")
        }
    },
    receive(message) {
        // todo 根据具体message进行判断
        message = JSON.parse(message);
        return message
    }
}


export default io

