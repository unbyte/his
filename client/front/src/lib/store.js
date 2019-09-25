import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        global: {},
        outpatient: {
            patientList: {
                wait: [],
                done: []
            },
            currentPatient: {},
            currentMedicalRecord: {}
        },
        connectionStatus: true
    },
    mutations: {
        setGlobalData(state, globalData) {
            state.global = globalData;
        },
        setWaitList(state, patientList) {
            // 拉取的列表按时间倒序，得reverse一下
            state.outpatient.patientList.wait = patientList.reverse();
        },
        addWaitList(state, waitList) {
            state.outpatient.patientList.wait.push(waitList);
        },
        removeWaitList(state, id) {
            state.outpatient.patientList.wait = state.outpatient.patientList.wait.filter(i => i.id !== id);
        },
        addDoneList(state, doneList) {
            state.outpatient.patientList.done.push(doneList);
        },
        setCurrentPatient(state, patient) {
            state.outpatient.currentPatient = patient;
        },
        setCurrentMedicalRecord(state, medicalRecord) {
            state.outpatient.currentMedicalRecord = medicalRecord;
        },
        clearOutpatientCurrent(state) {
            state.outpatient.currentMedicalRecord = {};
            state.outpatient.currentPatient = {};
        },
        moveWaitToDone(state) {
            state.outpatient.patientList.done.unshift(state.outpatient.patientList.wait.shift());
        },
        disconnected(state){
            state.connectionStatus = false;
        }
    },
    actions: {}
})

export default store
