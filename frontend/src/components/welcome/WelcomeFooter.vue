<template>
    <div id="footer" class="md:flex md:justify-between p-8 w-full h-80 bg-blue-900 text-yellow-50">
        <div clsss="text-left whitespace-nowrap">       
        </div>
        <div clsss="text-right whitespace-nowrap">
            <div class="text-md text-right font-semibold">
                <div class="transform transition duration-100 flex justify-end">
                    <div>Team.</div>
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