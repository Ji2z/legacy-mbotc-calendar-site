<template>
<div id="top" class="w-full h-screen flex justify-between items-center px-6 lg:px-32 relative md:flex md:justify-between">
        <div class="md:text-5xl sm:text-2xl text-myfont mt-lg md:ml-sm sm:mt-32">
            MbotC
        </div>
        <welcome-login></welcome-login>
    </div>

</template>
<script>
import WelcomeLogin from '@/components/welcome/WelcomeLogin.vue'

export default {
    name: 'WelcomeContent',
    components: {
        WelcomeLogin
    },

    setup(){
        return { }
    }
};
</script>

<style scoped>
</style>