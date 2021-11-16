<template>
    <div class="bg-panel rounded-xl shadow-lg p-12 m-4 text-font">
        <div class="overflow-hidden flex justify-between">
            <span class="w-4/6 h-10 text-3xl font-bold inline-block align-bottom overflow-hidden">{{notice.channel}}</span>
            <span class="w-1/6 h-10 text-xl font-bold inline-block align-bottom overflow-hidden">{{notice.user}}</span>
            <span class="w-1/6 h-10 text-xl font-bold inline-block align-bottom overflow-hidden cursor-pointer" @click="close">x Close</span>
        </div>
        <div>
            <span class="w-full h-10 text-xl inline-block align-bottom overflow-hidden">{{notice.startTime}} ~ {{notice.endTime}}</span>
        </div>
        <perfect-scrollbar ref="mdViewerWraper" class="text-lg overflow-y-auto h-80 p-4">
            <div id="editor" ref="mdViewer" class="text-font"></div>
        </perfect-scrollbar>
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
    name: 'SearchContent',
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
    setup(props, {emit}){
        const mdViewer = ref(null)
        const mdViewerWraper = ref(null)
        const state = reactive({
            mountViewr: null,
        })

        const close = ()=>{
            emit('close')
        }

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
        return { mdViewer, mdViewerWraper, close }
    }
};
</script>

<style scoped>
</style>