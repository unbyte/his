import store from './store'

const utils = {
    departmentClazzToRouteName(departmentClazz) {
        switch (departmentClazz) {
            case 0:
                return "outpatient";
            case 1:
                return "pharmacy";
            case 2:
                return "medical-technique";
            case 3:
                return "front-desk";
        }
    },
    departmentIDToObject(departmentID) {
        return store.state.global.departments[departmentID];
    },
    titleIDToObject(titleID) {
        return store.state.global.titles[titleID];
    },
    registrationLevelIDToObject(registrationLevelID) {
        return store.state.global.registrationLevels[registrationLevelID];
    },
    doctorIDToObject(doctorID) {
        return store.state.global.doctors[doctorID];
    },
    titleIDToLimit(titleID) {
        return this.registrationLevelIDToObject(this.titleIDToObject(titleID).registrationLevel).limit;
    },
    titleIDToFee(titleID) {
        return this.registrationLevelIDToObject(this.titleIDToObject(titleID).registrationLevel).fee;
    },
    getCurrentName() {
        return store.state.global.user.name;
    },
    getCurrentTitleName() {
        return this.titleIDToObject(store.state.global.user.title).name;
    },
    getCurrentDepartmentName() {
        return this.departmentIDToObject(store.state.global.user.department).name;
    },
    isLoggedIn() {
        return !!store.state.global.user
    },
    departmentClazzMap: {
        OUTPATIENT: 0,
        PHARMACY: 1,
        MEDICAL_TECHNIQUE: 2,
        FRONT_DESK: 3
    },
    statusNameMap: {
        0: '暂存',
        1: '未付费',
        2: '未消费',
        3: '已消费',
        4: '已作废',
        5: '已退费'
    }
}

export default utils;
