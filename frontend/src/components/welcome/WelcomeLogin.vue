<template>
    <div class="text-center align-middle bg-white rounded-xl shadow-lg mr-10">
        <div class="mt-10 mb-5 h-16 text-dark bold flex justify-between">
            <div class="ml-10 h-16 items-center">
                <p class="font-bold text-2xl">Sign in</p><br/>
            </div>
            <div class="mr-5 flex justify-end items-center">
                <p class="mr-5 italic text-blue-600 text-xl text-gray">with mattermost</p>
                <img class="w-16 h-16" src="@/assets/mattermost.png" alt="mattermost">
            </div>
        </div>
        <div>
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Server URL" v-model="state.url">
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Email" v-model="state.email">
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Password" v-model="state.password">
        </div>
        <div class="flex justify-between p-8">
            <div>
                <label for="loginToggle" class="mr-8 text-sm text-gray-700">keep me logged in.</label>
                <div class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                    <div>
                        <input type="checkbox" v-model="state.loginToggle" name="loginToggle" id="loginToggle" :class="{'border-blue-400':state.loginToggle, 'right-0':state.loginToggle}" class="absolute block w-5 h-5 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                        <label for="loginToggle" :class="{'bg-blue-400':state.loginToggle}" class="block overflow-hidden h-5 rounded-full bg-gray-300 cursor-pointer"></label>
                    </div>
                </div>
            </div>
            <div>
                <button class="bg-gray-200 text-white font-bold py-2 px-4 m-2 rounded" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable}" @click="submit">Take Me!</button>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomeLogin',
    components: {
    },

    setup(){
        const router = useRouter()
        const state = reactive({
            url:"",
            email:"",
            password:"",
            loginToggle:false,
            clickable:false
        })
        const submit = ()=>{
            router.push("/main")
        }
        return { state, submit }
    }
};
</script>

<style scoped>
</style>