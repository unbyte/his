<template>
    <div class="welcome-page">
        <div class="welcome">
            欢迎使用 ,
        </div>
        <div class="clock">
            <p class="date">
                {{date}}
            </p>
            <p class="time">
                {{time}}
            </p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Clock",
        data() {
            return {
                weekday: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
                time: null,
                date: null
            }
        },
        methods: {
            updateTime() {
                let t = new Date();
                this.time = `${this.padding(t.getHours())}:${this.padding(t.getMinutes())}:${this.padding(t.getSeconds())}`;
            },
            padding(num) {
                return num < 10 ? '0' + num : '' + num;
            },
            updateDate() {
                let t = new Date();
                this.date = `${this.padding(t.getFullYear())}年${this.padding(t.getMonth() + 1)}月${this.padding(t.getDate())}日 ${this.weekday[t.getDay()]}`;
            }
        },
        mounted() {
            this.updateDate();
            this.updateTime();
            setInterval(this.updateTime, 1000);
        }
    }
</script>

<style scoped>
    .welcome-page {
        position: relative;
        height: 100%;
        width: 100%;
        color: #666;
    }

    .welcome {
        position: absolute;
        top: 100px;
        left: 200px;
        font-size: 36px;
    }


    .clock {
        position: absolute;
        top: 300px;
        left: 0;
        right: 0;
        text-align: center;
    }

    .clock .time {
        letter-spacing: 0.05em;
        font-size: 80px;
        padding: 5px 0;
    }

    .clock .date {
        letter-spacing: 0.1em;
        font-size: 24px;
    }
</style>
