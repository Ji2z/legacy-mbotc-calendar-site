<template>
    <div class="bg-gray-100 w-full h-screen px-32 pt-12">
        <div class="bg-white w-11/12 h-full rounded-xl shadow-2xl p-8">
            <div class="header flex justify-between pb-8">
                <div>
                    <span class="text-4xl font-bold">{{monthList[state.month]}}</span>
                    <span class="text-4xl p-8">{{state.year}}</span>
                </div>
                <div class="inline-block mr-6">
                    <button @click="beforeMonth">							
                        <svg class="h-6 w-6 text-gray-500 inline-flex leading-none"  fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
                        </svg> 
                    </button>
                    <button @click="nextMonth">
                        <svg class="h-6 w-6 text-gray-500 inline-flex leading-none"  fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                        </svg>	
                    </button>
                </div>
            </div>
            <table class="w-full border-2 border-white">
                <thead>
                    <tr class="mx-auto text-3xl">
                        <th class="h-8 font-light text-red-600" style="width:12%">
                            <span>Sun</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Mon</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Tue</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Wed</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Thu</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Fri</span>
                        </th>
                        <th class="h-8 font-light" style="width:12%">
                            <span>Sat</span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="week in state.weeks" :key="week.id" class="text-center">
                        <td v-for="(day, index) in week" :key="day.id" class="p-1 w-10 overflow-auto transition cursor-pointer duration-500 ease hover:bg-gray-200 border-2 border-white">
                            <div class="flex flex-col h-28 w-10 mx-auto overflow-hidden" @click="goDetail(day)">
                                <div class="top h-4 w-full">
                                    <span v-if="state.nowFlag && state.today == day" class="text-blue-700 font-bold">{{day}}</span>
                                    <span v-else-if="index==0" class="text-red-500 font-bold">{{day}}</span>
                                    <span v-else class="text-gray-400">{{day}}</span>
                                </div>
                                <div class="bottom flex-grow h-24 py-1 w-full cursor-pointer">
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div>
            <div class="fixed right-16 bottom-16">
                <svg version="1.0" xmlns="http://www.w3.org/2000/svg" width="100" height="100" viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet"
                class="fill-current text-blue-700 cursor-pointer hover:text-blue-800" @click="goNotice">
                    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                    <path d="M2370 5113 c-379 -36 -661 -116 -980 -278 -378 -193 -717 -497 -965
                    -865 -104 -156 -232 -419 -294 -605 -49 -150 -89 -321 -113 -490 -17 -118 -17
                    -512 0 -630 42 -295 120 -553 242 -800 137 -280 272 -468 494 -691 221 -220
                    412 -357 681 -489 188 -92 309 -137 500 -185 500 -126 1002 -102 1490 71 149
                    53 407 182 540 271 299 199 573 480 769 788 72 113 188 353 235 486 235 662
                    194 1372 -115 1993 -124 250 -263 447 -458 648 -216 224 -428 378 -711 518
                    -296 146 -572 225 -900 255 -102 9 -333 11 -415 3z m304 -1253 c20 -14 49 -43
                    64 -64 l27 -39 5 -491 5 -491 491 -5 491 -5 39 -27 c21 -15 50 -44 64 -65 21
                    -31 25 -48 25 -113 0 -65 -4 -82 -25 -113 -14 -21 -43 -50 -64 -65 l-39 -27
                    -491 -5 -491 -5 -5 -491 -5 -491 -27 -39 c-15 -21 -44 -50 -65 -64 -31 -21
                    -48 -25 -113 -25 -65 0 -82 4 -113 25 -21 14 -50 43 -65 64 l-27 39 -5 491 -5
                    491 -491 5 -491 5 -39 27 c-62 44 -88 90 -92 167 -4 82 23 141 87 186 l43 30
                    491 5 492 5 5 492 5 491 30 43 c45 64 104 91 186 87 50 -3 75 -10 103 -28z"/>
                    </g>
                </svg>
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
    name: 'CalendarBig',
    components: {
    },
    setup(props, {emit}){
        const router = useRouter()
        const monthList = ["January","February","March","April","May","June","July","August","September","October","November","December"]
        const state = reactive({
            nowFlag: false,
            year:0,
            month:0,
            today:0,
            weeks: [[],[],[],[],[],[]]
        })
        const initCalendar = ()=>{
            state.weeks = [[],[],[],[],[],[]]
            let startDay = new Date(state.year, state.month, 1).getDay()
            let dayCount = new Date(state.year, state.month+1, 0).getDate()
            let target = 0
            for (let i = 0; i < startDay; i++) {
                state.weeks[0].push(" ")
            }
            for (let i = 1; i <= dayCount; i++) {
                state.weeks[target].push(i)
                startDay++
                if(startDay == 7){
                    startDay = 0
                    target++
                }
            }
            let today = new Date()
            state.nowFlag = (state.year == today.getFullYear() && state.month == today.getMonth())? true : false
        }
        const init = ()=>{
            let today = new Date()
            state.year = today.getFullYear()
            state.month = today.getMonth()
            state.today = today.getDate()
            initCalendar()
        }
        const nextMonth = ()=>{
            state.month++
            if(state.month == 12){
                state.month = 0
                state.year++
            }
            initCalendar()
        }
        const beforeMonth = ()=>{
            state.month--
            if(state.month == -1){
                state.month = 11
                state.year--
            }
            initCalendar()
        }
        const goDetail = (day)=>{
            emit('clickDetail')
            let target =  state.year.toString()
            if(state.month+1 < 10){
                target+="0"
            }
            target += (state.month + 1).toString()
            if(day < 10){
                target+="0"
            }
            target += day.toString()
            router.push("/main/detail/"+target)
        }
        const goNotice = ()=>{
            router.push("notice")
        }
        init()
        return { state, monthList, nextMonth, beforeMonth, goDetail, goNotice }
    }
};
</script>

<style scoped>
</style>