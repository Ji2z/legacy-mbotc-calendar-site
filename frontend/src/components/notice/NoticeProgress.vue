<template>
    <div>
        <DoughnutChart ref="chartRef" :chartData="state" :options="options"></DoughnutChart>
        <div class="relative left-1/2">
            {{progress}}%
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { computed, defineComponent, reactive, ref } from 'vue'
// import { useStore } from 'vuex'
// import { useRouter } from 'vue-router'
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);
import { DoughnutChart } from 'vue-chart-3'

export default defineComponent({
    name: 'NoticeProgress',
    components: {
        DoughnutChart,
    },
    props:{
        data : {                
            type: Array,
            default: [3,1]
        },
        progress : {
            type: Number,
            default : 0,
        }
    },
    setup(props){
        const chartRef = ref();
        const state = reactive({
            labels: ['Complete', 'Task'],
            datasets: [
                {
                    data: props.data,
                    backgroundColor: ['#40C459','#F6F6F6',],
                },
            ],
        })

        const options = ref({
            responsive: true,
            plugins: {
                legend: { position: 'top', },
                title: { display: true, text: '진행도', },
            },
        });

        return { chartRef, options, state };
    },
})
</script>

<style scoped>
</style>