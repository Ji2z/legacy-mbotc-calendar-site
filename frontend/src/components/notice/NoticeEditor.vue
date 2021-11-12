<template>
    <div class="w-full h-screen px-32 pt-12">
        <div class="bg-panel h-5/6 rounded-xl shadow-2xl p-8">
            <div class="header pb-8">
                <span class="text-4xl font-bold text-main ">Create Alert</span>
            </div>
            <div class="grid grid-cols-3 gap-4 text-font">
                <div class="col-span-1">
                    <div>
                        <p class="text-xl font-bold">Destination</p>
                    </div>
                    <div class="flex justify-start items-center pt-3">
                        <select class="form-select block w-full mr-3 border-b-2 p-1 bg-back text-font"  v-model="state.teamId" @change="state.teamId=0">
                            <option v-for="team in state.teams" :key="team.id" :value="team.id">{{team.teamName}}</option>
                        </select>
                        <select class="form-select block w-full mr-3 border-b-2 p-1 bg-back text-font"  v-model="state.channelId">
                            <option v-for="channel in state.teams[state.teamId].subscribe" :key="channel.channelId" :value="channel.channelId">{{channel.channelName}}</option>
                        </select>
                    </div> 
                </div>
                <div class="col-span-2">
                    <div class="flex justify-start items-center">
                        <p class="text-xl font-bold mr-4">Date</p>
                        <div class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                            <div>
                                <input type="checkbox" v-model="state.termToggle" name="termToggle" id="termToggle" :class="{'border-label':state.termToggle, 'right-0':state.termToggle}" class="absolute block w-5 h-5 rounded-full bg-back border-4 appearance-none cursor-pointer"/>
                                <label for="termToggle" :class="{'bg-main':state.termToggle}" class="block overflow-hidden h-5 rounded-full bg-gray-300 cursor-pointer"></label>
                            </div>
                        </div>
                        <p class="text-font text-sm">term</p>
                    </div>
                    <div class="flex justify-start items-center pt-3">
                        <input type="datetime-local" class="border-2 rounded-xl p-1 bg-back text-font"  v-model="state.startTime" :min="state.today">
                            <p v-if="state.termToggle" class="text-sm mx-4 text-font">-</p>
                        <input v-if="state.termToggle" type="datetime-local" class="border-2 rounded-xl p-1 bg-back text-font" v-model="state.endTime">
                    </div>
                </div>
            </div>
            <div ref="mdEditorWraper" class="w-11/12 h-2/3 bg-back text-font mx-auto mt-5">
                <div id="editor" ref="mdEditor"></div>
            </div>
            <div class="w-full flex justify-center">
                <button class="bg-back text-main px-4 py-2 my-2 inset-x-0 rounded" :class="{'bg-back':state.clickable, 'hover:bg-main':state.clickable}" @click = "submit">
                    Create Alert to MatterMost
                </button>
            </div>
        </div>
    </div>
</template>
<script>
import "@toast-ui/editor/dist/toastui-editor.css"; 
import Editor from "@toast-ui/editor";
import { getTime } from '../../common/lib/function.js';
// import abc from '@/components/'
import { reactive, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { notify } from '@kyvg/vue3-notification'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeEditor',
    components: {
        Editor
    },

    setup(){
        const store = useStore()
        const mdEditor = ref(null)
        const mdEditorWraper = ref(null)
        const state = reactive({
            termToggle: false,
            oldToggle: false,
            clickable: false,
            mountEditor: null,
            startTime: "",
            endTime: "",
            teamId: 0,
            channelId: "0",
            teamList: "",
            channelList: "",
            today: "",
            teams:[{
                color: "#FFFFFF",
                id: 0,
                open: false,
                subscribe: [
                    {channelId: '123', channelName: ' ', show: true},
                ],
                teamId: "",
                teamName: "",
            }],
        })
        const validation = ()=>{
            if(!state.startTime){
                return false
            }else if(state.channelId == 0){
                return false
            }else if(state.mountEditor.getMarkdown().length == 0){
                return false
            }
            return true
        }
        const submit = ()=>{
            //console.log(state.mountEditor.getMarkdown())
            //let timeZone = new Date().getTimezoneOffset()/60;
            //console.log(timeZone)
            if(validation()){
                let formData = new FormData()
                formData.append("channel_id", state.channelId,)
                formData.append("user_id", store.getters['root/getUserId'])
                formData.append("start_time", getTime(state.startTime))
                if(state.termToggle){
                    formData.append("end_time", getTime(state.endTime))
                }else{
                    formData.append("end_time", "")
                }
                formData.append("message", state.mountEditor.getMarkdown())
                let payload = {
                    token: store.getters['root/getToken'],
                    notice: formData
                }
                store.dispatch('root/uploadNotice', payload)
                .then((result)=>{
                    console.log("upload notice")
                    console.log(result)
                })
                .catch((err)=>{
                    console.log(err)
                })
            }else{
                notify({
                    title: "From MBOTC 😉",
                    text: "Please fill in the blanks.",
                    type: "warn"
                });
            }
        }
        onMounted(()=>{
            let wraperHeight = mdEditorWraper.value.clientHeight + 'px'
            //console.log(mdEditorWraper)
            //console.log(wraperHeight)
            state.mountEditor = new Editor({
                el: mdEditor.value,
                height: wraperHeight,
                initialEditType: "markdown",
                previewStyle: "vertical",
            });
        })
        const init = ()=>{
            let today = new Date()
            state.today = today.getFullYear() + "-" + parseInt(today.getMonth()+1) + "-"
            if(today.getDate() < 10){
                state.today = state.today + "0" + today.getDate()
            }else{
                state.today = state.today + today.getDate()
            }
            let payload = store.getters['root/getUserData']
            store.dispatch('root/getUserSetting', payload)
            .then((result)=>{
                state.teams = []
                //console.log(result)
                let index = 0;
                result.data.teams.forEach(data => {
                    let team = data
                    team.id = index
                    team.open = false,
                    index++
                    state.teams.push(team)
                }); 
                //console.log(state.teams)
            })
            .catch((err)=>{
            })
            //console.log(state.today)
        }
        init()
        return { state, submit, mdEditor, mdEditorWraper }
    }
};
</script>

<style scoped>
</style>