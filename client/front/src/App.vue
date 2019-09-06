<template>
    <div id="app">
        <router-view>
        </router-view>
    </div>
</template>

<script>

    export default {
        name: 'app',
        components: {}
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

    @import "assets/font.css";
</style>
