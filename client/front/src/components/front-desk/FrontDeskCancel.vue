<template>
    <div id="front-desk-cancel" class="tab-page">
        <mu-container>
            <mu-row gutter>
                <mu-col span="6" offset="2">
                    <mu-form :model="cancelForm" ref="cancelForm" label-position="right" label-width="100">
                        <mu-form-item label="病历号" prop="medicalRecordID" :rules="requiredRules">
                            <mu-text-field max-length="13" v-model="cancelForm.medicalRecordID"
                                           :oninput="cancelForm.medicalRecordID = cancelForm.medicalRecordID.replace(/[^\d]/g, '')"
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
            <mu-divider></mu-divider>
            <mu-row>
                <mu-col span="6" offset="3">
                    <mu-list textline="three-line" v-if="cancelableList.length">
                        <mu-list-item button :ripple="false" v-for="(i,index) in cancelableList" :key="index">
                            <mu-list-item-content>
                                <mu-list-item-title>科室 ( {{$utils.departmentIDToObject(i.department).name}} ) 医生
                                    ( {{$utils.doctorIDToObject(i.doctor).name}} )
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    <span>{{$utils.statusNameMap[i.status]}}</span><br>
                                    {{new Date(i.time).toLocaleDateString()}}
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                <mu-button color="error" @click="cancelRegistration(i.id,index)">
                                    退号
                                </mu-button>
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <p v-if="!cancelableList.length" class="empty-text">暂无可退号的挂号记录</p>
                </mu-col>
            </mu-row>
        </mu-container>
        <mu-dialog title="退号成功" width="360" :open.sync="dialogVisible" :overlay-close="false">
            {{dialogContent && dialogContent.status === 2 ?`应退还金额为 ￥${dialogContent.cost}`:'无应退还金额'}}
            <mu-button slot="actions" flat color="primary" @click="closeDialog">Close</mu-button>
        </mu-dialog>

        <mu-dialog title="退号确认" width="360" :open.sync="confirmVisible" :overlay-close="false">
            请再次确认是否撤回本次挂号
            <mu-button slot="actions" flat @click="confirmCancelRegistration">确认</mu-button>
            <mu-button slot="actions" flat color="primary" @click="closeConfirm">取消</mu-button>
        </mu-dialog>
    </div>
</template>

<script>
    export default {
        name: "FrontDeskCancel",
        data() {
            return {
                cancelForm: {
                    medicalRecordID: ''
                },
                requiredRules: [{
                    validate: val => val.length === 13, message: '病历号应为13位数字'
                }],
                cancelableList: [],
                dialogVisible: false,
                confirmVisible: false,
                dialogContent: null,
                temp: {
                    id: null,
                    index: null
                }
            }
        },
        methods: {
            query() {
                this.$refs.cancelForm.validate().then(result => {
                    if (result) {
                        let response = io.post('query-cancelable-by-medical-record-id', {
                            method: 'query-cancelable-by-medical-record-id',
                            params: {
                                id: this.cancelForm.medicalRecordID
                            }
                        });
                        if (response.status)
                            this.$toast.message(response.msg);
                        else
                            this.cancelableList = response.msg;

                    }
                });
            },
            reset() {
                this.$refs.cancelForm.clear();
                this.cancelForm = {
                    medicalRecordID: ''
                };
                this.cancelableList = [];
                this.dialogContent = null;
            },
            cancelRegistration(id, index) {
                this.temp = {
                    id,
                    index
                };
                this.confirmVisible = true;
            },
            closeDialog() {
                this.dialogVisible = false;
            },
            closeConfirm() {
                this.confirmVisible = false;
            },
            confirmCancelRegistration() {
                this.confirmVisible = false;
                let response = io.post('front-desk-cancel', {
                    method: 'front-desk-cancel',
                    params: {id: this.temp.id}
                });
                if (response.status)
                    this.$toast.message(response.msg);
                else {
                    // 弹窗，确认退费金额
                    this.dialogContent = this.cancelableList[this.temp.index];
                    this.dialogVisible = true;
                    this.cancelableList = this.cancelableList.filter(i => i.id !== this.temp.id);
                }
            }

        }
    }
</script>

<style scoped>
    #front-desk-cancel {
        padding: 40px;
    }

    .empty-text {
        text-align: center;
        letter-spacing: 1px;
        margin-top: 20px;
        color: #666;
    }

    .mu-divider {
        margin: 10px 0 20px 0;
    }

    .button-group {
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
</style>
