<template>
    <div class="bg-gray-100 w-full h-screen px-32 pt-12">
        <div class="bg-white w-11/12 rounded-xl shadow-2xl p-8">
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
            <table class="w-full">
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
                    <tr v-for="week in state.weeks" :key="week.id" class="text-center border-collapse border-0">
                        <td v-for="(day, index) in week" :key="day.num" class="w-10 overflow-auto transition cursor-pointer duration-500 hover:bg-gray-200">
                            <div class="flex flex-col h-28 w-full overflow-hidden" @click="goDetail(day)">
                                <div class="top h-4 w-full">
                                    <span v-if="state.nowFlag && state.today == day" class="text-blue-700 font-bold">{{day.num}}</span>
                                    <span v-else-if="index==0" class="text-red-500 font-bold">{{day.num}}</span>
                                    <span v-else class="text-gray-400">{{day.num}}</span>
                                </div>
                                <div class="bottom flex-grow h-24 py-1 w-full cursor-pointer overflow-y-hidden">
                                    <div v-for="node in day.notice" :key="node.token" class="text-sm h-6 w-full text-left text-white opacity-70" :style="{'background':node.color}">
                                        <p v-if="(node.startDay == day.num)" class="ml-2 font-bold">{{node.title}}</p>
                                    </div>
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
            //선택한 달의 공지 데이터 받아오는 api
            //팀 리스트 색깔, store같은곳에서 받아오기
            let teamColor = {
                "123" : "#3FA2F7",
                "111" : "#FFCB21",
                "555" : "#CF2A2A",
            }
            //noticeList는 시작 날짜순으로 정렬해야됨
            let noticeList = [
                {
                    token: "a123",
                    startDay: 1,
                    endDay: 3,
                    title: "교육지원비 서류 제출",
                    team: "123",
                    color: "#3FA2F7",
                },
                {
                    token: "b123",
                    startDay: 11,
                    endDay: 11,
                    title: "모의 SW 역량 평가",
                    team: "123",
                    color: "#3FA2F7",
                },
                {
                    token: "e123",
                    startDay: 14,
                    endDay: 19,
                    title: "프로젝트 제출 기간",
                    team: "123",
                    color: "#3FA2F7",
                },
                {
                    token: "c123",
                    startDay: 17,
                    endDay: 17,
                    title: "UCC 제출일",
                    team: "111",
                    color: "#FFCB21",
                },
                {
                    token: "d123",
                    startDay: 18,
                    endDay: 18,
                    title: "최종 발표",
                    team: "555",
                    color: "#CF2A2A",
                },
                {
                    token: "f123",
                    startDay: 23,
                    endDay: 23,
                    title: "팀 선정기간",
                    team: "111",
                    color: "#FFCB21",
                }
            ]

            state.weeks = [[],[],[],[],[],[]]
            let startDay = new Date(state.year, state.month, 1).getDay()
            let dayCount = new Date(state.year, state.month+1, 0).getDate()
            let target = 0
            for (let i = 0; i < startDay; i++) {
                let day = {
                    num : " ",
                    count : [], // 여기에 공지갯수
                }
                state.weeks[0].push(day)
            }
            for (let i = 1; i <= dayCount; i++) {
                let day = {
                    num : i,
                    notice : [], // 여기에 공지넣기
                }
                noticeList.forEach(notice => {
                    if(notice.startDay <= day.num && day.num <= notice.endDay){
                        day.notice.push(notice)
                    }
                });
                state.weeks[target].push(day)
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