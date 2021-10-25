<template>
    <div>
        <main-sidebar/>
        <router-view :key="$route.fullPath"/>
    </div>
</template>
<script>
// import abc from '@/components/'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'MainPage',
    components: {
    },

    setup(){
        return { }
    }
};
</script>

<style scoped>
</style>
