<template>
    <div class="border-label border-4 rounded-xl shadow-lg p-12 m-4 text-font" :class="{'bg-black':(state.themeId == 2), 'bg-panel':(state.themeId != 2) }">
        <div class="overflow-hidden flex justify-between">
            <div class="overflow-hidden flex justify-start">
                <span class="h-10 text-3xl font-bold inline-block align-bottom overflow-hidden mr-2">{{notice.team}}</span>
                <span class="h-10 text-xl inline-block align-bottom overflow-hidden">{{notice.channel}}</span>
            </div>
            <span class="w-1/6 h-10 text-xl font-bold inline-block align-bottom overflow-hidden cursor-pointer" @click="close">x Close</span>
        </div>
        <div class="overflow-hidden flex justify-start mt-3">
            <img :src="logo" alt="logo" class="h-6 w-6 mr-2">
            <div class="h-10 text-xl align-text-bottom overflow-hidden mr-8 ">{{notice.user}}</div>
            <div class="h-10 text-xl align-text-bottom overflow-hidden text-gray-500">{{notice.startTime}} ~ {{notice.endTime}}</div>
        </div>
        <hr>
        <perfect-scrollbar ref="mdViewerWraperSearch" class="text-lg h-2/3 p-4">
            <div id="editor" ref="mdViewerSearch" class="text-font"></div>
        </perfect-scrollbar>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';
import Editor from "@toast-ui/editor";
// import abc from '@/components/'
import { reactive, ref, onUpdated } from 'vue'
import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'
import logo_0 from '@/assets/logo/logo_0.png'

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
            time : {
                type: String,
                default: " ",
            },
        },
    },
    setup(props, {emit}){
        const logo = logo_0
        const mdViewerSearch = ref(null)
        const mdViewerWraperSearch = ref(null)
        const store = useStore()
        const state = reactive({
            mountViewr: null,
            themeId : store.getters['root/getThemeId'],
        })

        const close = ()=>{
            emit('close')
        }

        onUpdated(()=>{
            // let wraperHeight = mdViewerWraperSearch.value.clientHeight + 'px'
            //console.log(mdViewerWraperSearch)
            //console.log(wraperHeight)
            // console.log("------>")
            // console.log(props.notice.content)
            state.mountViewer = new Editor.factory({
                el: mdViewerSearch.value,
                viewer: true,
                // height: wraperHeight,
                initialValue: props.notice.content,
                theme: (state.themeId== 1 || state.themeId == 2)?"dark":"light"
            });

            changeFontSize();
        })

        const changeFontSize = () => {
            document.getElementsByClassName("toastui-editor-contents")[0].style.fontSize = "20px";
        }
        return { mdViewerSearch, mdViewerWraperSearch, close, state, logo, onUpdated }
    }
};
</script>

<style scoped>
</style>