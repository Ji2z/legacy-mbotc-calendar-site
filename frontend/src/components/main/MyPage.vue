<template>
    <div class="bg-gray-100 w-full h-screen px-32 pt-12">
        <div class="w-11/12 h-full p-8">
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
                    <span class="text-2xl">hello.ssafy.com</span>
                </div>
                <div class="flex justify-between items-end">
                    <div>
                        <span class="font-bold text-2xl mr-2">ID</span>
                        <span class="text-2xl">abc@abcmail.com</span>
                    </div>
                    <div>
                        <button class="bg-white text-gray-200 font-bold border-2 border-gray-200 py-2 px-4 m-2 rounded-full hover:bg-blue-700 hover:text-white" @click = "leave">Leave</button>
                    </div>
                </div>
            </div>
            <div class="flex items-end mt-8 mb-4">
                <p class="font-bold text-2xl">My Team</p><br/>
            </div>
            <div class="bg-white w-full h-1/2 rounded-xl shadow-2xl p-8 border-l-8 border-blue-800">
                <div class="flex justify-end">
                    <button class="bg-white text-blue-800 font-bold border-2 border-blue-800 py-2 px-4 m-2 rounded-full hover:bg-blue-700 hover:text-white" @click = "save">&nbsp;Save&nbsp;</button>
                </div>
                <div class="grid grid-cols-2 gap-4 w-full">
                    <div class="overflow-y-auto p-4">
                        <div v-for="team in state.teams" :key="team.token" class="m-2 p-2 bg-white rounded-xl shadow-2xl border-b-2 border-r-2 border-gray-200">
                            <div class="flex justify-between">
                                <div class="flex items-center border-l-8" :style="{'border-color': team.color}">
                                    <p class="text-xl overflow-x-hidden ml-2 cursor-pointer" @click="selectTeam(team.token)">
                                        {{team.name}}
                                    </p>
                                </div>
                                <div class="flex justify-end items-center">
                                    <div class="w-5 h-5 cursor-pointer" :style="{background: team.color}" @click="changeColor(team.token)"/>
                                    <div>
                                        <svg class="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                                        </svg>	
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-span-1">
                        <div>
                            <p class="text-gray-500">{{state.teams[state.selectedTeam].name}}</p><br/>
                        </div>
                        <div class="bg-gray-200 overflow-y-auto rounded-xl shadow-inner p-4">
                            <div v-for="channel in state.teams[state.selectedTeam].channel" :key="channel.token" class="m-2 p-2 bg-white rounded-xl">
                                <div class="flex justify-between">
                                    <p class="text-xl overflow-x-hidden">
                                        {{channel.channelName}}
                                    </p>
                                    <div class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                                        <div>
                                            <input type="checkbox" v-model="channel.toggle" name="toggle" :id="channel.token" :class="{'border-blue-800':channel.toggle, 'right-0':channel.toggle}" class="absolute block w-5 h-5 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                                            <label :for="channel.token" :class="{'bg-blue-800':channel.toggle}" class="block overflow-hidden h-5 rounded-full bg-gray-300 cursor-pointer"></label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
    name: 'MyPage',
    components: {
    },

    setup(){
        const state = reactive({
            pickerOpen: false,
            teams:[
                {
                    token:"0",
                    name:"5기 전체 공지",
                    color: "#3FA2F7",
                    channel:[
                        {
                            token:"0123123",
                            channelName : "자율 오픈소스",
                            toggle: true
                        },
                        {
                            token:"11245123",
                            channelName : "특화 UCC 우수팀",
                            toggle: false
                        },
                        {
                            token:"12351232",
                            channelName : "A103",
                            toggle: false
                        },
                    ]
                },
                {
                    token:"1",
                    name:"5기 자율 팀빌딩 서울",
                    color: "#FFCB21",
                    channel:[
                        {
                            token:"0161341",
                            channelName : "1자율 오픈소스",
                            toggle: false
                        },
                        {
                            token:"1246131",
                            channelName : "1특화 UCC 우수팀",
                            toggle: false
                        },
                        {
                            token:"2123645",
                            channelName : "1A103",
                            toggle: false
                        },
                    ]
                },
                {
                    token:"2",
                    name:"5기 발표회",
                    color: "#CF2A2A",
                    channel:[
                        {
                            token:"6235410",
                            channelName : "2자율 오픈소스",
                            toggle: false
                        },
                        {
                            token:"1123451",
                            channelName : "2특화 UCC 우수팀",
                            toggle: false
                        },
                        {
                            token:"412432",
                            channelName : "2A103",
                            toggle: false
                        },
                    ]
                }
            ],
            selectedTeam: 0
        })
        const init = ()=>{
        }
        const selectTeam = (token)=>{
            state.selectedTeam = token
        }
        const changeColor = (token)=>{
            state.pickerOpen = true
        }
        init()
        return { state, selectTeam, changeColor }
    }
};
</script>

<style scoped>
</style>