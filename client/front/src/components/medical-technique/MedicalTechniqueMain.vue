<template>
    <div id="medical-technique-main">
        <mu-container>
            <mu-row gutter>
                <mu-col span="6" offset="2">
                    <mu-form :model="form" ref="refundForm" label-position="right" label-width="100">
                        <mu-form-item label="病历号" prop="medicalRecordID" :rules="requiredRules">
                            <mu-text-field max-length="13" v-model="form.medicalRecordID"
                                           :oninput="form.medicalRecordID = form.medicalRecordID.replace(/[^\d]/g, '')"
                                           prop="medicalRecordID"></mu-text-field>
                        </mu-form-item>
                    </mu-form>
                </mu-col>
                <mu-col span="3">
                    <div class="button-group">
                        <mu-button color="primary" @click="check">
                            完成
                        </mu-button>
                        <mu-button @click="reset">
                            重置
                        </mu-button>
                    </div>
                </mu-col>
            </mu-row>

        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "MedicalTechniqueMain",
        data() {
            return {
                requiredRules: [{
                    validate: val => val.length === 13, message: '病历号应为13位数字'
                }],
                form: {
                    medicalRecordID: ''
                }
            }
        },
        methods: {
            check() {
                let response = io.post('medical-technique-check', {
                    method: 'medical-technique-check',
                    params: {
                        id: this.form.medicalRecordID,
                    }
                });
                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }
                this.$toast.success(response.msg);
                this.reset();
            },
            reset() {
                this.form.medicalRecordID = ''
            }
        }
    }
</script>

<style scoped>
    .container {
        padding-top: 36px;
    }

    i {
        margin: 0 6px;
    }

    h2 {
        font-weight: 700;
        margin-bottom: 12px;
    }

    >>> .mu-table-header, >>> .mu-table-body {
        width: 100% !important;
    }

    .button-group {
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
</style>
