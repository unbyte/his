const mock = {
    loginSuccess: {
        "msg": {
            "registrationLevels": {
                0: {"fee": 0.0, "limit": 0, "id": 0},
                1: {"fee": 20.0, "limit": 120, "id": 1},
                2: {"fee": 60.0, "limit": 80, "id": 2},
                3: {"fee": 100.0, "limit": 40, "id": 3}
            },
            "doctors": {
                4: {"name": "红红红", "id": 4, "department": 4, "title": 1},
                5: {"name": "绿绿绿", "id": 5, "department": 4, "title": 2},
                6: {"name": "黄黄黄", "id": 6, "department": 4, "title": 3},
                7: {"name": "青青青", "id": 7, "department": 5, "title": 1},
                8: {"name": "紫紫紫", "id": 8, "department": 5, "title": 1}
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
                4: {"name": "外科", "id": 4, "clazz": 0},
                5: {"name": "消化科", "id": 5, "clazz": 0}
            },
            "inspectionItems": {
                0: {"code": "PTTS", "departmentID": 3, "fee": 5.0, "id": 0, "name": "普通透视"},
                1: {"code": "SGBCTS", "departmentID": 3, "fee": 15.0, "id": 1, "name": "食管钡餐透视"}
            },
            "user": {"name": "白白白", "title": 12, "department": 0}
        }, "status": 0
    },
    registerWithNewMedicalRecordSuccess: {
        "msg": {"registrationID": 1568916278621, "medicalRecordID": 1568916278620, "cost": 100},
        "status": 0
    },
    registerWithExistMedicalRecordSuccess: {
        "msg": {"registrationID": 1568916278621, "medicalRecordID": 1568916278620, "cost": 100},
        "status": 0
    },
    cancelSuccess: {
        "msg": "退号成功",
        "status": 0
    },
    queryMedicalRecordByIDSuccess: {
        "msg": {
            "birthday": 1558916278620,
            "address": "北京",
            "gender": 0,
            "name": "爱新觉罗",
            "id": 1568976155713,
            "IDNumber": "011110011110011110"
        }, "status": 0
    },
    queryCancelableByMedicalRecordID: {
        "msg": [
            {
                "id": 100,
                "time": 1569005142057,
                "doctor": 4,
                "department": 4,
                "cost": 21,
                "status": 1
            }
        ], "status": 0
    }


}


export default mock;
