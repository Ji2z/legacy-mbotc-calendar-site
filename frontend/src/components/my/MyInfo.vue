<template>
    <div>
        <div class="header pb-4">
            <span class="text-4xl font-bold text-main ">Settings</span>
        </div>
        <div class="text-font bold flex justify-between mb-2">
            <div class="flex items-end">
                <span class="font-bold text-2xl align-bottom">My account</span>
            </div>
            <div class="flex justify-end items-end">
                <p class="mr-5 italic text-main text-xl">with mattermost</p>
                <img class="w-16 h-16" src="@/assets/mattermost.png" alt="mattermost">
            </div>
        </div>
        <div class="text-font bg-panel w-full h-1/5 rounded-xl shadow-2xl mb-2 p-8 border-l-8 border-label">
            <div>
                <span class="font-bold text-2xl mr-2">URL</span>
                <span class="text-2xl">{{url}}</span>
            </div>
            <div class="flex justify-between items-end">
                <div>
                    <span class="font-bold text-2xl mr-2">ID</span>
                    <span class="text-2xl">{{id}}</span>
                </div>
                <div>
                    <button class="bg-back text-main font-bold border-2 border-label py-2 px-4 m-2 rounded-full hover:bg-main hover:text-back" @click="state.openModal=true">Leave</button>
                </div>
            </div>
        </div>
        <main-confirm :modalData="modalData" v-if="state.openModal" @cancel="state.openModal=false" @action="leave"/>
    </div>
</template>
<script>
import MainConfirm from '@/components/main/MainConfirm.vue'
// import abc from '@/components/'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MyInfo',
    components: {
        MainConfirm
    },
    props:{
        url:{
            type: String,
            default: "",
        },
        id:{
            type: String,
            default: "",
        }
    },
    setup(){
        const modalData = {
            title: "Leave MBotC",
            message: "You will lose all of your data by leaving MBotC. This action cannot be undone.",
            action: "Delete Account",
        }
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            openModal: false,
        })
        const leave = ()=>{
            let payload = store.getters['root/getUserData']

            store.dispatch('root/deleteUser', payload)
            .then((result)=>{
                //console.log("MbotC login")
                console.log(result)
                // if(result.status == 200){
                //     router.push("/main")
                // }
            })
            .catch((err)=>{
                console.log(err)
                // status 409 핸들링
            })
        }
        return { modalData, state, leave }
    }
};
</script>

<style scoped>
</style>