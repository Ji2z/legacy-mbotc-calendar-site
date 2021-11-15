<template>
    <div class="w-40 h-40 p-2 bg-panel border-2 border-label rounded-lg fixed left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2">
        <div class="w-32 grid grid-cols-4 gap-2">
            <div v-for="color in palette" :key="color.id" class="w-5 h-5 cursor-pointer rounded-sm" :style="{ 'background-color': `#${ color.hex }` }" @click="changeColor(color.hex)"></div>
        </div>
        <div class="flex justify-between mt-2">
            #<input type="text" v-model="state.color" :style="{ 'background-color': `#${ state.color }`}" class="w-16">
            <button class="text-main font-bold mr-2" @click.stop="save">select</button>
            <button class="text-main" @click.stop="close">x</button>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MyPalette',
    components: {
    },
    props:{
        color : {                
            type: String,
            default: "#FFFFFF",
        },
        id : {                
            type: Number,
            default: 0,
        },
    },
    setup(props, {emit}){
        const palette = [
            {hex : "EA5455", id : 0},
            {hex : "FF8243", id : 1},
            {hex : "F2F013", id : 2},
            {hex : "2FDD92", id : 3},
            {hex : "2F86A6", id : 4},
            {hex : "3F72AF", id : 5},
            {hex : "9C19E0", id : 6},
            {hex : "FFB6B9", id : 7},
            {hex : "1FAB89", id : 8},
            {hex : "F9ED69", id : 9},
            {hex : "3F72AF", id : 10},
            {hex : "F67280", id : 11},
            {hex : "9896F1", id : 8},
            {hex : "FF5722", id : 9},
            {hex : "00A8CC", id : 10},
            {hex : "005691", id : 11},
        ]
        const state = reactive({
            color: props.color.substring(1,8)
        })
        const changeColor = (hex)=>{
            state.color = hex
        }
        const save = ()=>{
            emit("saveColor", {color:state.color, id:props.id})
        }
        const close = ()=>{
            emit("close")
        }
        return { palette, state, changeColor, save, close }
    }
};
</script>

<style scoped>
</style>