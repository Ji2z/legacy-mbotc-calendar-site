<template>
    <div class="w-full h-screen px-32 pt-12">
        <div class="bg-panel w-11/12 h-full rounded-xl shadow-2xl p-8">
            <div class="header pb-8">
                <span class="text-4xl font-bold text-main ">Create Alert</span>
            </div>
            <div class="grid grid-cols-3 gap-4 text-font">
                <div class="col-span-1">
                    <div>
                        <p class="text-xl font-bold">Destination</p>
                    </div>
                    <div class="flex justify-start items-center pt-3">
                        <select class="form-select block w-full mr-3 border-b-2 p-1">
                            <option value="" disabled selected>Choose Team</option>
                        </select>
                        <select class="form-select block w-full mr-3 border-b-2 p-1">
                            <option value="" disabled selected>Choose Channel</option>
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
                        <p class="text-main text-sm">term</p>
                    </div>
                    <div class="flex justify-start items-center pt-3">
                        <input type="date" class="border-2 rounded-xl p-1">
                            <p v-if="state.termToggle" class="text-main text-sm mx-4">-</p>
                        <input v-if="state.termToggle" type="date" class="border-2 rounded-xl p-1">
                    </div>
                </div>
            </div>
            <div ref="mdEditorWraper" class="w-11/12 h-2/3 bg-back mx-auto mt-5">
                <div ref="mdEditor"></div>
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
// import abc from '@/components/'
import { reactive, ref, onMounted } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeEditor',
    components: {
        Editor
    },

    setup(){
        const mdEditor = ref(null)
        const mdEditorWraper = ref(null)
        const state = reactive({
            termToggle: false,
            clickable: false,
        })
        const submit = ()=>{

        }
        onMounted(()=>{
            let wraperHeight = mdEditorWraper.value.clientHeight + 'px'
            //console.log(mdEditorWraper)
            //console.log(wraperHeight)
            const mountEditor = new Editor({
                el: mdEditor.value,
                height: wraperHeight,
                initialEditType: "markdown",
                previewStyle: "vertical",
            });
        })
        return { state, submit, mdEditor, mdEditorWraper }
    }
};
</script>

<style scoped>
</style>