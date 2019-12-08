<template>
    <!--todo 页面逻辑需要修改！重写吧-->
    <div id="outpatient-prescription">
        <mu-container>
            <mu-form :model="addForm" label-position="right" label-width="100">
                <mu-row>
                    <mu-col span="8">
                        <mu-form-item label="药品">
                            <mu-select filterable v-model="addForm.medicineID" full-width>
                                <mu-option v-for="(medicine,index) in medicines" :key="index"
                                           :label="medicine.name"
                                           :value="medicine.id" :search-text="medicine.code"></mu-option>
                            </mu-select>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="4">
                        <mu-form-item label="数量">
                            <mu-text-field
                                    v-model="addForm.amount"
                                    type="number"
                                    @input="addForm.amount = addForm.amount<0 ?  0:addForm.amount"></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>
                <mu-row>
                    <mu-col span="4">
                        <mu-form-item label="用量">
                            <mu-text-field
                                    v-model="addForm.dosage"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="4">
                        <mu-form-item label="用法">
                            <mu-text-field
                                    v-model="addForm.usage"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="4">
                        <mu-form-item label="频次">
                            <mu-text-field
                                    v-model="addForm.frequency"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>
            </mu-form>
            <mu-row>
                <mu-col span="7" offset="1">
                    <mu-data-table :columns="columns" :data="medicineList">
                        <template slot="expand" slot-scope="scope">
                            <div style="padding: 12px 0 12px 24px;">
                                <p>用量:{{scope.row.dosage}}</p>
                                <p>用法:{{scope.row.usage}}</p>
                                <p>频次:{{scope.row.frequency}}</p>
                            </div>
                        </template>
                        <template slot-scope="scope">
                            <td>{{medicines[scope.row.medicineID].name}}</td>
                            <td class="is-right">{{medicines[scope.row.medicineID].format}}</td>
                            <td class="is-right">{{scope.row.amount}}</td>
                            <td class="is-right">{{medicines[scope.row.medicineID].price * scope.row.amount}}</td>
                            <td class="is-right">
                                <mu-button color="error" small @click="deleteFromMedicineList(scope.row.medicineID)">删除
                                </mu-button>
                            </td>
                        </template>
                    </mu-data-table>
                </mu-col>
                <mu-col>
                    <mu-row>
                        <mu-col span="10" offset="1">
                            <mu-text-field
                                    label-float
                                    label="处方名称"
                                    full-width
                                    v-model="currentPrescriptionName"
                            ></mu-text-field>
                        </mu-col>
                    </mu-row>
                    <mu-row>
                        <mu-col span="4" offset="1">
                            <mu-button full-width color="primary" @click="addMedicine">添加药品</mu-button>
                        </mu-col>
                        <mu-col span="4" offset="2">
                            <mu-button full-width color="primary" @click="tempSave"
                                       :disabled="!this.medicineList.length || this.currentPrescriptionStatus >0">暂存处方
                            </mu-button>
                        </mu-col>
                    </mu-row>
                    <mu-row style="margin-top:20px;">
                        <mu-col span="10" offset="1">
                            <mu-button full-width color="primary" @click="publish"
                                       :disabled="!this.medicineList.length || this.currentPrescriptionStatus >1">开立处方
                            </mu-button>
                        </mu-col>
                    </mu-row>
                    <mu-row>
                        <mu-list textline="two-line" v-if="existPrescriptions.length">
                            <mu-list-item button :ripple="false"
                                          v-for="(i,index) in existPrescriptions"
                                          :key="index">
                                <mu-list-item-content>
                                    <mu-list-item-title>{{i.name}}
                                    </mu-list-item-title>
                                    <mu-list-item-sub-title>
                                        处方内共{{i.medicineList.length}}种药物
                                    </mu-list-item-sub-title>
                                    <mu-list-item-sub-title>
                                        {{getStatusText(i.status)}}
                                    </mu-list-item-sub-title>
                                </mu-list-item-content>
                                <mu-list-item-action>
                                    <mu-button color="primary"
                                               :disabled="i.status === 4 ||  i.id === currentPrescriptionID"
                                               @click="()=>{i.status?cancel(i.id):edit(i.id)}">
                                        {{getButtonText(i.status,index)}}
                                    </mu-button>
                                </mu-list-item-action>
                            </mu-list-item>
                        </mu-list>
                    </mu-row>
                </mu-col>
            </mu-row>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "OutpatientPrescription",
        data() {
            return {
                addForm: {
                    medicineID: null,
                    dosage: '',
                    usage: '',
                    frequency: '',
                    amount: 1,
                },
                medicineList: [],
                currentPrescriptionID: -1,
                currentPrescriptionStatus: -1,
                currentPrescriptionName: '',
                columns: [
                    {title: '药品名', width: 126,},
                    {title: '规格', width: 120, align: 'center'},
                    {title: '数量', width: 120, align: 'center'},
                    {title: '费用', width: 86, align: 'center'},
                    {title: '操作', width: 120, align: 'center'},
                ],
                existPrescriptions: []
            }
        },
        methods: {
            reset() {
                this.fetchExist();
                this.medicineList = [];
                this.currentPrescriptionID = -1;
                this.currentPrescriptionStatus = -1;
                this.currentPrescriptionName = '';
            },
            edit(id) {
                let tmp = this.existPrescriptions.filter(i => i.id === id)[0];
                this.medicineList = tmp.medicineList;
                this.currentPrescriptionID = tmp.id;
                this.currentPrescriptionStatus = tmp.status;
                this.currentPrescriptionName = tmp.name;
            },
            cancel(id) {
                let response = io.post('outpatient-cancel-prescription', {
                    method: 'outpatient-cancel-prescription',
                    params: {id}
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                this.$toast.success(response.msg);
                this.reset();
            },
            addMedicine() {
                if (!this.addForm.medicineID && this.addForm.medicineID !== 0) {
                    this.$toast.message("未指定添加药物");
                    return;
                }
                if (this.medicineList.length === 5) {
                    this.$toast.message("一个药单中最多包含5种药物");
                    return;
                }

                this.medicineList.push(this.addForm);
                this.addForm = {
                    medicineID: null,
                    dosage: '',
                    usage: '',
                    frequency: '',
                    amount: 1,
                }
            },
            deleteFromMedicineList(id) {
                this.medicineList = this.medicineList.filter(i => i.medicineID !== id);
            },
            tempSave() {
                if (this.currentPrescriptionName.trim() === "") {
                    this.$toast.message("请填写处方名称");
                    return;
                }
                let response = io.post('outpatient-save-temp-prescription', {
                        method: 'outpatient-save-temp-prescription',
                        params: {
                            id: this.currentPrescriptionID,
                            medicalRecord: this.$route.params.medicalRecord,
                            medicineList: this.medicineList,
                            registration: this.$store.state.outpatient.currentPatient.id,
                            name: this.currentPrescriptionName,
                            clazz: 1//todo 临时写死
                        }
                    })
                ;

                if (response.status) {
                    this.$toast.message(response.msg)
                    return;
                }

                this.$toast.success(response.msg);
                this.reset();
            },
            publish() {
                if (this.currentPrescriptionName.trim() === "") {
                    this.$toast.message("请填写处方名称");
                    return;
                }
                let response = io.post('outpatient-publish-prescription', {
                    method: 'outpatient-publish-prescription',
                    params: {
                        id: this.currentPrescriptionID,
                        medicalRecord: this.$route.params.medicalRecord,
                        medicineList: this.medicineList,
                        registration: this.$store.state.outpatient.currentPatient.id,
                        name: this.currentPrescriptionName,
                        clazz: 1//todo 临时写死
                    }
                });

                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                this.$toast.success(response.msg);
                this.reset();
            },
            fetchExist() {
                let response = io.post('query-prescriptions-by-registration-id', {
                    method: 'query-prescriptions-by-registration-id',
                    params: {
                        id: this.$store.state.outpatient.currentPatient.id,
                    }
                });

                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                this.existPrescriptions = response.msg;
            },
            getButtonText(status, index) {
                switch (status) {
                    case 4:
                        return "已作废";
                    case 0:
                        return this.existPrescriptions[index].id === this.currentPrescriptionID ? "编辑中" : "编辑";
                    default:
                        return "作废";

                }
            },
            getStatusText(status) {
                switch (status) {
                    case 4:
                        return "已作废";
                    case 0:
                        return "暂存";
                    default:
                        return "已开立";
                }
            }
        },
        computed: {
            medicines() {
                return this.$store.state.global.medicines;
            }
        },
        mounted() {
            this.fetchExist();
        }
    }
</script>

<style scoped>
    .container {
        padding: 36px;
    }
</style>
