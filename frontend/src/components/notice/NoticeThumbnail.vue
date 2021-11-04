<template>
    <div class="relative inline-block h-5/6 w-48 bg-panel text-font rounded-lg p-2 mr-2">
        <div class="overflow-hidden">
            <span class="w-1/2 font-bold inline-block align-bottom mr-2 overflow-hidden">{{notice.title}}</span>
            <span class="w-1/2 text-sm inline-block align-bottom mr-2 overflow-hidden">{{notice.channel}}</span>
        </div>
        <div class="text-xs overflow-hidden p-2">
            {{notice.content}}
        </div>
        <div class="absolute bottom-2 right-2 w-5 h-5 border-4 border-label cursor-pointer" @click="check">
            <img v-if="notice.check" class="transform scale-150" src="@/assets/check.png" alt="check">
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeThumbnail',
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
    setup(props, {emit}){
        const check = ()=>{
            if(!props.notice.check){
                emit("checked")
            }else{
                emit("unchecked")
            }
            props.notice.check = !props.notice.check
        }
        return { check }
    }
};
</script>

<style scoped>
</style>