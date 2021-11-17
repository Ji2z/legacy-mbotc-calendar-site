<template>
    <div class="flex justify-start myPage" id="socket">
        <!-- <button class="ml-10" @click="onClickTop('meeeeeesssssage')">button</button> -->
        <notifications/>
        <div class="w-full ml-20">
            <router-view :key="$route.fullPath" class="w-full h-screen pt-12"
            @clickDetail="onClickDetail"/>
        </div>
        <main-sidebar
        :detailFlag = "state.sideDetailFlag"
        @clickOther="onClickOther"/>
    </div>
</template>
<script>
import MainSidebar from '@/components/main/MainSidebar.vue'
import { reactive } from 'vue'
import { notify } from '@kyvg/vue3-notification'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { useStore } from 'vuex'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MainPage',
    components: {
        MainSidebar
    },

    setup(){
        const store = useStore()
        const state = reactive({
            sideDetailFlag: false,
            stompClient: null,
            userToken: store.getters['root/getToken']
        })
        const onClickDetail = ()=>{
            //console.log("detail")
            state.sideDetailFlag = true
        }
        const onClickOther = ()=>{
            //console.log("other")
            state.sideDetailFlag = false
        }
        const connect = () => {
            //console.log("시작이 되는건가?")
            const serverURL = "/api/v1/websocket"
            let socket = new SockJS(serverURL);
            state.stompClient = Stomp.over(socket);
            state.stompClient.connect(
                {},
                frame =>{
                    //console.log("연결완료")
                    state.stompClient.subscribe('/sub/notification/'+ state.userToken, function(notice){
                        //console.log("응답 : ",notice);
                        let notification = JSON.parse(notice.body);
                        onClickTop(notification.content.substr(0,20));
                    })
                }
            )
        }
        const onClickTop = (message) => {
            notify(
                {
                title: "New Notification",
                text: message,
                });
        }
        connect()
        return { state, onClickDetail, onClickOther, connect, onClickTop }
    }
};
</script>

<style scoped>
.myPage{
    background-color: rgba(0, 0, 0, 0);
}
/* .vue-notification-wrapper{
    }
.my-notification {
    
  margin: 0 5px 5px;
  padding: 10px;
  font-size: 12px;
  color: rgb(6, 27, 78) !important;
  
  .notification-title {
      
      border-radius: 25px !important;
      box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2) !important;
    color: #000000;
  }

  .notification-content {
    color: #fafafa;
  }

  &.success {
   
  }
  &.info {
    
  }
  &.error {
   
  }
} */
</style>
