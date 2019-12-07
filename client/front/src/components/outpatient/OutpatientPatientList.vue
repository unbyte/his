<template>
    <div id="outpatient-patient-list">
        <mu-list textline="two-line" toggle-nested>
            <mu-list-item button :ripple="false" nested :open="open === 'wait'"
                          @toggle-nested="open = arguments[0] ? 'wait' : ''"
                          class="custom-list">
                <mu-list-item-title>
                    <mu-icon value=":i-icon-wait" color="orange"></mu-icon>
                    待诊
                </mu-list-item-title>
                <mu-list-item button :ripple="false" slot="nested" v-for="(wait,index) in waitList" :key="index"
                              :class="$store.state.outpatient.currentPatient.id===wait.id?'current':''"
                              @click="handleClickPatientInList(wait,index)"
                >
                    <mu-list-item-content>
                        <mu-list-item-title>{{wait.name}}
                            <mu-badge content="紧急" color="orange" v-if="wait.urgent"></mu-badge>
                        </mu-list-item-title>
                        <mu-list-item-sub-title style="color: rgba(0, 0, 0, .9)">{{genderMap[wait.gender]}}
                            {{$utils.birthToAge(wait.birthday)}}岁
                        </mu-list-item-sub-title>
                        <mu-list-item-sub-title>
                            No.{{wait.medicalRecord}}
                        </mu-list-item-sub-title>
                    </mu-list-item-content>
                </mu-list-item>
            </mu-list-item>
            <mu-list-item button :ripple="false" nested :open="open === 'done'"
                          @toggle-nested="open = arguments[0] ? 'done' : ''"
                          class="custom-list">
                <mu-list-item-title>
                    <mu-icon value=":i-icon-done" color="green" style="font-weight: 900"></mu-icon>
                    已诊
                </mu-list-item-title>
                <mu-list-item button :ripple="false" slot="nested" v-for="(done,index) in doneList" :key="index">
                    <mu-list-item-content>
                        <mu-list-item-title>{{done.name}}
                            <mu-badge content="紧急" color="orange" v-if="done.urgent"></mu-badge>
                        </mu-list-item-title>
                        <mu-list-item-sub-title style="color: rgba(0, 0, 0, .9)">{{genderMap[done.gender]}}
                            {{$utils.birthToAge(done.birthday)}}岁
                        </mu-list-item-sub-title>
                        <mu-list-item-sub-title>
                            No.{{done.medicalRecord}}
                        </mu-list-item-sub-title>
                    </mu-list-item-content>
                </mu-list-item>
            </mu-list-item>
        </mu-list>
        <mu-text-field v-model="searchContent" label="查询病历" label-float class="search-field" max-length="13"
                       action-icon=":i-icon-search" :action-click="search"
                       @input="searchContent = searchContent.replace(/[^\d]/g, '')"></mu-text-field>
    </div>
</template>

<script>
    export default {
        name: "OutpatientPatientList",
        data() {
            return {
                open: 'wait',
                genderMap: ['男', '女'],
                searchContent: ''
            }
        },
        computed: {
            waitList() {
                return this.$store.state.outpatient.patientList.wait;
            },
            doneList() {
                return this.$store.state.outpatient.patientList.done;
            }
        },
        methods: {
            search() {

            },
            handleClickPatientInList(patient, index) {
                if (index !== 0)
                    return;
                if (!Object.keys(this.$store.state.outpatient.currentPatient).length) {
                    this.$store.commit('setCurrentPatient', patient);
                    this.$router.push(`/outpatient/${patient.medicalRecord}/info`);
                }
            }

        }
    }
</script>

<style scoped>

    #outpatient-patient-list {
        padding-top: 40px;
        height: 100%;
        background: #fafafa;
        position: relative;
    }

    .custom-list >>> > .mu-item-wrapper .mu-item {
        height: 32px !important;
    }

    .custom-list >>> > ul .mu-item {
        margin-left: 0 !important;
    }

    .custom-list >>> > ul .current .mu-item-wrapper {
        border-left: 2px solid #2196f3;
    }

    .search-field {
        width: 100%;
        position: absolute;
        bottom: -28px;
        left: 0;
    }
</style>
