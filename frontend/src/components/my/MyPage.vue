<template>
    <div class="bg-gray-100 w-full h-screen px-32 pt-12">
        <div class="w-11/12 h-full p-8">
            <my-info :url="state.myInfo.url" :id="state.myInfo.id" />
            <my-subscribe/>
        </div>
    </div>
</template>
<script>
import MyInfo from '@/components/my/MyInfo.vue'
import MySubscribe from '@/components/my/MySubscribe.vue'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MyPage',
    components: {
        MyInfo,
        MySubscribe
    },

    setup(){
        const state = reactive({
            myInfo:{
                url: "hello.ssafy.com",
                id: "abc@abcmail.com"
            },
        })
        const init = ()=>{
        }
        init()
        return { state }
    }
};
</script>

<style scoped>
</style>