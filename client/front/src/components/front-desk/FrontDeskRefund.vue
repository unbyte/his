<template>
    <div id="front-desk-refund">
        <mu-container>
            <mu-row gutter>
                <mu-col span="6" offset="2">
                    <mu-form :model="refundForm" ref="refundForm" label-position="right" label-width="100">
                        <mu-form-item label="病历号" prop="medicalRecordID" :rules="requiredRules">
                            <mu-text-field max-length="13" v-model="refundForm.medicalRecordID"
                                           :oninput="refundForm.medicalRecordID = refundForm.medicalRecordID.replace(/[^\d]/g, '')"
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
                    <mu-list textline="three-line" v-if="paidList.prescriptions.length">
                        <mu-sub-header inset>处方</mu-sub-header>
                        <mu-list-item button :ripple="false" v-for="(i,index) in paidList.prescriptions"
                                      :key="index">
                            <mu-list-item-content>
                                <mu-list-item-title>{{i.clazz ? '西药处方' : '中药处方'}}
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    <span>处方内共{{i.medicineListSize}}种药物</span><br>
                                    {{$utils.statusNameMap[i.status]}}
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <mu-button color="primary" :disabled="i.status!==4"
                                           @click="refund(i.id,i.cost,index,0)">
                                    退费
                                </mu-button>
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <mu-divider inset v-if="paidList.inspectionRecords.length"></mu-divider>
                    <mu-list textline="three-line" v-if="paidList.inspectionRecords.length">
                        <mu-sub-header inset>检查检验项目</mu-sub-header>
                        <mu-list-item button :ripple="false"
                                      v-for="(i,index) in paidList.inspectionRecords"
                                      :key="index">
                            <mu-list-item-content>
                                <mu-list-item-title>{{$utils.inspectionItemIDToObject(i.item).name}}
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    <span>{{new Date(i.time).toLocaleDateString()}}</span><br>
                                    {{$utils.statusNameMap[i.status]}}
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <mu-button color="primary" :disabled="i.status!==4"
                                           @click="refund(i.id,i.cost,index,1)">
                                    退费
                                </mu-button>
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <p v-if="!paidList.inspectionRecords.length && !paidList.prescriptions.length"
                       class="empty-text">暂无可退费项目</p>
                </mu-col>
            </mu-row>
            <mu-dialog title="退费成功" width="360" :open.sync="dialogVisible" :overlay-close="false">
                {{`应退还金额 ￥${temp.cost}`}}
                <mu-button slot="actions" flat color="primary" @click="closeDialog">已退还</mu-button>
            </mu-dialog>
            <mu-dialog title="退费确认" width="360" :open.sync="confirmVisible" :overlay-close="false">
                <mu-button slot="actions" flat @click="confirmRefund">确认退费</mu-button>
                <mu-button slot="actions" flat color="primary" @click="closeConfirm">取消</mu-button>
            </mu-dialog>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "FrontDeskRefund",
        data() {
            return {
                refundForm: {
                    medicalRecordID: ''
                },
                requiredRules: [{
                    validate: val => val.length === 13, message: '病历号应为13位数字'
                }],
                paidList: {
                    prescriptions: [],
                    inspectionRecords: []
                },
                confirmVisible: false,
                dialogVisible: false,
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
                this.$refs.refundForm.validate().then(result => {
                    if (result) {
                        let response = io.post('query-paid-items-by-medical-record-id', {
                            method: 'query-paid-items-by-medical-record-id',
                            params: {
                                id: this.refundForm.medicalRecordID
                            }
                        });
                        if (response.status)
                            this.$toast.message(response.msg);
                        else
                            this.paidList = response.msg;

                    }
                });
            },
            reset() {
                this.$refs.refundForm.clear();
                this.refundForm = {
                    medicalRecordID: ''
                };
                this.paidList = {
                    prescriptions: [],
                    inspectionRecords: []
                };
            },
            refund(id, cost, index, type) {
                //type 0 - prescription/1 - inspectionRecords
                this.temp = {
                    id,
                    cost,
                    index,
                    type
                };
                this.confirmVisible = true;
            },
            closeConfirm() {
                this.confirmVisible = false;
            },
            closeDialog() {
                this.dialogVisible = false;
            },
            confirmRefund() {
                this.confirmVisible = false;
                let response = io.post('front-desk-refund', {
                    method: 'front-desk-refund',
                    params: {type: this.temp.type, id: this.temp.id}
                });
                if (response.status)
                    this.$toast.message(response.msg);
                else {
                    this.$toast.success(response.msg);
                    if (this.temp.type)
                        this.paidList.inspectionRecords = this.paidList.inspectionRecords.filter(i => i.id !== this.temp.id);
                    else
                        this.paidList.prescriptions = this.paidList.prescriptions.filter(i => i.id !== this.temp.id);
                }
            }
        }
    }
</script>

<style scoped>
    #front-desk-refund {
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
