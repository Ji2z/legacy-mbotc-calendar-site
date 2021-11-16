<template>
    <div class="bg-panel rounded-xl shadow-lg p-12 text-font divide-y-2">
        <div>
            <div class="overflow-hidden flex justify-between">
                <div class="overflow-hidden flex justify-start">
                    <div class="h-16 text-3xl text-main mr-8 font-bold align-text-bottom overflow-hidden">{{notice.team}}</div>
                    <div class="h-16 text-xl text-black align-text-bottom overflow-hidden">{{notice.team}}</div>
                </div>
                <div>

                </div>
            </div>
            <div class="overflow-hidden flex justify-start">
                <img :src="logo" alt="logo" class="h-6 w-6 mx-4">
                <div class="h-10 text-xl font-bold align-text-bottom overflow-hidden mr-8 ">{{notice.user}}</div>
                <div class="h-10 text-xl align-text-bottom overflow-hidden">{{notice.startTime}} ~ {{notice.endTime}}</div>
            </div>
        </div>
        <perfect-scrollbar ref="mdViewerWraper" class="text-lg h-80 overflow-hidden p-4">
            <div id="editor" ref="mdViewer" class="text-font"></div>
        </perfect-scrollbar>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import Editor from "@toast-ui/editor";
import logo_0 from '@/assets/logo/logo_0.png'
// import abc from '@/components/'
import { reactive, ref, onUpdated } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeContent',
    components: {
    },
    props:{
        notice:{
            title : {                
                type: String,
                default: " ",
            },
            team : {
                type: String,
                default: " ",
            },
            channel : {
                type: String,
                default: " ",
            },
            content : {
                type: String,
                default: " ",
            },
            files : {
                type: Array,
                default: [],
            },
            check : {
                type: Boolean,
                default: false
            },
            user : {
                type: String,
                default: " ",
            },
            startTime : {
                type: String,
                default: " ",
            },
            endTime : {
                type: String,
                default: " ",
            },
        },
    },
    setup(props){
        const logo = logo_0
        const mdViewer = ref(null)
        const mdViewerWraper = ref(null)
        const state = reactive({
            mountViewr: null,
        })
        onUpdated(()=>{
            let wraperHeight = mdViewerWraper.value.clientHeight + 'px'
            //console.log(mdViewerWraper)
            //console.log(wraperHeight)
            // console.log("------>")
            // console.log(props.notice.content)
            state.mountViewer = new Editor.factory({
                el: mdViewer.value,
                viewer: true,
                height: wraperHeight,
                initialValue: props.notice.content,
                theme: 'dark',
            });
        })
        return { logo, mdViewer, mdViewerWraper }
    }
};
</script>

<style scoped>
</style>