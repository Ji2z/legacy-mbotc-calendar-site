<template>
    <div class="text-center align-middle bg-white rounded-xl shadow-lg mr-10">
        <div class="mt-20 mb-5 text-dark bold flex justify-between">
            <div class="ml-5">
                <p class="mb-5 font-bold text-2xl">Sign in</p><br/>
            </div>
            <div class="mr-5">
                <p class="italic text-blue-600 text-xl text-gray">with mattermost</p>
            </div>
        </div>
        <div>
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" v-model="state.url">
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" v-model="state.email">
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" v-model="state.password">
        </div>
        <div class="flex justify-between mt-10">
            <div class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                <div>
                    <input type="checkbox" v-model="state.loginToggle" name="loginToggle" id="loginToggle" :class="{'border-blue-400':state.loginToggle, 'right-0':state.loginToggle}" class="absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                    <label for="loginToggle" :class="{'bg-blue-400':state.loginToggle}" class="block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                </div>
            </div>
            <label for="loginToggle" class="text-xs text-gray-700">keep me logged in.</label>
            <div>
                <button class="bg-gray-200 text-white font-bold py-2 px-4 m-2 rounded" :class="{'bg-blue-500':clickable, 'hover:bg-blue-700':clickable}" @click = "submit">Sign in!</button>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'WelcomeLogin',
    components: {
    },

    setup(){
        const state = reactive({
            url:"",
            email:"",
            password:"",
            loginToggle:false
        })
        return { state }
    }
};
</script>

<style scoped>
</style>