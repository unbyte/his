<template>
    <div id="outpatient-info">
        <mu-container>
            <mu-form :model="medicalRecordInfo" label-position="right" label-width="100">
                <mu-row gutter>
                    <mu-col span="3">
                        <mu-form-item prop="name" label="病历号">
                            <mu-text-field prop="name" :value="medicalRecordInfo.id" disabled></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="3">
                        <mu-form-item prop="name" label="姓名">
                            <mu-text-field prop="name" :value="medicalRecordInfo.name" disabled></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="2">
                        <mu-form-item prop="gender" label="性别">
                            <mu-text-field prop="name" :value="medicalRecordInfo.gender?'女':'男'"
                                           disabled></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="2">
                        <mu-form-item prop="age" label="年龄">
                            <mu-text-field prop="age" :value="getAge(medicalRecordInfo.birthday)"
                                           disabled></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="2" style="text-align: center;">
                        <mu-button color="primary" @click="updateMedicalRecordInfo">保存</mu-button>
                    </mu-col>
                </mu-row>
                <mu-divider style="margin: 28px 0;"></mu-divider>
                <mu-row>
                    <mu-col span="5">
                        <mu-form-item prop="presentIllnessHistory" label="现病史">
                            <mu-text-field prop="presentIllnessHistory"
                                           v-model="medicalRecordInfo.presentIllnessHistory" multi-line :rows="10"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="5" offset="1">
                        <mu-form-item prop="currentIllnessTreatment" label="治疗情况">
                            <mu-text-field prop="currentIllnessTreatment"
                                           v-model="medicalRecordInfo.currentIllnessTreatment" multi-line :rows="10"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>
                <mu-row>
                    <mu-col span="5">
                        <mu-form-item prop="pastHistory" label="既往史">
                            <mu-text-field prop="pastHistory" v-model="medicalRecordInfo.pastHistory" multi-line
                                           :rows="10"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="5" offset="1">
                        <mu-form-item prop="allergyHistory" label="过敏史">
                            <mu-text-field prop="allergyHistory" v-model="medicalRecordInfo.allergyHistory" multi-line
                                           :rows="10"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>
            </mu-form>
        </mu-container>
    </div>
</template>

<script>

    export default {
        name: "OutpatientInfo",
        data() {
            return {
                medicalRecordInfo: {}
            }
        },
        methods: {
            getAge(birthday) {
                if (birthday == null)
                    return;
                return parseInt((Date.now() - new Date(birthday).getTime() + 1000 * 60 * 60 * 24) / (1000 * 60 * 60 * 24 * 365));
            },
            updateMedicalRecordInfo() {
                if (!Object.keys(this.medicalRecordInfo).length)
                    return;
                let {id, pastHistory, allergyHistory, currentIllnessTreatment, presentIllnessHistory} = this.medicalRecordInfo;
                let response = io.post('outpatient-update-medical-record-info', {
                    method: 'outpatient-update-medical-record-info',
                    params: {
                        id, pastHistory, allergyHistory, currentIllnessTreatment, presentIllnessHistory
                    }
                });
                if (response.status)
                    this.$toast.message(response.msg);
                 else {
                    this.$toast.success(response.msg);
                    this.$store.commit('setCurrentMedicalRecord', this.medicalRecordInfo);
                }
            }
        },
        mounted() {
            let tmp = this.$store.state.outpatient.currentMedicalRecord;
            if (Object.keys(tmp).length) {
                this.medicalRecordInfo = tmp;
            } else {
                // 根据病历id获取病历对象
                let response = io.post('query-medical-record-by-id', {
                    method: 'query-medical-record-by-id',
                    params: {id: this.$route.params.medicalRecord}
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }
                this.$store.commit('setCurrentMedicalRecord', response.msg);
                this.medicalRecordInfo = response.msg;
            }
        },
        computed: {}
    }
</script>

<style scoped>
    .container {
        padding-top: 36px;
    }
</style>
