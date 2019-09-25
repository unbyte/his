<template>
    <mu-dialog title="警告" width="600" max-width="80%" :esc-press-close="false" :overlay-close="false" :open="!connectionStatus">
        您已与服务器断开链接，请关闭程序尝试重新登陆。
        <mu-button slot="actions" flat color="primary" @click="quitApp">退出程序</mu-button>
    </mu-dialog>
</template>

<script>
    export default {
        name: "DisconnectedDialog",
        methods:{
            quitApp() {
                if (!isDev)
                    lifecycle.exit()
            }
        },
        computed:{
            connectionStatus() {
                return this.$store.state.connectionStatus;
            }
        }
    }
</script>

<style scoped>

</style>
