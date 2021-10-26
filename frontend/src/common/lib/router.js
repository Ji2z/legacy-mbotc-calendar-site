import { createRouter, createWebHistory } from 'vue-router';

//page
import WelcomePage from '@/views/WelcomePage.vue';
import MainPage from '@/views/MainPage.vue';
import ErrorPage from '@/views/ErrorPage.vue';

//component
import CalendarBig from '@/components/calendar/CalendarBig.vue';
import DetailPage from '@/components/main/DetailPage.vue';
import NoticeEditor from '@/components/notice/NoticeEditor.vue';
import MyPage from '@/components/main/MyPage.vue';

const routes = [
    {
        path: '/',
        name: 'Welcome',
        component: WelcomePage,
        meta:{ loginRequired: false }
    },
    {
        path: '/main',
        name: 'Main',
        component: MainPage,
        children: [
            {path: "/:date", component: CalendarBig, meta:{ loginRequired: true } },
            {path: "detail/:date", component: DetailPage , meta:{ loginRequired: true } },
            {path: "/notice", component: NoticeEditor , meta:{ loginRequired: true } },
            {path: "/myPage", component: MyPage , meta:{ loginRequired: true } },
        ],
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: "/404"
    },
    {
        path: '/404',
        name: 'Error',
        component: ErrorPage,  
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// const isLoggedIn = function(){
//     return localStorage.getItem('jwt')? true:false;
// }

// router.beforeEach((to, from, next) => {
//     if(to.meta.loginRequired){
//         if(isLoggedIn()){
//             next()
//         }else{
//             alert("로그인이 필요합니다!")
//             const params = {
//                 redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
//             };
//             window.Kakao.Auth.authorize(params);
//         }
//     }else{
//         next()
//     }
// })

export default router;
