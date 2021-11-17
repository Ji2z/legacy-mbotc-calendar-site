<template>
    <div class="bg-panel rounded-xl shadow-lg p-12 text-font divide-y-2">
        <div>
            <div class="overflow-hidden flex justify-between">
                <div class="overflow-hidden flex justify-start">
                    <div class="h-16 text-3xl text-font mr-8 font-bold align-text-bottom overflow-hidden">{{notice.team}}</div>
                    <div class="h-16 text-xl text-font align-text-bottom overflow-hidden">{{notice.team}}</div>
                </div>
            </div>
            <div class="overflow-hidden flex justify-between">
                <div class="overflow-hidden flex justify-start">
                    <img :src="logo" alt="logo" class="h-6 w-6 mx-4">
                    <div class="h-10 text-xl font-bold align-text-bottom overflow-hidden mr-8 ">{{notice.user}}</div>
                    <div class="h-10 text-xl align-text-bottom overflow-hidden mr-8">{{notice.startTime}} ~ {{notice.endTime}}</div>
                </div>
                <div class="overflow-hidden flex justify-end">
                    <div v-if="notice.files!=null" class="h-10 pb-2 mr-4">
                        <button class="h-8 px-2 bg-back text-main align-bottom rounded text-sm" @click="download">
                            Download files
                        </button>
                    </div>
                    <div v-if="state.myNoticeFlag">
                        <button class="bg-red-500 h-8 px-2 text-white align-bottom rounded text-sm hover:bg-red-700" @click="deleteNotice">Delete</button>
                    </div>
                </div>
            </div>
        </div>
        <perfect-scrollbar ref="mdViewerWraper" class="text-lg h-80 overflow-hidden p-4">
            <div id="editor" ref="mdViewer" class="text-font"></div>
        </perfect-scrollbar>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';
import Editor from "@toast-ui/editor";
import logo_0 from '@/assets/logo/logo_0.png'
// import abc from '@/components/'
import { reactive, ref, onUpdated } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'NoticeContent',
    components: {
    },
    props:{
        notice:{
            token : {                
                type: String,
                default: " ",
            },
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
                type: String,
                default: null,
            },
            check : {
                type: Boolean,
                default: false
            },
            user : {
                type: String,
                default: "mbotc",
            },
            userId : {
                type: String,
                default: " ",
            },
            startTime : {
                type: String,
                default: "What are you looking for? ",
            },
            endTime : {
                type: String,
                default: " There's no notifications now.",
            },
        },
    },
    setup(props){
        const router = useRouter()
        const store = useStore()
        const logo = logo_0
        const mdViewer = ref(null)
        const mdViewerWraper = ref(null)
        const state = reactive({
            mountViewr: null,
            fileList:[],
            targetFile: 0,
            myNoticeFlag: false,
        })
        onUpdated(()=>{
            if(store.getters['root/getUserId'] === props.notice.userId){
                state.myNoticeFlag = true
            }else{
                state.myNoticeFlag = false
            }
            state.fileList = []
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
                theme: (store.getters['root/getThemeId'] == 1 || store.getters['root/getThemeId'] == 2)?"dark":"light"
            });
        })
        const download = ()=>{
            if(props.notice.files != null){
                let fileIds = props.notice.files.split(",")
                fileIds.forEach(file => {
                    // store.dispatch('root/getFileLink', payload)
                    // .then((result)=>{
                    //     console.log(result)
                    //     const link = document.createElement('a');
                    //     const url = result.data.link
                    //     link.href = url;
                    //     link.setAttribute('download', '');
                    //     //link.style.cssText = 'display:none';
                    //     document.body.appendChild(link);
                    //     // link.click();
                    //     // link.remove();
                    // })
                    // .catch((err)=>{
                    // })
                    // store.dispatch('root/getFile', payload)
                    // .then((result)=>{
                    //     console.log(result)
                    //     try {
                    //         let blob = new Blob([result.data], { type: result.headers['content-type'] })
                    //         const url = (window.URL ? URL : webkitURL).createObjectURL(blob);
                    //         const link = document.createElement('a');
                    //         let fileName = 'unknown';
                    //         const contentDisposition = result.headers['content-disposition'];
                    //         if (contentDisposition) {
                    //         const [ fileNameMatch ] = contentDisposition.split(';').filter(str => str.includes('filename'));
                    //         if (fileNameMatch)
                    //             [ , fileName ] = fileNameMatch.split('=');
                    //         }
                    //         fileName = fileName.replace(new RegExp('["]','g'), '');
                    //         console.log(fileName)
                    //         console.log(blob)

                    //         if (window.navigator.msSaveOrOpenBlob) { // IE 10+
                    //             window.navigator.msSaveOrOpenBlob(blob, fileName)
                    //         } else { // not IE
                    //             link.href = url;
                    //             link.setAttribute('download', `${fileName}`);
                    //             link.style.cssText = 'display:none';
                    //             document.body.appendChild(link);
                    //             link.click();
                    //             link.remove();
                    //         }
                    //     } catch (e) {
                    //         console.error(e)
                    //     }
                    // })
                    // .catch((err)=>{

                    // })
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function(){
                        if (this.readyState == 4 && this.status == 200){
                        
                            let fileName = "unknown";
                            let disposition = xhr.getResponseHeader('Content-Disposition');
                            if (disposition) {
                                const [ fileNameMatch ] = disposition.split(';').filter(str => str.includes('filename'));
                                if (fileNameMatch)
                                    [ , fileName ] = fileNameMatch.split('=');
                            }
                            fileName = fileName.replace(new RegExp('["]','g'), '');

                            console.log(fileName)
                        
                            //this.response is what you're looking for
                            //console.log(this.response, typeof this.response);
                            let a = document.createElement("a");
                            let url = URL.createObjectURL(this.response)
                            a.href = url;
                            a.download = fileName;
                            document.body.appendChild(a);
                            a.click();
                            window.URL.revokeObjectURL(url);
                        }
                    }
                    xhr.open('get', '/api/v4/files/' + file);
                    xhr.setRequestHeader("Authorization", "bearer " + store.getters['root/getToken'])
                    xhr.responseType = 'blob';
                    xhr.send();
                });
            }
        }
        const deleteNotice = ()=>{
            let payload={
                token: store.getters['root/getToken'],
                postId: props.notice.token
            }
            store.dispatch('root/deleteNotice', payload)
            .then((result)=>{
                if(result.status == 200){
                    router.go()
                }
            })
            .catch((err)=>{
            })
            
        }
        return { state, logo, mdViewer, mdViewerWraper, download, deleteNotice }
    }
};
</script>

<style scoped>
</style>