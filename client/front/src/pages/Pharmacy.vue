<template>
    <div id="pharmacy-page">
        <mu-container>
            <mu-row class="main-page">
                <mu-container>
                    <mu-tabs :value.sync="active" inverse color="primary" indicator-color="rgb(33, 150, 243)"
                             text-color="rgba(0, 0, 0, .54)">
                        <mu-tab to="/pharmacy/index">发药</mu-tab>
                        <mu-tab to="/pharmacy/withdrawal">退药</mu-tab>
                        <mu-tab to="/pharmacy/query">查询</mu-tab>
                    </mu-tabs>
                    <router-view></router-view>
                    <mu-chip class="name-chip" color="primary" delete @delete="exit">
                        <mu-icon value=":i-icon-profile"></mu-icon>
                        【{{user.title}}】{{user.name}}
                    </mu-chip>
                </mu-container>
            </mu-row>
            <mu-chip class="name-chip" color="primary" delete @delete="exit">
                <mu-icon value=":i-icon-profile"></mu-icon>
                【{{user.title}}】{{user.name}}
            </mu-chip>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "Pharmacy",
        data() {
            return {
                active:0,
                user: {
                    name: '',
                    title: '',
                    department: ''
                },
            }
        },
        methods: {
            exit() {
                lifecycle.exit();
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

            // todo 获取待取药列表
            if (!isDev)
                lifecycle.showStage("main");
        },
    }
</script>

<style scoped>
    #pharmacy-page {
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
    #front-desk-page >>> .mu-tab-wrapper, .mu-form-item {
        font-size: 15px;
    }
</style>
