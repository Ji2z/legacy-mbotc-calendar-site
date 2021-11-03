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
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Server URL" v-model="state.url" @change="validationCheck">
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Email" v-model="state.email" @change="validationCheck">
            <input type="password" class="rounded w-4/5 h-10 border-2 mt-3" placeholder="  Password" v-model="state.password" @change="validationCheck">
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
                <button class="bg-gray-200 text-white font-bold py-2 px-4 m-2 rounded cursor-not-allowed" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable, 'cursor-pointer':state.clickable}" @click="submit">Take Me!</button>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomeLogin',
    components: {
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            url:"",
            email:"",
            password:"",
            loginToggle:false,
            clickable:false
        })
        const submit = ()=>{
            if(state.clickable){
                let payload = {
                    url: state.url,
                    loginData:{
                        "device_id": "",
                        "login_id": state.email,
                        "password": state.password,
                        "token": ""
                    }
                }
                store.dispatch('root/userLoginMM',payload)
                .then((result)=>{
                    console.log(result)
                    //router.push("/main")
                })
                .catch((err)=>{

                })
            }
        }
        const validationCheck = ()=>{
            var urlRegExp = /[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)?/gi;
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            
            if(!state.url.match(urlRegExp)){
                state.clickable = false
            }else if(!state.email.match(emailRegExp)){
                state.clickable = false
            }else if(state.password.length == 0){
                state.clickable = false
            }else{
                state.clickable = true
            }

        }
        const init = ()=>{
            let loginStatus = localStorage.getItem("loginStatus")
            if(loginStatus){

            }
        }
        init()
        return { state, submit, validationCheck }
    }
};
</script>

<style scoped>
</style>