<template>
    <div class="bg-gray-100 w-5/6 px-2 pt-12">
        <div class="w-full rounded-xl p-2">
            <div class="header flex justify-between py-2">
                <div>
                    <span class="text-xl font-bold p-2 pr-0">{{monthList[state.month]}}</span>
                    <span class="text-xl p-2">{{state.year}}</span>
                </div>
                <div class="inline-block">
                    <button @click="beforeMonth">							
                        <svg class="h-5 w-5 text-gray-500 inline-flex leading-none"  fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
                        </svg> 
                    </button>
                    <button @click="nextMonth">
                        <svg class="h-5 w-5 text-gray-500 inline-flex leading-none"  fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
                        </svg>	
                    </button>
                </div>
            </div>
            <table class="w-full">
                <thead>
                    <tr class="mx-auto text-md">
                        <th class="h-5 font-light text-red-600" style="width:12%">
                            <span>Sun</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Mon</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Tue</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Wed</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Thu</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Fri</span>
                        </th>
                        <th class="h-5 font-light" style="width:12%">
                            <span>Sat</span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="week in state.weeks" :key="week.id" class="text-center">
                        <td v-for="(day, index) in week" :key="day.id" class="p-1 w-6 overflow-auto transition cursor-pointer duration-500 ease hover:bg-gray-200">
                            <div class="flex flex-col h-12 w-6 mx-auto overflow-hidden" @click="goDetail(day)">
                                <div class="top h-2 w-full">
                                    <span v-if="state.nowFlag && state.today == day" class="text-blue-700 font-bold">{{day}}</span>
                                    <span v-else-if="index==0" class="text-red-500 font-bold">{{day}}</span>
                                    <span v-else class="text-gray-400">{{day}}</span>
                                </div>
                                <div class="bottom flex-grow h-10 py-1 w-full cursor-pointer">
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'CalendarSmall',
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
        init()
        return { state, monthList, nextMonth, beforeMonth, goDetail }
    }
};
</script>

<style scoped>
</style>