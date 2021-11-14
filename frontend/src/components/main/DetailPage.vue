<template>
    <div class="w-full h-screen overflow-y-auto no-scrollbar pt-12">
        <div class="grid grid-cols-4 gap-4 w-5/6 h-full mx-auto">
            <div class="col-span-3 h-full">
                <calendar-title :date="state.detailDate"/>
                <div class="h-1/5 py-2 overflow-x-scroll whitespace-nowrap no-scrollbar content-end">
                    <notice-thumbnail v-for="notice in state.notices" :key="notice.id" :notice = notice class="cursor-pointer"
                    @click="clickNotice(notice.id)" @checked="changeChecked(notice.id, true)" @unchecked="changeChecked(notice.id, false)"/>
                </div>
                <notice-content class="h-3/5" :notice ="state.chooseNotice"/>
            </div>
            <div class="col-span-1 h-full">
                <notice-progress class="w-3/4 h-auto mx-auto" :data="state.data" :progress="state.progress"/>
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
import { getTime } from '../../common/lib/function.js';

import { reactive } from 'vue'
import { useStore } from 'vuex'
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
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            detailDate: 0,
            notices:[
                {
                    id:0,
                    title : "테스트 공지",
                    channel : " ",
                    content : " ",
                    files: "",
                    check : false, 
                    user: "",
                    startTime: "",
                    endTime: "",
                },
            ],
            chooseNotice: {},
            data:[0,1],
            progress: 0,
        })

        const init = ()=>{
            state.detailDate = router.currentRoute.value.params.date
            //가져온 날짜로 공지 떙겨오는 api 위치해야됨
            let payload = {
                year: state.detailDate.substring(0,4),
                month: state.detailDate.substring(4,6),
                day: state.detailDate.substring(6,8),
                token: store.getters['root/getToken']
            }
            store.dispatch('root/getDayNotice', payload)
            .then((result)=>{
                // console.log("Day list")
                // console.log(result)
                state.notices = []
                let index = 0
                result.data.notifications.forEach(node => {
                    let notice = {
                        id: index,
                        title: node.content.substring(0, 10),
                        channel: node.channel.team.name + " / " + node.channel.name,
                        content: node.content,
                        files: node.files,
                        check: false, 
                        user: node.user.userName,
                        startTime: getTime(node.startTime),
                        endTime: getTime(node.endTime),
                    }
                    index ++
                    let data = localStorage.getItem(state.detailDate)
                    if(data){
                        let checkList = JSON.parse(data)
                        checkList.forEach(node => {
                            if(node.id == notice.id){
                                notice.check = node.check
                            }
                        });
                    }
                    state.notices.push(notice)
                });
                countChecked()
            })
            .catch((err)=>{
                console.log(err)
            })
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
            state.notices.forEach(notice => {
                if(notice.id == id){
                    notice.check = check
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
            countChecked()
        }
        const countChecked = ()=>{
            let count = 0
            if(state.notices.length > 0){
                state.notices.forEach(node => {
                    if(node.check){
                        count++
                    }
                });
                state.data[0] = count
                state.data[1] = state.notices.length - count
                state.progress = Math.floor((parseInt(count)/parseInt(state.notices.length)) * 100)
            }else{
                state.data = [0,1]
                state.progress = 0
            }
        }

        init()
        return { state, clickNotice, changeChecked }
    }
};
</script>

<style scoped>
</style>