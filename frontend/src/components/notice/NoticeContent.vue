<template>
    <div class="bg-panel rounded-xl shadow-lg p-8 m-4 text-font">
        <div class="overflow-hidden">
            <span class="w-1/3 h-10 text-3xl font-bold inline-block align-bottom mr-2 overflow-hidden">{{notice.title}}</span>
            <span class="w-1/3 font-bold inline-block align-bottom mr-2 overflow-hidden">{{notice.channel}}</span>
        </div>
        <div ref="mdViewerWraper" class="text-lg overflow-hidden p-4">
            <div id="editor" ref="mdViewer"></div>
        </div>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import Editor from "@toast-ui/editor";
// import abc from '@/components/'
import { reactive, ref, onMounted } from 'vue'
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
            check : {
                type: Boolean,
                default: false
            },
        },
    },
    setup(){
        const mdViewer = ref(null)
        const mdViewerWraper = ref(null)
        const state = reactive({
            mountViewr: null,
        })
        onMounted(()=>{
            let wraperHeight = mdViewerWraper.value.clientHeight + 'px'
            //console.log(mdViewerWraper)
            //console.log(wraperHeight)
            state.mountViewer = new Editor({
                el: mdViewer.value,
                height: wraperHeight,
                initialValue: notice.content,
                previewStyle: "vertical",
                viewer: true,
            });
        })
        return { mdViewer, mdViewerWraper }
    }
};
</script>

<style scoped>
</style>