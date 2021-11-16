<template>
    <div class="relative inline-block h-5/6 w-60 bg-panel text-font rounded-lg p-2 mr-2 overflow-hidden shadow-md">
        <div class="h-1/6">
            <div class="font-bold align-bottom mr-2 overflow-hidden">{{notice.team}}</div>
            <div class="text-sm align-bottom overflow-hidden">{{notice.channel}}</div>
        </div>
        <div class="text-xs mt-2 h-1/2 p-2 pt-4 overflow-hidden">
            <p class="whitespace-normal truncate">{{notice.content}}</p>
        </div>
        <div class="float-right w-7 h-7 border-4 border-label cursor-pointer" @click.stop="check">
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
            team : {
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
        }
        return { check }
    }
};
</script>

<style scoped>
</style>