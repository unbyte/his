const utils = {
    departmentClazzToRouteName(departmentClazz){
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
    }
}

export default utils;
