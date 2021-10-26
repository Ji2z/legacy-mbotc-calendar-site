<template>
    <div>
        <welcome-content/>
        <welcome-introduction/>
        <welcome-footer/>
        <div class="fixed h-40 w-8 right-3 p-1 bottom-1/2 rounded-full opacity-30 bg-gray-500 flex flex-col justify-between">
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[0], 'bg-gray-400':!state.marker[0]}" @click="jumpScroll(0)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[1], 'bg-gray-400':!state.marker[1]}" @click="jumpScroll(1)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[2], 'bg-gray-400':!state.marker[2]}" @click="jumpScroll(2)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[3], 'bg-gray-400':!state.marker[3]}" @click="jumpScroll(3)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[4], 'bg-gray-400':!state.marker[4]}" @click="jumpScroll(4)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[5], 'bg-gray-400':!state.marker[5]}" @click="jumpScroll(5)"></div>
            <div class="h-4 w-4 rounded-full mx-auto cursor-pointer" :class="{'bg-white': state.marker[6], 'bg-gray-400':!state.marker[6]}" @click="jumpScroll(6)"></div>
        </div>
        <div class="transform fixed bottom-0 mx-auto inset-x-0" :class="{'rotate-180':state.reverseFlag}">
            <img class="animate-bounce transform ml-auto mr-auto w-14 h-14 opacity-50 hover:opacity-80 hover:scale-x-125 cursor-pointer" src="@/assets/svg/arrow-down.svg" alt="arrow" @click="nextMarker">
        </div>
    </div>
</template>
<script>
import WelcomeContent from '@/components/welcome/WelcomeContent.vue'
import WelcomeFooter from '@/components/welcome/WelcomeFooter.vue'
import WelcomeIntroduction from '@/components/welcome/WelcomeIntroduction.vue'

import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        WelcomeContent,
        WelcomeFooter,
        WelcomeIntroduction,
    },

    setup(){
        const state = reactive({
            marker:[true,false,false,false,false,false],
            offset:["top","intro1","intro2","intro3","intro4","intro5","footer"],
            now:0,
            lastScroll:0,
            reverseFlag:false,
        })

        const checkMarker = () =>{
            state.reverseFlag = (state.now==6)?true:false
        }

        const jumpScroll = (target)=>{
            state.marker[state.now] = false
            state.now = target
            state.marker[state.now] = true
            location.href = "#"+ state.offset[target]
            //console.log(state.now)
        }
        const markerChange = ()=>{
            let nowScroll = window.pageYOffset;
            let list = []

            if(state.now !=6 || nowScroll < state.lastScroll){

                state.offset.forEach(item => {
                    const target = document.getElementById(item)
                    const clientRect = target.getBoundingClientRect()
                    const relativeTop = clientRect.top
                    list.push(relativeTop + nowScroll)
                })
                list.push(9999)
                //console.log(list)
                nowScroll += 5
                for (let index = 0; index <= 6; index++) {
                    if(nowScroll >= list[index] && nowScroll <= list[index+1]){
                        state.marker[state.now] = false
                        state.now = index
                        state.marker[state.now] = true
                        break
                    }
                }
                state.lastScroll = nowScroll
            }
            checkMarker()
        }

        const nextMarker = ()=>{
            state.marker[state.now] = false
            state.now++
            if(state.now == 7){
                state.now = 0
            }
            location.href = "#"+ state.offset[state.now]
            state.marker[state.now] = true
            checkMarker()
        }

        window.addEventListener('scroll',markerChange)

        return {state , jumpScroll, nextMarker}
    }
};
</script>

<style scoped>
</style>