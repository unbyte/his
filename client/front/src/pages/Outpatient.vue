<template>
    <div id="outpatient-page">
        <mu-container>
            <mu-row class="main-page">
                <!--逻辑是，没选中患者，左边显示时钟，选中了，左边显示主界面-->
                <!--左侧是主界面-->
                <mu-col span="10">
                    <router-view></router-view>
                </mu-col>
                <!--右侧是患者列表-->
                <mu-col span="2" class="full-height">
                    <OutpatientPatientList></OutpatientPatientList>
                </mu-col>
            </mu-row>
            <mu-chip class="name-chip" color="primary" delete @delete="exit">
                <mu-icon value=":i-icon-profile"></mu-icon>
                【{{user.title}}】{{user.name}}
            </mu-chip>
        </mu-container>
    </div>
</template>

<script>
    import OutpatientPatientList from "../components/outpatient/OutpatientPatientList";

    export default {
        name: "Outpatient",
        data() {
            return {
                user: {
                    name: '',
                    title: '',
                    department: ''
                },
            }
        },
        mounted() {
            if (!this.$utils.isLoggedIn()) {
                this.$router.push('/');
                return;
            }
            this.user.name = this.$utils.getCurrentName();
            this.user.title = this.$utils.getCurrentTitleName();
            this.user.department = this.$utils.getCurrentDepartmentName();

            // 初始化加入到待诊列表
            this.$store.commit('setWaitList',this.$store.state.global.patients);

            if (!isDev)
                lifecycle.showStage("main");
        },
        methods: {
            exit() {
                lifecycle.exit();
            }
        },
        components: {
            OutpatientPatientList
        }
    }
</script>

<style scoped>
    #outpatient-page {
        display: flex;
        width: 100%;
        height: 100%;
        justify-content: center;
        align-items: center;
    }

    .container {
        position: relative;
        min-width: 1280px;
        height: 800px;
        padding: 0 !important;
        border: 1px solid #ccc;
    }

    .name-chip {
        position: absolute;
        right: 12px;
        top: 8px;
        z-index: 999;
    }

    /*为了各个子页面单独配，直接设置一个class*/
    .tab-page {
        position: relative;
        height: 100%;
        width: 100%;
    }


    /*强制取消掉自适应，防止宽度改变，下划线长度不够*/
    .mu-tab {
        min-width: 160px !important;
    }

    /*改变字体大小*/
    #outpatient-page >>> .mu-tab-wrapper, .mu-form-item {
        font-size: 15px;
    }

    .main-page {
        width: 100%;
        height: 100%;
    }

    .full-height {
        height: 100%;
    }

</style>
