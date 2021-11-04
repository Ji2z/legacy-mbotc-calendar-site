<template>
    <div class="w-full h-screen pt-12">
        <div class="grid grid-cols-4 gap-4 w-5/6 h-full mx-auto">
            <div class="col-span-3 h-full">
                <calendar-title :date="state.detailDate"/>
                <div class="h-1/5 py-2 overflow-x-scroll whitespace-nowrap no-scrollbar">
                    <notice-thumbnail v-for="notice in state.notices" :key="notice.id" :notice = notice class="cursor-pointer"
                    @click="clickNotice(notice.id)" @checked="changeChecked(notice.id, true)" @unchecked="changeChecked(notice.id, false)"/>
                </div>
                <notice-content class="h-3/5" :notice = state.chooseNotice />
            </div>
            <div class="col-span-1 h-full">
                <notice-progress class="w-3/4 h-auto mx-auto"/>
                <calendar-small class="w-3/4 h-auto" :date="state.detailDate"/>
            </div>
        </div>
    </div>
</template>
<script>
import CalendarSmall from '@/components/calendar/CalendarSmall.vue'
import CalendarTitle from '@/components/calendar/CalendarTitle.vue'
import NoticeContent from '@/components/notice/NoticeContent.vue'
import NoticeProgress from '@/components/notice/NoticeProgress.vue'
import NoticeThumbnail from '@/components/notice/NoticeThumbnail.vue'

import { reactive } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'DetailPage',
    components: {
        CalendarSmall,
        CalendarTitle,
        NoticeContent,
        NoticeProgress,
        NoticeThumbnail
    },

    setup(){
        const router = useRouter()
        const state = reactive({
            detailDate: 0,
            notices:[
                {
                    id:0,
                    title : "테스트 공지",
                    channel : "테스트 채널",
                    content : "공지 내용",
                    check : false,
                },
                {
                    id:1,
                    title : "테스트 공지",
                    channel : "기이이이이이인 테스트 채널",
                    content : "공지 내용",
                    check : true,
                },
                {
                    id:2,
                    title : "기이이이이이이인 테스트 공지",
                    channel : "테스트 채널",
                    content : "공지 내용",
                    check : false,
                },
                {
                    id:3,
                    title : "테스트 공지",
                    channel : "테스트 채널",
                    content : "공지 내용",
                    check : false,
                }
            ],
            chooseNotice: {},
        })

        const init = ()=>{
            state.detailDate = router.currentRoute.value.params.date
            //가져온 날짜로 공지 떙겨오는 api 위치해야됨
            //공지의 check값은 localStorage에서 가져오기
            if(state.notices.length > 0){
                state.chooseNotice = state.notices[0]
            }
        }

        const clickNotice = (id)=>{
            //console.log(id)
            state.chooseNotice = state.notices[id]
        }
        const changeChecked = (id, check)=>{
            let data = localStorage.getItem(state.detailDate)
            let checkList = []
            let saveFlag = false
            if(data){
                checkList = JSON.parse(data)
            }

            checkList.forEach(notice => {
                if(notice.id == id){
                    notice.check = check
                    saveFlag = true
                }
            });

            if(!saveFlag){
                let notice = {
                    id: id,
                    check: check,
                }
                checkList.push(notice)
            }

            localStorage.setItem(state.detailDate, JSON.stringify(checkList))
        }

        init()
        return { state, clickNotice, changeChecked }
    }
};
</script>

<style scoped>
.no-scrollbar::-webkit-scrollbar{
    display: none;
}
.no-scrollbar {
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
}
</style>