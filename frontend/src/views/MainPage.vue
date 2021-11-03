<template>
    <div class="flex justify-start myPage">
        <router-view :key="$route.fullPath" class="w-full h-screen"
        @clickDetail="onClickDetail"/>
        <main-sidebar
        :detailFlag = "state.sideDetailFlag"
        @clickOther="onClickOther"/>
    </div>
</template>
<script>
import MainSidebar from '@/components/main/MainSidebar.vue'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MainPage',
    components: {
        MainSidebar
    },

    setup(){
        const state = reactive({
            sideDetailFlag: false,
        })
        const onClickDetail = ()=>{
            //console.log("detail")
            state.sideDetailFlag = true
        }
        const onClickOther = ()=>{
            //console.log("other")
            state.sideDetailFlag = false
        }
        return { state, onClickDetail, onClickOther }
    }
};
</script>

<style scoped>
.myPage{
    background-color: rgba(0, 0, 0, 0);
}
</style>
