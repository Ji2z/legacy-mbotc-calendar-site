<template>
    <div class="bg-panel rounded-xl shadow-lg p-8 m-4 text-font">
        <div class="overflow-hidden flex justify-between">
            <span class="w-5/6 h-10 text-3xl font-bold inline-block align-bottom overflow-hidden">{{notice.channel}}</span>
            <span class="w-1/6 h-10 text-xl font-bold inline-block align-bottom overflow-hidden">:{{notice.user}}</span>
        </div>
        <div>
            <span class="w-full h-10 text-xl inline-block align-bottomoverflow-hidden">{{notice.startTime}} ~ {{notice.endTime}}</span>
        </div>
        <div ref="mdViewerWraper" class="text-lg overflow-hidden p-4">
            <div id="editor" ref="mdViewer" class="text-font"></div>
        </div>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import Editor from "@toast-ui/editor";
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
            });
        })
        return { mdViewer, mdViewerWraper }
    }
};
</script>

<style scoped>
</style>