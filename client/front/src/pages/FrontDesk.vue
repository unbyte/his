<template>
    <div>
        <HeaderBar></HeaderBar>
    </div>
</template>

<script>
    import HeaderBar from "../components/global/HeaderBar";

    export default {
        name: "FrontDesk",
        mounted() {
            if (!isDev)
                lifecycle.showStage("main");
        },
        components: {
            HeaderBar
        }
    }
</script>

<style scoped>

</style>
