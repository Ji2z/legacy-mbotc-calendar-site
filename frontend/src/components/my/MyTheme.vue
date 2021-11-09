<template>
    <div class="pb-4">
        <div class="flex items-end mt-8 mb-4 text-font">
            <p class="font-bold text-2xl">My Theme</p><br/>
        </div>
        <div class="text-font bg-panel w-full h-48 rounded-xl shadow-2xl mb-2 p-8 border-l-8 border-label">
            <div class="flex justify-between items-end">
                <div v-for="theme in state.themes" :key="theme.id" class="pb-2 overflow-x-scroll whitespace-nowrap no-scrollbar">
                    <div class="relative inline-block h-32 w-48 bg-panel text-font rounded-lg p-2 mr-2" @click="clickTheme(theme.id)">
                        <div class="h-24 w-40 bg-black border-green-600" :class="{'border-4':(state.selected==theme.id)}">
                        </div>
                        <div class="h-4 text-xs p-2">
                            {{theme.theme}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive } from 'vue'
import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MyTheme',
    components: {
    },
    setup(){
        const store = useStore()
        const state = reactive({
            selected: store.getters['root/getThemeId'],
            themes:[
                {
                    id: 0,
                    theme: "light",
                },
                {                    
                    id: 1,
                    theme: "dark",
                },
                {
                    id: 2,
                    theme: "ssuk",
                },
                {
                    id: 3,
                    theme: "blue",
                },
                {
                    id: 4,
                    theme: "eclipse",
                },
            ],
        })
        const clickTheme = (id)=>{
            //console.log(id)
            state.selected = id
            store.commit('root/setTheme', state.themes[state.selected])
        }
        const init = ()=>{

        }
        init()
        return { state, clickTheme }
    }
};
</script>

<style scoped>
.no-scrollbar::-webkit-scrollbar{
    display: none;
}
.no-scrollbar {
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
}
</style>