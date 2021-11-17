<template>
    <div>
        <div id="intro1" class="grid grid-cols-2 w-full h-screen items-center px-6 lg:px-32 relative">
            <div class="w-full h-full">
            </div>
            <div class="w-full h-full">
            </div>
        </div>
        <div id="intro2" class="grid grid-cols-2 w-full h-screen items-center px-6 lg:px-32 relative">
            <div class="w-full h-full">
            </div>
            <div class="w-full h-full flex justify-end">
                <img :src="page[0]" alt="page-0" class="h-full w-full">
            </div>
        </div>
        <div id="intro3" class="grid grid-cols-2 w-full h-screen items-center px-6 lg:px-32 relative">
            <div class="w-full h-full flex justify-start">
                <div>
                    <img :src="page[1]" alt="page-1" class="h-full w-full">
                </div>
            </div>
            <div class="w-full h-full">
            </div>
        </div>
        <div id="intro4" class="grid grid-cols-2 w-full h-screen items-center px-6 lg:px-32 relative">
            <div class="w-full h-full">
            </div>
            <div class="w-full h-full flex justify-end">
                <div>
                    <img :src="page[2]" alt="page-3" class="h-full w-full">
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import page_1 from '@/assets/page_1.png'
import page_2 from '@/assets/page_2.png'
import page_3 from '@/assets/page_3.png'

export default {
    name: 'WelcomeIntroduction',
    components: {
    },

    setup(){
        const page = [page_1, page_2, page_3]
        return { page }
    }
};
</script>

<style scoped>
</style>