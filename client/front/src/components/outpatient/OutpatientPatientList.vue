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
        <mu-text-field v-model="searchContent" label="查询科室" label-float class="search-field"
                       action-icon=":i-icon-search" :action-click="search"></mu-text-field>
        <mu-dialog title="搜索结果" width="640" :open.sync="showSearch" :overlay-close="false">
            <mu-data-table stripe :columns="tableAttr" :sort.sync="sort" @sort-change="handleSortChange"
                           :data="searchResult" no-data-text="空数据" max-height="600">
                <template slot-scope="scope">
                    <td>{{scope.row.id}}</td>
                    <td>{{scope.row.name}}</td>
                    <td>{{$utils.birthToAge(scope.row.birth)}}</td>
                    <td>{{scope.row.gender ? '女':'男'}}</td>
                </template>
            </mu-data-table>
            <mu-button slot="actions" flat color="primary" @click="closeSearch">关闭</mu-button>
        </mu-dialog>
    </div>
</template>

<script>
    export default {
        name: "OutpatientPatientList",
        data() {
            return {
                open: 'wait',
                genderMap: ['男', '女'],
                searchContent: '',
                searchResult: [],
                showSearch: false,
                sort: {
                    name: '',
                    order: 'asc'
                },
                tableAttr: [
                    {title: '病历号', name: 'id', width: 192, align: 'center', sortable: true},
                    {title: '姓名', name: 'name', width: 156, align: 'center', sortable: true},
                    {title: '年龄', name: 'birth', width: 118, align: 'center', sortable: true},
                    {title: '性别', name: 'gender', width: 118, align: 'center', sortable: true},
                ],
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
                let id = this.getDepartmentID(this.searchContent);
                if (id === -1) {
                    this.$toast.info("该科室不存在");
                    return;
                }

                let response = io.post('query-medical-record-by-department-id', {
                    method: 'query-medical-record-by-department-id',
                    params: {
                        id
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                this.searchResult = response.msg;
                this.showSearch = true;
            },
            getDepartmentID(name) {
                switch (name) {
                    case "心内科":
                        return 43;
                    case "消化内科":
                        return 42;
                    case "呼吸内科":
                        return 41;
                    case "内科":
                        return 40;
                    default:
                        return -1;
                }
            },
            closeSearch() {
                this.showSearch = false;
                this.searchResult = [];
            },
            handleClickPatientInList(patient, index) {
                if (index !== 0)
                    return;
                if (!Object.keys(this.$store.state.outpatient.currentPatient).length) {
                    this.$store.commit('setCurrentPatient', patient);
                    this.$router.push(`/outpatient/${patient.medicalRecord}/info`);
                }
            },
            handleSortChange({name, order}) {
                if (name === "name")
                    this.searchResult = this.searchResult.sort((a, b) => order === 'asc' ? a[name].localeCompare(b[name], 'zh-CN') : b[name].localeCompare(a[name], 'zh-CN'));
                else
                    this.searchResult = this.searchResult.sort((a, b) => order === 'asc' ? a[name] - b[name] : b[name] - a[name]);
            },
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
