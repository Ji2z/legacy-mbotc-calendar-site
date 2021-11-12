<template>
    <div class="w-32 h-40 p-2 bg-panel border-2 border-label rounded-lg absolute left-1/2 top-1/2 inset-0">
        <div class="grid grid-cols-3 gap-2">
            <div v-for="color in palette" :key="color.id" class="w-5 h-5 cursor-pointer rounded-sm" :style="{ 'background-color': `#${ color.hex }` }" @click="changeColor(color.hex)"></div>
        </div>
        <div class="flex justify-between mt-2">
            #<input type="text" v-model="state.color" :style="{ 'background-color': `#${ state.color }`}" class="w-16">
            <button class="bg-back text-main font-bold" @click.stop="save">save</button>
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
            {hex : "FF0000", id : 0},
            {hex : "800000", id : 1},
            {hex : "FFFF00", id : 2},
            {hex : "808000", id : 3},
            {hex : "00FF00", id : 4},
            {hex : "008000", id : 5},
            {hex : "00FFFF", id : 6},
            {hex : "008080", id : 7},
            {hex : "0000FF", id : 8},
            {hex : "000080", id : 9},
            {hex : "FF00FF", id : 10},
            {hex : "800080", id : 11},
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
        return { palette, state, changeColor, save }
    }
};
</script>

<style scoped>
</style>