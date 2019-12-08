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
            let cu = state.outpatient.currentPatient;
            if (Object.keys(cu).length)
                patientList = patientList.filter(i => i.id !== cu.id);
            patientList.sort((a, b) => a.power - b.power);
            state.outpatient.patientList.wait = Object.keys(cu).length ? [cu].concat(patientList) : patientList;
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
        disconnected(state) {
            state.connectionStatus = false;
        }
    },
    actions: {}
})

export default store
