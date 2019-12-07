<template>
    <div id="outpatient-history">
        <mu-container>
            <mu-row>
                <mu-col span="6" offset="3">
                    <mu-list textline="three-line" v-if="history.length">
                        <mu-list-item button :ripple="false" v-for="(i,index) in history"
                                      :key="index">
                            <mu-list-item-content>
                                <mu-list-item-title>{{i.diseaseName}}
                                </mu-list-item-title>
                                <mu-list-item-sub-title>
                                    <span>{{$utils.departmentIDToObject(i.departmentID).name}}</span><br>
                                    医生 {{i.doctorName}}
                                </mu-list-item-sub-title>
                            </mu-list-item-content>
                            <mu-list-item-action>
                                {{new Date(i.registrationTime).toLocaleDateString()}}
                            </mu-list-item-action>
                        </mu-list-item>
                    </mu-list>
                    <p v-if="!history.length"
                       class="empty-text">无历史就诊记录</p>
                </mu-col>
            </mu-row>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "OutpatientHistory",
        data() {
            return {
                history: []
            }
        },
        mounted() {
            let response = io.post('query-registrations-by-medical-record-id', {
                method: 'query-registrations-by-medical-record-id',
                params: {
                    id: this.$route.params.medicalRecord
                }
            });

            if (response.status) {
                this.$toast.message(response.msg);
                return;
            }
            this.history = response.msg;
        }
    }
</script>

<style scoped>
    .container {
        padding-top: 36px;
    }
</style>
