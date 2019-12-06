<template>
    <div id="outpatient-diagnosis">
        <mu-container>
            <mu-form :model="diagnosisForm" label-position="right" label-width="100">
                <mu-row>
                    <mu-col span="6">
                        <mu-form-item label="主诉">
                            <mu-text-field v-model="diagnosisForm.complaint" multi-line :rows="6"
                                           :disabled="diagnosisForm.status>-1"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col>
                        <mu-row>
                            <mu-col span="10">
                                <mu-form-item :label="!diagnosisForm.status?'终诊疾病':'初诊疾病'">
                                    <mu-select filterable multiple chips
                                               v-model="diagnosisForm.disease" full-width
                                               :disabled="diagnosisForm.status === 1">
                                        <mu-option v-for="(disease,index) in filteredDiseases" :key="index"
                                                   :label="disease.name"
                                                   :value="disease.id" :search-text="disease.code"></mu-option>
                                    </mu-select>
                                </mu-form-item>
                            </mu-col>
                        </mu-row>
                        <mu-row>
                            <mu-col span="6">
                                <mu-form-item prop="clazz" label="医别">
                                    <mu-select v-model.number="diagnosisForm.clazz" prop="clazz"
                                               :disabled="diagnosisForm.status>-1" full-width
                                               @change="diagnosisForm.disease=[]">
                                        <mu-option label="中" :value="0"></mu-option>
                                        <mu-option label="西" :value="1"></mu-option>
                                    </mu-select>
                                </mu-form-item>
                            </mu-col>
                            <mu-col span="4" offset="1">
                                <mu-button color="primary"
                                           @click="()=>{!diagnosisForm.status ? finalDiagnosis() :presumptiveDiagnosis()}"
                                           :disabled="diagnosisForm.status === 1" full-width>
                                    {{diagnosisForm.status===-1?'确认初诊':'确认终诊'}}
                                </mu-button>
                            </mu-col>
                        </mu-row>
                    </mu-col>
                </mu-row>
                <mu-divider></mu-divider>
                <mu-row>
                    <mu-col span="10">
                        <mu-form-item prop="presentIllnessHistory" label="最终诊断">
                            <mu-text-field prop="presentIllnessHistory" :disabled="diagnosisForm.status !== 0"
                                           v-model="diagnosisForm.judgement" multi-line :rows="6"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>
                <mu-row>
                    <mu-col span="5">
                        <mu-form-item prop="pastHistory" label="注意事项">
                            <mu-text-field prop="pastHistory" v-model="diagnosisForm.note" multi-line
                                           :disabled="diagnosisForm.status !== 0"
                                           :rows="5"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                    <mu-col span="5">
                        <mu-form-item prop="currentIllnessTreatment" label="建议">
                            <mu-text-field prop="currentIllnessTreatment"
                                           v-model="diagnosisForm.suggestion" multi-line :rows="5"
                                           :disabled="diagnosisForm.status !== 0"
                            ></mu-text-field>
                        </mu-form-item>
                    </mu-col>
                </mu-row>


            </mu-form>
        </mu-container>
    </div>
</template>

<script>
    // todo 修改疾病为病种树
    export default {
        name: "OutpatientDiagnosis",
        data() {
            return {
                diagnosisForm: {
                    id: null,
                    complaint: '',
                    clazz: 1,
                    disease: [],
                    suggestion: '',
                    note: '',
                    judgement: '',
                    status: -1
                }
            }
        },
        methods: {
            presumptiveDiagnosis() {
                if (this.diagnosisForm.status === -1) {
                    let response = io.post('outpatient-presumptive-diagnosis', {
                        method: 'outpatient-presumptive-diagnosis',
                        params: {
                            id: this.diagnosisForm.id,
                            complaint: this.diagnosisForm.complaint,
                            clazz: this.diagnosisForm.clazz,
                            disease: this.diagnosisForm.disease
                        }
                    });
                    if (response.status) {
                        this.$toast.message(response.msg);
                        return;
                    }

                    this.$toast.success(response.msg);
                    this.query();
                }
            },
            finalDiagnosis() {
                if (this.diagnosisForm.status === 0) {
                    let response = io.post('outpatient-final-diagnosis', {
                        method: 'outpatient-final-diagnosis',
                        params: {
                            id: this.diagnosisForm.id,
                            disease: this.diagnosisForm.disease,
                            suggestion: this.diagnosisForm.suggestion,
                            note: this.diagnosisForm.note,
                            judgement: this.diagnosisForm.judgement
                        }
                    });
                    if (response.status) {
                        this.$toast.message(response.msg);
                        return;
                    }
                    this.$toast.success(response.msg);
                    this.query();
                }
            },
            query() {
                let response = io.post('query-diagnosis-by-registration-id', {
                    method: 'query-diagnosis-by-registration-id',
                    params: {
                        id: this.$store.state.outpatient.currentPatient.id
                    }
                });

                if (!response.status)
                    this.diagnosisForm = response.msg;
                else
                    this.diagnosisForm.id = this.$store.state.outpatient.currentPatient.id;
            }
        },
        mounted() {
            this.query();
        },
        computed: {
            diseases() {
                return this.$store.state.global.diseases;
            },
            filteredDiseases() {
                if (!this.diseases)
                    return [];
                return Object.values(this.diseases).filter(i => i.clazz === this.diagnosisForm.clazz);
            }
        }
    }
</script>

<style scoped>
    .container {
        padding-top: 36px;
    }

    .mu-divider {
        margin: 32px 0;
    }
</style>
