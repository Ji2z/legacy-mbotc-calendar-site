<template>
    <div class="bg-gray-100 w-full h-screen px-32 pt-12">
        <div class="bg-white w-11/12 h-full rounded-xl shadow-2xl p-8">
            <div class="header pb-8">
                <span class="text-4xl font-bold text-blue-800 ">Create Alert</span>
            </div>
            <div class="grid grid-cols-3 gap-4">
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
                                <input type="checkbox" v-model="state.termToggle" name="termToggle" id="termToggle" :class="{'border-blue-800':state.termToggle, 'right-0':state.termToggle}" class="absolute block w-5 h-5 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                                <label for="termToggle" :class="{'bg-blue-800':state.termToggle}" class="block overflow-hidden h-5 rounded-full bg-gray-300 cursor-pointer"></label>
                            </div>
                        </div>
                        <p class="text-gray-500 text-sm">term</p>
                    </div>
                    <div class="flex justify-start items-center pt-3">
                        <input type="date" class="border-2 rounded-xl p-1">
                        <p v-if="state.termToggle" class="text-gray-500 text-sm mx-4">-</p>
                        <input v-if="state.termToggle" type="date" class="border-2 rounded-xl p-1">
                    </div>
                </div>
            </div>
            <div class="w-11/12 h-2/3 bg-gray-200 mx-auto mt-5">
                <!-- md editor here -->
            </div>
            <div class="w-full flex justify-center">
                <button class="bg-gray-200 text-white px-4 py-2 my-2 inset-x-0 rounded" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable}" @click = "submit">
                    Create Alert to MatterMost
                </button>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeEditor',
    components: {
    },

    setup(){
        const state = reactive({
            termToggle:false,
            clickable:false,
        })
        const submit = ()=>{

        }
        return { state, submit }
    }
};
</script>

<style scoped>
</style>