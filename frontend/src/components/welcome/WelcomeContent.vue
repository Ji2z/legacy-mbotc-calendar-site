<template>
<div id="top" class="w-full h-screen flex justify-between items-center px-6 lg:px-32 relative bg-gray-100">
        <div class="md:text-5xl sm:text-2xl font-bold">
            <p class="leading-relaxed">The Most</p>
            <p class="leading-relaxed">Beautiful and Convenient</p>
            <p class="leading-relaxed">Mattermost Bot Calendar 🗓</p>
            <br>
            <br>
            <br>
            <p>MBotC 🤖</p>
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