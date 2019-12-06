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
            case 8:
                return "admin";
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
    inspectionItemIDToObject(inspectionItemID) {
        return store.state.global.inspectionItems[inspectionItemID];
    },
    doctorIDToObject(doctorID) {
        return store.state.global.doctors[doctorID];
    },
    medicineIDToObject(medicineID) {
        return store.state.global.medicines[medicineID];
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
    birthToAge(birthday) {
        if (birthday == null)
            return;
        return parseInt((Date.now() - new Date(birthday).getTime() + 1000 * 60 * 60 * 24) / (1000 * 60 * 60 * 24 * 365));
    },
    isLoggedIn() {
        return !!store.state.global.user
    },
    departmentClazzMap: {
        OUTPATIENT: 0,
        PHARMACY: 1,
        MEDICAL_TECHNIQUE: 2,
        FRONT_DESK: 3,
        ADMIN: 8
    },
    statusNameMap: {
        0: '暂存',
        1: '未付费',
        2: '未消费',
        3: '已消费',
        4: '已作废',
        5: '已退费',
        6: '已作废'
    }
}

export default utils;
