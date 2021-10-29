<template>
    <div>
        <div class="header pb-4">
            <span class="text-4xl font-bold text-blue-800 ">Settings</span>
        </div>
        <div class="text-dark bold flex justify-between mb-2">
            <div class="flex items-end">
                <span class="font-bold text-2xl align-bottom">My account</span>
            </div>
            <div class="flex justify-end items-end">
                <p class="mr-5 italic text-blue-600 text-xl text-gray">with mattermost</p>
                <img class="w-16 h-16" src="@/assets/mattermost.png" alt="mattermost">
            </div>
        </div>
        <div class="bg-white w-full h-1/5 rounded-xl shadow-2xl mb-2 p-8 border-l-8 border-blue-800">
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
                    <button class="bg-white text-gray-200 font-bold border-2 border-gray-200 py-2 px-4 m-2 rounded-full hover:bg-blue-700 hover:text-white" @click = "leave">Leave</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MyInfo',
    components: {
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
        return { }
    }
};
</script>

<style scoped>
</style>