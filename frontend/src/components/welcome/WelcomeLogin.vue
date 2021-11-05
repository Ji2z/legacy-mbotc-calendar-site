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
            <input type="text" class="rounded w-4/5 h-10 border-2 mt-3" disabled placeholder="  Server URL" v-model="state.url" @change="validationCheck">
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
                <button class="bg-gray-200 text-white font-bold py-2 px-4 m-2 rounded" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable, 'cursor-not-allowed':!state.clickable}" @click="submit">Take Me!</button>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { getServerURL } from '../../common/lib/function.js'

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
                    // console.log("MM login")
                    // console.log(result)
                    store.commit('root/setToken', result.headers.token)
                    store.commit('root/setEmail', result.data.email)
                    register(result.headers.token, result.data.email, result.data.id, result.data.username)
                })
                .catch((err)=>{

                })
            }
        }
        const register = (token, email, id,  userName)=>{
            //console.log("MbotC login start")
            let payload = {
                "token": token,
                "userEmail" :email,
                "userId": id,
                "userName" : userName,
            }
            store.dispatch('root/userLogin', payload)
            .then((result)=>{
                //console.log("MbotC login")
                //console.log(result)
                if(result.status == 200 ||  result.status == 201){
                    router.push("/main")
                }
            })
            .catch((err)=>{
                console.log(err)
                // status 409 핸들링
            })
        }
        const validationCheck = ()=>{
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            
            if(!state.email.match(emailRegExp)){
                state.clickable = false
            }else if(state.password.length == 0){
                state.clickable = false
            }else{
                state.clickable = true
            }

        }
        const init = ()=>{
            state.url = "  " + getServerURL()
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