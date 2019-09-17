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
    departmentIDToName(departmentID) {
        return store.state.global.departments[departmentID].name;
    },
    titleIDToName(titleID) {
        return store.state.global.titles[titleID].name;
    },
    getCurrentName() {
        return store.state.global.user.name;
    },
    getCurrentTitleName() {
        return this.titleIDToName(store.state.global.user.title);
    },
    getCurrentDepartmentName() {
        return this.departmentIDToName(store.state.global.user.department);
    },
    isLoggedIn(){
        return !!store.state.global.user
    }
}

export default utils;
