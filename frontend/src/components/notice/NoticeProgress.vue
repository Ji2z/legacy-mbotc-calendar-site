<template>
    <div>
        <vue3-chart-js :id="doughnutChart.id" :type="doughnutChart.type" :data="doughnutChart.data"></vue3-chart-js>
        <div class="relative left-1/2">
            {{0}}%
        </div>
    </div>
</template>
<script>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
// import abc from '@/components/'
// import { reactive } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'

export default {
    name: 'NoticeProgress',
    components: {
        Vue3ChartJs,
    },

    setup(){
        const doughnutChart = {
            id: 'doughnut',
            type: 'doughnut',
            data: {
                    labels: ['Complete','task'],
                    datasets: [
                    {
                        backgroundColor: [
                        '#163172',
                        '#F6F6F6',
                        ],
                        data: [3,1]
                    }
                    ]
                }
        }
        return { doughnutChart }
    },
}
</script>

<style scoped>
</style>