<template>
    <div id="outpatient-main">
        <mu-container>
            <mu-tabs :value.sync="active" inverse color="primary" indicator-color="rgb(33, 150, 243)"
                     text-color="rgba(0, 0, 0, .54)">
                <mu-tab to="info">病历信息</mu-tab>
                <mu-tab to="diagnosis">诊断管理</mu-tab>
                <mu-tab to="tech">医技项目</mu-tab>
                <mu-tab to="prescription">处方管理</mu-tab>
                <mu-tab to="history">历史就诊</mu-tab>
            </mu-tabs>
            <router-view></router-view>
            <mu-button round small color="primary" class="done-button" @click="complete">
                <mu-icon value=":i-icon-done"></mu-icon>
                完成门诊
            </mu-button>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "OutpatientMain",
        data() {
            return {
                active: 0,
            }
        },
        methods: {
            complete() {
                let response = io.post('outpatient-complete', {
                    method: 'outpatient-complete',
                    params: {
                        id: this.$store.state.outpatient.currentPatient.id
                    }
                });

                if (response.status) {
                    this.$toast.message(response.msg);
                    return;
                }

                this.$toast.success(response.msg);

                this.$store.commit('moveWaitToDone');

                this.$store.commit('clearOutpatientCurrent');

                this.$router.push('/outpatient/index');
            }
        },
        mounted() {

        },
    }
</script>

<style scoped>
    #outpatient-main {
        display: flex;
        width: 100%;
        height: 100%;
        justify-content: center;
        align-items: center;
    }

    .container {
        position: relative;
        padding: 0 !important;
    }

    .done-button {
        position: absolute;
        top: 8px;
        right: 48px;
        z-index: 999;
    }
</style>
