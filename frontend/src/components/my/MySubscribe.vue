<template>
    <div class="pb-4">
        <div class="flex items-end mt-8 mb-4 text-font">
            <p class="font-bold text-2xl">My Team</p><br/>
        </div>
        <div class="bg-panel w-full h-1/2 rounded-xl shadow-2xl p-8 border-l-8 border-label">
            <div class="flex justify-end">
                <button class="bg-back text-main font-bold border-2 border-label py-2 px-4 m-2 rounded-full hover:bg-main hover:text-back" @click="save">&nbsp;Save&nbsp;</button>
            </div>
            <div class="grid grid-cols-2 gap-4 w-full">
                <div class="overflow-y-auto p-4">
                    <div v-for="team in state.teams" :key="team.id" class="m-2 p-2 bg-panel rounded-xl shadow-2xl border-b-2 border-r-2 border-label">
                        <div class="flex justify-between">
                            <div class="flex items-center border-l-8" :style="{'border-color': team.color}">
                                <p class="text-xl overflow-x-hidden ml-2 cursor-pointer" @click="selectTeam(team.id)">
                                    {{team.teamName}}
                                </p>
                            </div>
                            <div class="flex justify-end items-center">
                                <div class="w-5 h-5 cursor-pointer" :style="{background: team.color}" @click="changeColor(team.id)"/>
                                <div>
                                    <svg class="h-5 w-5 bg-panel" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                                    </svg>	
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-span-1">
                    <div>
                        <p class="bg-panel">{{state.teams[state.selectedTeam].name}}</p><br/>
                    </div>
                    <div class="bg-back overflow-y-auto rounded-xl shadow-inner p-4">
                        <div v-for="channel in state.teams[state.selectedTeam].subscribe" :key="channel.channelId" class="m-2 p-2 bg-panel text-font rounded-xl">
                            <div class="flex justify-between">
                                <p class="text-xl overflow-x-hidden">
                                    {{channel.channelName}}
                                </p>
                                <div class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                                    <div>
                                        <input type="checkbox" v-model="channel.show" name="show" :id="channel.token" :class="{'border-label':channel.show, 'right-0':channel.show}" class="absolute block w-5 h-5 rounded-full bg-back border-4 appearance-none cursor-pointer"/>
                                        <label :for="channel.token" :class="{'bg-main':channel.show}" class="block overflow-hidden h-5 rounded-full bg-gray-300 cursor-pointer"></label>
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
import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MySubscribe',
    components: {
    },

    setup(){
        const store = useStore()
        const state = reactive({
            pickerOpen: false,
            teams:[],
            selectedTeam: 0
        })
        const init = ()=>{
            let payload = store.getters['root/getUserData']
            store.dispatch('root/getUserSetting', payload)
            .then((result)=>{
                state.teams = []
                console.log(result)
                let index = 0;
                result.forEach(data => {
                    let team = data
                    team.id = index
                    index++
                    state.teams.push(team)
                }); 
            })
            .catch((err)=>{
                console.log(err)
            })
        }
        const selectTeam = (id)=>{
            state.selectedTeam = id
        }
        const changeColor = (token)=>{
            state.pickerOpen = true
        }
        const save = ()=>{
            let payload = {
                token: store.getters['root/getToken'],
                teams: state.teams,
                theme: store.getters['root/getTheme']
            }
            store.dispatch('root/setUserSetting', payload)
            .then((result)=>{
                console.log(result)
                //state.teams 에 넣고 위의 v-for와 매칭
            })
            .catch((err)=>{
                console.log(err)
            })
        }
        init()
        return { state, selectTeam, changeColor, save }
    }
};

</script>

<style scoped>
</style>