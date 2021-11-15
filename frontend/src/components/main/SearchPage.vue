<template>
    <div class="w-full h-screen px-32 pt-12 overflow-y-auto no-scrollbar">
        <div class= "w-11/12 p-8 text-font flex justify-between">
            <div class="header pb-4">
                <span class="text-4xl font-bold text-main ">Search from Content</span>
            </div>
            <div class="flex justify-end items-end">
                <p class="mr-5 italic text-main text-xl">with mattermost</p>
                <img class="w-16 h-16" src="@/assets/mattermost.png" alt="mattermost">
            </div>
        </div>
        <div class="flex justify-center">
            <input type="text" class="w-3/5 h-10 p-1 pl-4 border-2 rounded-2xl mr-2" placeholder="content word" v-model="state.word" @keyup.enter="search">
            <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 50 50" width="40px" height="40px"
            class="fill-current cursor-pointer" @click.stop="search">
                <path d="M31.4465 27.673H29.4591L28.7547 26.9937C31.2201 24.1258 32.7044 20.4025 32.7044 16.3522C32.7044 
                    7.32075 25.3836 0 16.3522 0C7.32075 0 0 7.32075 0 16.3522C0 25.3836 7.32075 32.7044 16.3522 32.7044C20.4025 
                    32.7044 24.1258 31.2201 26.9937 28.7547L27.673 29.4591V31.4465L40.2516 44L44 40.2516L31.4465 27.673V27.673ZM16.3522 
                    27.673C10.0881 27.673 5.03145 22.6164 5.03145 16.3522C5.03145 10.0881
                    10.0881 5.03145 16.3522 5.03145C22.6164 5.03145 27.673 10.0881 27.673 16.3522C27.673 22.6164 22.6164 27.673 16.3522 27.673Z"/>
            </svg>
        </div>
        <div v-if="state.notices.length != 0" class="no-scrollbar w-3/4 mx-auto p-4">
            <div v-for="notice in state.notices" :key="notice.postId" @click.stop="searchOne(notice.postId)" class="text-font bg-panel w-full rounded-xl shadow-2xl p-4 m-2">
                <div class = "font-bold flex justify-between">
                    <div class="w-1/2 overflow-hidden p-4 text-bold text-2xl">
                        {{notice.channel}}
                    </div>
                    <div class="w-1/4 overflow-hidden p-4 text-xl">
                        {{notice.startTime}}
                    </div>
                    <div class="w-1/4 overflow-hidden p-4 text-xl">
                        {{notice.user}}
                    </div>
                </div>
                <div class="w-full overflow-hidden p-4 text-xl whitespace-normal truncate">
                    {{notice.content}}
                </div>
            </div>
        </div>
        <div v-if="state.open" class="z-50 absolute top-1/2 left-1/2 inset-0">
            <div class="flex justify-end">
                <button class="text-main" @click="state.open=false">x&nbsp;Close</button>
            </div>
            <notice-content class="h-3/5" :notice="state.noticeDetail"/>
        </div>
    </div>
</template>
<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import NoticeContent from '@/components/notice/NoticeContent.vue'
import { getTime } from '../../common/lib/function.js';

export default {
    name: 'DetailPage',
    components: {
        NoticeContent
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            word : "",
            token : store.getters['root/getToken'],
            notices: [],
            noticeDetail:{
                },
            open: false,
        })

        const search = () =>{
            let payload = {
                "word" : state.word,
                "token": state.token,
            }
            store.dispatch('root/getNoticeSearch', payload)
            .then((result) => {
                state.notices = []
                console.log(result)
                result.data.notifications.forEach(node => {
                    let notice = {
                        channel: node.channel.team.name + " / " + node.channel.name,
                        content: node.content,
                        files: node.files,
                        user: node.user.userName,
                        startTime: getTime(node.startTime),
                        endTime: getTime(node.endTime),
                        postId: node.token,
                    }
                    state.notices.push(notice)
                });
            })
            .catch((err)=>{
                console.log(err)
            })
        }

        const searchOne = (postId) =>{
            console.log("눌렸")
            state.open = true
            let payload = {
                "postId" :postId,
                "token": state.token,
            }
            store.dispatch('root/getNoticeDetail', payload)
            .then((result) => {
                console.log(result)
                state.noticeDetail = result.data;
            })
            .catch((err)=>{
                console.log(err)
            })
        }

        const init = ()=>{
        }


        init()
        return { state, search, searchOne }
    }
};
</script>

<style scoped>
</style>