<template>
    <div id="front-desk-charge">
        <mu-container>
            <mu-row gutter>
                <mu-col span="6" offset="2">
                    <mu-form :model="chargeForm" ref="chargeForm" label-position="right" label-width="100">
                        <mu-form-item label="病历号" prop="medicalRecordID" :rules="requiredRules">
                            <mu-text-field max-length="13" v-model="chargeForm.medicalRecordID"
                                           :oninput="chargeForm.medicalRecordID = chargeForm.medicalRecordID.replace(/[^\d]/g, '')"
                                           prop="medicalRecordID"></mu-text-field>
                        </mu-form-item>
                    </mu-form>
                </mu-col>
                <mu-col span="3">
                    <div class="button-group">
                        <mu-button color="primary" @click="query">
                            查询
                        </mu-button>
                        <mu-button @click="reset">
                            重置
                        </mu-button>
                    </div>
                </mu-col>
            </mu-row>
            <mu-divider class="margin"></mu-divider>
            <mu-row>
                <mu-col span="6" offset="3">
                    <mu-list textline="two-line" v-if="chargeableList.prescriptions.length">
                        <mu-sub-header inset>处方</mu-sub-header>
                        <mu-list-item button avatar :ripple="false" v-for="(i,index) in chargeableList.prescriptions"
                                      :key="index">
                            <mu-list-item-action>
                                ￥{{i.cost.toFixed(2)}}
                            </mu-list-item-action>
                            <mu-list-item-content>
                                <mu-list-item-title>{{i.clazz ? '西药处方' : '中药处方'}}
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    处方内共{{i.medicineListSize}}种药物
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <mu-button color="primary" @click="charge(i.id,i.cost,index,0)">
                                    收费
                                </mu-button>
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <mu-divider inset v-if="chargeableList.inspectionRecords.length"></mu-divider>
                    <mu-list textline="two-line" v-if="chargeableList.inspectionRecords.length">
                        <mu-sub-header inset>检查检验项目</mu-sub-header>
                        <mu-list-item button avatar :ripple="false"
                                      v-for="(i,index) in chargeableList.inspectionRecords"
                                      :key="index">
                            <mu-list-item-action>
                                ￥{{i.cost.toFixed(2)}}
                            </mu-list-item-action>
                            <mu-list-item-content>
                                <mu-list-item-title>{{$utils.inspectionItemIDToObject(i.item).name}}
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    {{new Date(i.time).toLocaleDateString()}}
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <mu-button color="primary" @click="charge(i.id,i.cost,index,1)">
                                    收费
                                </mu-button>
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <p v-if="!chargeableList.inspectionRecords.length && !chargeableList.prescriptions.length"
                       class="empty-text">暂无可缴费项目</p>
                </mu-col>
            </mu-row>
            <mu-dialog title="缴费确认" width="360" :open.sync="confirmVisible" :overlay-close="false">
                本项目缴费应收 ￥{{temp.cost}} ，请确认缴费完毕
                <mu-button slot="actions" flat @click="confirmCharge">已缴费</mu-button>
                <mu-button slot="actions" flat color="primary" @click="closeConfirm">取消</mu-button>
            </mu-dialog>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "FrontDeskCharge",
        data() {
            return {
                chargeForm: {
                    medicalRecordID: ''
                },
                requiredRules: [{
                    validate: val => val.length === 13, message: '病历号应为13位数字'
                }],
                chargeableList: {
                    prescriptions: [],
                    inspectionRecords: []
                },
                confirmVisible: false,
                dialogContent: null,
                temp: {
                    id: null,
                    cost: null,
                    index: null,
                    type: null
                }
            }
        },
        methods: {
            query() {
                this.$refs.chargeForm.validate().then(result => {
                    if (result) {
                        let response = io.post('query-uncharged-items-by-medical-record-id', {
                            method: 'query-uncharged-items-by-medical-record-id',
                            params: {
                                id: this.chargeForm.medicalRecordID
                            }
                        });
                        if (response.status)
                            this.$toast.message(response.msg);
                        else
                            this.chargeableList = response.msg;

                    }
                });
            },
            reset() {
                this.$refs.chargeForm.clear();
                this.chargeForm = {
                    medicalRecordID: ''
                };
                this.chargeableList = {
                    prescriptions: [],
                    inspectionRecords: []
                };
                this.dialogContent = null;
            },
            charge(id, cost, index, type) {
                //type 0 - prescription/1 - inspectionRecords
                this.temp = {
                    id,
                    cost,
                    index,
                    type
                }
                this.confirmVisible = true;
            },
            closeConfirm() {
                this.confirmVisible = false;
            },
            confirmCharge() {
                this.confirmVisible = false;
                let response = io.post('front-desk-charge', {
                    method: 'front-desk-charge',
                    params: {type: this.temp.type, id: this.temp.id}
                });
                if (response.status)
                    this.$toast.message(response.msg);
                else {
                    this.$toast.success(response.msg);
                    if (this.temp.type)
                        this.chargeableList.inspectionRecords = this.chargeableList.inspectionRecords.filter(i => i.id !== this.temp.id);
                    else
                        this.chargeableList.prescriptions = this.chargeableList.prescriptions.filter(i => i.id !== this.temp.id);
                }
            }

        }
    }
</script>

<style scoped>
    #front-desk-charge {
        padding: 40px;
    }

    .empty-text {
        text-align: center;
        letter-spacing: 1px;
        margin-top: 20px;
        color: #666;
    }

    .margin {
        margin: 10px 0 20px 0;
    }

    .button-group {
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
</style>
