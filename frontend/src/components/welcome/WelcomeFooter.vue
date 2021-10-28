<template>
    <div id="footer" class="md:flex md:justify-between p-8 w-full h-80 bg-blue-900 text-yellow-50">
        <div clsss="text-left whitespace-nowrap">       
        </div>
        <div clsss="text-right whitespace-nowrap">
            <div class="text-md text-right font-semibold">
                <p class="text-lg">SSAFY 5기 자율 프로젝트</p>
                <br>
                <p>오픈소스 서울 1반 3팀 A103</p>
                <div class="transform" @mouseenter="enterTeamName" @mouseleave="leaveTeamName">
                    <span>Team.</span>
                    <span v-if="state.teamName" class="text-yellow-50 text-opacity-50">
                        <span class="text-yellow-50 text-opacity-100 font-bold text-2xl">공</span>
                        지
                        <span class="text-yellow-50 text-opacity-100 font-bold text-2xl">모</span> 
                        아
                        <span class="text-yellow-50 text-opacity-100 font-bold text-2xl">전</span> 
                        해드려요
                    </span>
                    <span v-else class="text-yellow-50 text-opacity-100 font-bold text-2xl">
                        공모전
                    </span>
                </div>
                <br>
                <p>©Copyright 2021</p>
            </div>
        </div>
    </div>
</template>
<script>
import { reactive } from 'vue'

export default {
    name: 'WelcomeFooter',
    components: {
    },

    setup(){
        const state = reactive({
            teamName : false
        })
        const enterTeamName = ()=>{
            state.teamName = true
        }
        const leaveTeamName = ()=>{
            state.teamName = false
        }
        return { state, enterTeamName, leaveTeamName }
    }
};
</script>

<style scoped>
</style>