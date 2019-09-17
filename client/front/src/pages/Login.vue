<template>
    <div id="login-page">
        <div id="login-box">
            <mu-row>
                <mu-col span="4" class="title">
                    <div class="buttons">
                        <div class="config-button">
                            <mu-tooltip placement="right" content="设置">
                                <mu-button fab small color="primary" @click="openConfig">
                                    <mu-icon value=":i-icon-configuration"></mu-icon>
                                </mu-button>
                            </mu-tooltip>
                        </div>
                        <div class="exit-button">
                            <mu-tooltip placement="right" content="退出">
                                <mu-button fab small color="primary" @click="quitApp">
                                    <mu-icon value=":i-icon-exit"></mu-icon>
                                </mu-button>
                            </mu-tooltip>
                        </div>
                    </div>
                </mu-col>
                <mu-col span="6" offset="1" class="form">
                    <mu-form id="login-form" ref="loginForm" :model="loginForm">
                        <mu-form-item label="ID" prop="username" :rules="usernameRules">
                            <mu-text-field v-model.number="loginForm.username" prop="username"></mu-text-field>
                        </mu-form-item>
                        <mu-form-item label="密码" prop="password" :rules="passwordRules">
                            <mu-text-field type="password" v-model="loginForm.password" prop="password"></mu-text-field>
                        </mu-form-item>
                        <mu-form-item>
                            <mu-button color="primary" @click="submit">登陆</mu-button>
                            <mu-button @click="clear">重置</mu-button>
                        </mu-form-item>
                    </mu-form>
                </mu-col>
            </mu-row>
        </div>
        <mu-dialog title="设置" width="400" max-width="80%" :esc-press-close="false" :overlay-close="false"
                   :open.sync="configVisible">
            <mu-form ref="configForm" :model="configForm">
                <mu-form-item label="服务器地址" prop="serverAddress" :rules="addressRules">
                    <mu-text-field v-model="configForm.serverAddress" prop="serverAddress"></mu-text-field>
                </mu-form-item>
                <mu-form-item label="服务器端口" prop="serverPort" :rules="portRules">
                    <mu-text-field v-model.number="configForm.serverPort" prop="serverPort"></mu-text-field>
                </mu-form-item>
            </mu-form>
            <mu-button slot="actions" flat color="primary" @click="saveConfig">保存</mu-button>
            <mu-button slot="actions" flat @click="closeConfig">取消</mu-button>
        </mu-dialog>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                usernameRules: [
                    {validate: (val) => !!val || val === 0, message: '必须填写ID'},
                    {validate: (val) => Math.floor(val) === val, message: 'ID格式不正确'}
                ],
                passwordRules: [
                    {validate: (val) => !!val, message: '必须填写密码'}
                ],
                addressRules: [
                    {validate: (val) => !!val, message: '必须不为空'},
                    {
                        validate: (val) => /^((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}$/.test(val),
                        message: 'ip地址格式不正确'
                    }
                ],
                portRules: [
                    {validate: (val) => Math.floor(val) === val && val > 0 && val < 65535, message: '端口格式不正确'}
                ],
                loginForm: {
                    username: null,
                    password: ''
                },
                configForm: {
                    serverAddress: isDev ? '127.0.0.1' : config.getConfig('server_address'),
                    serverPort: isDev ? 30000 : parseInt(config.getConfig('server_port'))
                },
                configVisible: false
            };
        },
        methods: {
            submit() {
                this.$refs.loginForm.validate().then((result) => {
                    if (result) {
                        let response = io.post('login', JSON.stringify(this.loginForm));
                        if (response.status === 0) {
                            // 登陆成功
                            this.$store.commit("setGlobalData", response.msg);
                            this.$router.push(`/${this.$utils.departmentClazzToRouteName(response.msg.departments[response.msg.user.department].clazz)}/main`);
                        } else
                            this.$toast.message(response.msg);
                    }
                });
            },
            clear() {
                this.$refs.loginForm.clear();
                this.loginForm = {
                    username: '',
                    password: ''
                };
            },
            quitApp() {
                if (!isDev)
                    lifecycle.exit()
            },
            openConfig() {
                this.configVisible = true
            },
            closeConfig() {
                this.configVisible = false
            },
            saveConfig() {
                this.$refs.configForm.validate().then((result) => {
                    if (result) {
                        if (!isDev) {
                            config.setConfig('server_address', this.configForm.serverAddress);
                            config.setConfig('server_port', this.configForm.serverPort);
                        }
                        this.configVisible = false
                    }
                })
            }
        },
        mounted() {

        }
    }
</script>

<style scoped>
    #login-page {
        display: flex;
        width: 100%;
        height: 100%;
        justify-content: center;
        align-items: center;
    }


    #login-box {
        height: 360px;
        width: 700px;
        color: #fff;
        background: #fff;
        box-shadow: 0 0 14px 0 #dde9f3;
    }


    .form {
        padding: 20px;
        height: 360px;
        display: flex;
        align-items: center;
    }

    .title {
        height: 360px;
        background: #2196f3;
    }

    .buttons {
        position: absolute;
        bottom: 0px;
        margin: 10px;
        width: 120px;
    }

    .buttons > div {
        margin-top: 20px;
    }


    >>> .mu-form-item:last-child .mu-form-item-content {
        display: flex;
        justify-content: space-around;
    }

    >>> .mu-form-item:last-child {
        margin: 20px 0 -40px 0;
    }

    >>> .mu-dialog-body {
        height: 180px;
    }

    >>> .mu-dialog-title {
        padding: 12px 24px;
    }

</style>
