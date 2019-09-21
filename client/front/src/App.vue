<template>
    <div id="app" :class="switchClass()">
        <router-view>
        </router-view>
    </div>
</template>

<script>

    export default {
        name: 'app',
        methods: {
            switchClass() {
                return !isDev ? 'font-color-fix' : '';
            }
        },
    }
</script>

<style>
    html, body, #app {
        background: #fff;
        overflow: hidden;
        width: 100%;
        height: 100%;
        font-family: 'Microsoft YaHei UI', sans-serif;
    }

    * {
        padding: 0;
        margin: 0;
        border: 0;
    }

    *:not(input,textarea) {
        user-select: none;
    }

    .mu-tooltip {
        font-size: 14px !important;
    }


    /*字体透明颜色渲染有问题，覆盖掉透明颜色的字体*/
    .font-color-fix .mu-tabs-inverse, /*Tab头*/
    .font-color-fix .mu-form-item, /*表单*/
    .font-color-fix .mu-step-label.disabled, /*Step*/
    .font-color-fix .mu-table th, /*表格头*/
    .mu-day-button:not(.selected):not(:hover) .mu-day-button-text:not(:hover), /*日期选择器*/
    .font-color-fix .mu-input-help, /*编辑框下边的提示文本*/
    .font-color-fix .mu-item-action, /*清单右边*/
    .font-color-fix .mu-item-sub-title, /*清单副标题*/
    .mu-dialog-wrapper .mu-dialog-body /*弹窗内容*/
    {
        color: rgba(0, 0, 0, 0.9) !important;
    }


    @import "assets/font.css";
</style>
