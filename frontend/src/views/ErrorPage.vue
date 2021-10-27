<template>
    <div class="h-screen w-full p-10 bg-gray-100 font-bold text-center">
        <div class="w-1/2 mx-auto bg-white">
            <p class="text-9xl">OOPS!</p>
            <p class="text-4xl">Something wrong</p>
            <br>
            <br>
            <p class="text-4xl">404</p>
            <p class="text-2xl">Page Not Found</p>
            <br>
        </div>
        <button class="bg-blue-500 text-white font-bold py-2 px-4 m-2 rounded" @click="goBack">Previous Page</button>
    </div>
</template>
<script>
// import abc from '@/components/'
//import { reactive } from 'vue'
// import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'ErrorPage',
    components: {
    },

    setup(){
        const router = useRouter()
        const goBack = () =>{
            router.go(-1)
        }
        return { goBack }

    }
};
</script>

<style scoped>
</style>