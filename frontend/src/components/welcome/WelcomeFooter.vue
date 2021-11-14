<template>
    <div id="footer" class="md:flex md:justify-between p-8 w-full h-80 bg-blue-900 text-yellow-50">
        <div clsss="text-left whitespace-nowrap">       
        </div>
        <div clsss="text-right whitespace-nowrap">
            <div class="text-md text-right font-semibold">
                <p class="text-lg">SSAFY 5기 자율 프로젝트</p>
                <br>
                <p>오픈소스 서울 1반 3팀 A103</p>
                <div class="transform transition duration-100 flex justify-end" @mouseenter="enterTeamName" @mouseleave="leaveTeamName">
                    <div>Team.</div>
                    <div v-if="state.teamName" class="text-yellow-50 text-opacity-50 flex justify-end">
                        <div class="text-yellow-50 text-opacity-100 font-bold text-2xl">공</div>
                        지
                        <div class="text-yellow-50 text-opacity-100 font-bold text-2xl">모</div> 
                        아
                        <div class="text-yellow-50 text-opacity-100 font-bold text-2xl">전</div> 
                        해드려요
                    </div>
                    <div v-else class="text-yellow-50 text-opacity-100 font-bold text-2xl">
                        공모전
                    </div>
                </div>
                <br>
                <p>©Copyright 2021</p>
            </div>
            <div class="flex justify-end content-end mt-4 cursor-pointer">
                <img :src="state.github" alt="git" class="h-10 w-10 mr-4" @click="clickLogo(0)">
                <img :src="state.notion" alt="notion" class="h-9 w-9 mr-4" @click="clickLogo(1)">
                <img :src="state.npm" alt="npm" class="h-8 w-16 mr-4 mt-1" @click="clickLogo(2)">
            </div>
        </div>
    </div>
</template>
<script>
import { reactive } from 'vue'
import notion from '@/assets/logo/notion.png'
import github from '@/assets/logo/github.png'
import npm from '@/assets/logo/npm.png'

export default {
    name: 'WelcomeFooter',
    components: {
    },

    setup(){
        const state = reactive({
            teamName : false,
            notion,
            github,
            npm
        })
        const enterTeamName = ()=>{
            state.teamName = true
        }
        const leaveTeamName = ()=>{
            state.teamName = false
        }
        const clickLogo = (id)=>{
            switch (id) {
                case 0:
                    window.open("https://github.com");
                    break;
                case 1:
                    window.open("https://notion.so");
                    break;
                case 2:
                    window.open("https://npmjs.com");
                    break;
            }
        }
        return { state, enterTeamName, leaveTeamName, clickLogo }
    }
};
</script>

<style scoped>
</style>