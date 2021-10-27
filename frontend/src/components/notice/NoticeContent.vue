<template>
    <div class="bg-white rounded-xl shadow-lg p-8 m-4">
        <div class="overflow-hidden">
            <span class="w-1/3 h-8 text-3xl font-bold inline-block align-bottom mr-2 overflow-hidden">{{notice.title}}</span>
            <span class="w-1/3 font-bold inline-block align-bottom mr-2 overflow-hidden">{{notice.channel}}</span>
        </div>
        <div class="text-lg overflow-hidden p-4">
            {{notice.content}}
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeContent',
    components: {
    },
    props:{
        notice:{
            title : {                
                type: String,
                default: " ",
            },
            channel : {
                type: String,
                default: " ",
            },
            content : {
                type: String,
                default: " ",
            },
            check : {
                type: Boolean,
                default: false
            },
        },
    },
    setup(){
        return { }
    }
};
</script>

<style scoped>
</style>