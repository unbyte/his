// io

const io = {
    post(methodName, request) {
        if (!isDev) {
            return eval(`(${window.request.send(methodName, request)})`);
        } else {
            if (methodName === 'login')
                return {
                    "msg": {
                        "registrationLevels": {
                            0: {"fee": 0.0, "limit": 0, "id": 0},
                            1: {"fee": 20.0, "limit": 120, "id": 1},
                            2: {"fee": 60.0, "limit": 80, "id": 2},
                            3: {"fee": 100.0, "limit": 40, "id": 3}
                        },
                        "titles": {
                            0: {"registrationLevel": 0, "name": "住院医师", "id": 0},
                            1: {"registrationLevel": 1, "name": "主治医师", "id": 1},
                            2: {"registrationLevel": 2, "name": "副主任医师", "id": 2},
                            3: {"registrationLevel": 3, "name": "主任医师", "id": 3},
                            4: {"registrationLevel": 0, "name": "初级技师", "id": 4},
                            5: {"registrationLevel": 0, "name": "主管技师", "id": 5},
                            6: {"registrationLevel": 0, "name": "副主任技师", "id": 6},
                            7: {"registrationLevel": 0, "name": "主任技师", "id": 7},
                            8: {"registrationLevel": 0, "name": "初级药师", "id": 8},
                            9: {"registrationLevel": 0, "name": "主管药师", "id": 9},
                            10: {"registrationLevel": 0, "name": "副主任药师", "id": 10},
                            11: {"registrationLevel": 0, "name": "主任药师", "id": 11},
                            12: {"registrationLevel": 0, "name": "前台接诊", "id": 12}
                        },
                        "departments": {
                            0: {"name": "前台", "id": 0, "clazz": 3},
                            1: {"name": "中药药房", "id": 1, "clazz": 1},
                            2: {"name": "西药药房", "id": 2, "clazz": 1},
                            3: {"name": "放射科", "id": 3, "clazz": 2},
                            4: {"name": "外科", "id": 4, "clazz": 0}
                        },
                        "user": {"name": "白白白", "title": 12, "department": 0}
                    }, "status": 0
                };
        }
    },
    receive(message) {
        // todo 根据具体message进行判断
        message = JSON.parse(message);
        return message
    }
}


export default io

