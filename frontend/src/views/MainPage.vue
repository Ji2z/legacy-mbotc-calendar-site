<template>
    <div class="bg-gray-100 flex justify-start">
        <router-view :key="$route.fullPath" class="w-full h-screen"/>
        <main-sidebar/>
    </div>
</template>
<script>
import MainSidebar from '@/components/main/MainSidebar.vue'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MainPage',
    components: {
        MainSidebar
    },

    setup(){
        return { }
    }
};
</script>

<style scoped>
</style>
