<template>
    <div id="admin-page">
        <mu-container>
            <mu-tabs :value.sync="active" inverse color="primary" indicator-color="rgb(33, 150, 243)"
                     text-color="rgba(0, 0, 0, .54)">
                <mu-tab to="/admin/index">首页</mu-tab>
                <mu-tab to="/admin/query">查询</mu-tab>
            </mu-tabs>
            <router-view v-if="isRouterAlive"></router-view>
            <mu-chip class="name-chip" color="primary" delete @delete="exit">
                <mu-icon value=":i-icon-profile"></mu-icon>
                【{{user.title}}】{{user.name}}
            </mu-chip>
        </mu-container>
    </div>
</template>

<script>
    export default {
        name: "Admin",
        data() {
            return {
                active: 0,
                user: {
                    name: '',
                    title: '',
                    department: ''
                },
                isRouterAlive: true
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

            if (!isDev)
                lifecycle.showStage("main");
        },
        methods: {
            exit() {
                lifecycle.exit();
            },
            reload() {
                this.isRouterAlive = false;
                this.$nextTick(() => (this.isRouterAlive = true))
            }
        }
    }
</script>

<style scoped>
    #admin-page {
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
