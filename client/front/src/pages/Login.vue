<template>
    <div id="login-page">
        <div id="login-box">
            <mu-row>
                <mu-col span="4" class="title">
                    {{app}}
                    <div class="exit-button">
                        <mu-tooltip placement="right" content="退出">
                            <mu-button fab small color="primary" @click="quitApp">
                                <mu-icon value=":i-icon-exit"></mu-icon>
                            </mu-button>
                        </mu-tooltip>
                    </div>
                </mu-col>
                <mu-col span="6" offset="1" class="form">
                    <mu-form id="login-form" ref="form" :model="loginForm" class="mu-demo-form">
                        <mu-form-item label="用户名" prop="username" :rules="usernameRules">
                            <mu-text-field v-model="loginForm.username" prop="username"></mu-text-field>
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
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                usernameRules: [
                    {validate: (val) => !!val, message: '必须填写用户名'}
                ],
                passwordRules: [
                    {validate: (val) => !!val, message: '必须填写密码'}
                ],
                loginForm: {
                    username: '',
                    password: ''
                },
                app: {testData: 1}
            };
        },
        methods: {
            submit() {
                this.$refs.form.validate().then((result) => {
                    if (result) {
                        // request.login(this.loginForm.username, this.loginForm.password, this.loginForm.role)
                    }
                });
            },
            clear() {
                this.$refs.form.clear();
                this.loginForm = {
                    username: '',
                    password: ''
                };
            },
            quitApp() {
                lifecycle.exit()
            }
        },
        mounted() {
            window['app'] = this.app
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
    }

    .title {
        height: 360px;
        background: #2196f3;
    }

    .exit-button {
        position: absolute;
        bottom: 0px;
        margin: 10px;
    }

</style>
