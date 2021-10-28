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
            <main-addon class="fixed right-16 bottom-16" @click="goEdit"/>
        </div>
    </div>
</template>
<script>
import MainAddon from '@/components/main/MainAddon.vue'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'CalendarBig',
    components: {
        MainAddon
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
        const goEdit = ()=>{
            router.push("/main/notice")
        }
        init()
        return { state, monthList, nextMonth, beforeMonth, goDetail, goNotice, goEdit }
    }
};
</script>

<style scoped>
</style>