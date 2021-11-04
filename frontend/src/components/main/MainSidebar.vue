<template>
    <div class="h-screen">
        <div class="h-1/5 fixed left-0 top-0">
            <img :src="state.logo[state.theme]" alt="logo" class="h-16 w-16 ml-4 mt-4">
        </div>
        <div class="flex flex-col fixed w-20 h-5/6 bottom-0 left-0 rounded-tr-3xl bg-main font-bold">
            <div class="h-20">

            </div>
            <div class="my-2 ml-2 h-20 rounded-l-2xl" :class="{'bg-back':state.nav[0], 'text-main':state.nav[0], 'text-back':!state.nav[0]}" @click="clickNav(0)">
                <svg version="1.0" xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet"
                class="fill-current mx-auto mt-5 cursor-pointer">
                    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                    <path d="M840 4920 l0 -200 -147 0 c-91 0 -175 -6 -218 -15 -225 -47 -413
                    -235 -460 -460 -22 -105 -22 -3665 0 -3770 47 -225 235 -413 460 -460 105 -22
                    4065 -22 4170 0 225 47 413 235 460 460 22 105 22 3665 0 3770 -47 225 -235
                    413 -460 460 -43 9 -127 15 -217 15 l-148 0 0 200 0 200 -200 0 -200 0 0 -200
                    0 -200 -1320 0 -1320 0 0 200 0 200 -200 0 -200 0 0 -200z m0 -800 l0 -200
                    200 0 200 0 0 200 0 200 1320 0 1320 0 0 -200 0 -200 200 0 200 0 0 201 0 201
                    153 -4 c144 -3 156 -4 190 -28 21 -14 50 -43 65 -64 l27 -39 3 -274 3 -273
                    -2160 0 -2161 0 0 253 c0 139 5 268 10 289 13 44 81 115 124 127 17 5 93 10
                    169 10 l137 1 0 -200z m3878 -2233 l-3 -1354 -27 -39 c-15 -21 -44 -50 -65
                    -64 l-37 -25 -2026 0 -2026 0 -37 25 c-21 14 -50 43 -65 64 l-27 39 -3 1354
                    -2 1353 2160 0 2160 0 -2 -1353z"/>
                    <path d="M760 2620 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M1560 2620 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M2360 2620 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M3160 2620 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M3960 2620 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M760 1820 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M1560 1820 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M2360 1820 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M3160 1820 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M3960 1820 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M760 1020 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M1560 1020 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M2360 1020 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    <path d="M3160 1020 l0 -200 200 0 200 0 0 200 0 200 -200 0 -200 0 0 -200z"/>
                    </g>
                </svg>
            </div>
            <div class="my-2 ml-2 h-20 rounded-l-2xl" :class="{'bg-back':state.nav[1], 'text-main':state.nav[1], 'text-back':!state.nav[1]}" @click="clickNav(1)">
                <svg version="1.0" xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet"
                class="fill-current mx-auto mt-5 cursor-pointer">
                    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                    <path d="M247 4310 c-92 -24 -179 -97 -218 -185 -17 -38 -23 -70 -23 -135 -1
                    -76 2 -90 29 -139 33 -60 87 -110 154 -143 69 -34 186 -32 263 4 70 32 129 93
                    164 166 23 49 26 65 22 132 -5 88 -32 151 -91 212 -71 73 -203 112 -300 88z"/>
                    <path d="M1195 4306 c-61 -19 -96 -40 -139 -83 -122 -120 -122 -325 0 -447 22
                    -21 64 -51 94 -65 l55 -26 1675 0 1675 0 56 26 c117 55 184 160 184 289 0 129
                    -67 234 -184 289 l-56 26 -1660 2 c-1319 1 -1668 -1 -1700 -11z"/>
                    <path d="M235 2867 c-64 -21 -97 -42 -141 -89 -76 -79 -105 -174 -83 -276 16
                    -78 39 -120 94 -171 61 -56 121 -81 209 -88 65 -5 80 -3 132 21 77 35 129 87
                    165 161 76 158 5 348 -158 422 -66 30 -160 39 -218 20z"/>
                    <path d="M1169 2856 c-91 -37 -142 -86 -181 -173 -31 -67 -30 -178 1 -250 29
                    -66 110 -143 176 -169 49 -19 97 -19 1880 -19 l1830 0 55 26 c142 68 218 227
                    177 370 -32 109 -118 197 -223 227 -31 9 -489 12 -1850 12 l-1809 0 -56 -24z"/>
                    <path d="M235 1427 c-62 -21 -97 -42 -138 -84 -129 -128 -122 -335 15 -458 56
                    -51 113 -74 201 -81 67 -6 80 -4 133 20 77 35 129 87 165 161 39 81 41 184 5
                    262 -65 140 -245 225 -381 180z"/>
                    <path d="M1190 1427 c-54 -16 -135 -73 -163 -115 -47 -67 -61 -112 -60 -192 0
                    -98 27 -162 97 -228 99 -94 -2 -87 1261 -87 l1110 0 55 26 c224 106 253 412
                    53 550 -89 62 -35 59 -1227 58 -743 -1 -1102 -4 -1126 -12z"/>
                    </g>
                </svg>
            </div>
            <div class="my-2 ml-2 h-20 rounded-l-2xl" :class="{'bg-back':state.nav[2], 'text-main':state.nav[2], 'text-back':!state.nav[2]}" @click="clickNav(2)">
                <svg version="1.0" xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet"
                class="fill-current mx-auto mt-5 cursor-pointer">
                    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                    <path d="M782 4880 c-359 -50 -641 -294 -748 -645 -25 -81 -27 -105 -27 -240
                    0 -135 2 -159 27 -240 49 -161 172 -357 261 -415 40 -26 95 -34 146 -20 39 11
                    99 67 562 528 285 285 529 533 543 553 74 106 29 213 -135 323 -188 126 -424
                    184 -629 156z m290 -375 c27 -9 50 -20 52 -24 3 -4 -156 -168 -354 -366 l-358
                    -358 -15 29 c-41 81 -52 249 -22 351 53 182 198 328 376 378 71 20 255 14 321
                    -10z"/>
                    <path d="M4115 4880 c-148 -21 -279 -71 -406 -156 -164 -110 -209 -217 -135
                    -323 14 -20 258 -268 543 -553 463 -461 523 -517 562 -528 51 -14 106 -6 146
                    20 89 58 212 254 261 415 25 81 27 105 27 240 0 135 -2 159 -27 240 -115 378
                    -430 628 -817 649 -52 2 -121 1 -154 -4z m244 -365 c300 -79 465 -373 382
                    -680 -25 -93 -1 -110 -395 284 l-360 360 30 12 c101 40 245 51 343 24z"/>
                    <path d="M2405 4344 c-16 -2 -66 -9 -110 -15 -253 -33 -520 -127 -744 -264
                    -139 -84 -243 -166 -366 -290 -212 -211 -354 -432 -453 -701 -255 -696 -104
                    -1452 400 -2003 l68 -75 -29 -30 c-16 -17 -117 -129 -225 -249 -208 -232 -225
                    -260 -211 -336 22 -119 154 -184 255 -125 19 11 127 122 240 247 113 124 215
                    236 227 248 l21 22 84 -51 c300 -181 634 -272 998 -272 364 0 698 91 998 272
                    l84 51 21 -22 c12 -12 114 -124 227 -248 113 -125 221 -236 240 -247 101 -59
                    233 6 255 125 14 76 -3 104 -211 336 -108 120 -209 232 -225 249 l-29 30 68
                    75 c281 307 451 669 508 1084 19 141 15 417 -9 555 -73 418 -255 769 -552
                    1065 -298 299 -649 478 -1075 550 -91 15 -392 28 -455 19z m370 -365 c98 -13
                    248 -50 338 -83 811 -306 1234 -1188 960 -2005 -154 -461 -517 -833 -973 -996
                    -189 -68 -322 -90 -540 -90 -218 0 -351 22 -540 90 -456 163 -819 535 -973
                    996 -237 706 44 1476 680 1867 313 193 673 269 1048 221z"/>
                    <path d="M2397 3305 c-22 -7 -50 -24 -64 -37 -53 -48 -53 -51 -53 -558 0 -515
                    0 -513 59 -563 54 -45 86 -48 465 -45 l353 3 40 27 c83 55 105 163 50 247 -47
                    71 -68 76 -359 79 l-258 3 0 357 c0 325 -2 361 -19 397 -22 49 -48 73 -98 91
                    -48 17 -68 17 -116 -1z"/>
                    </g>
                </svg>
            </div>
            <div class="my-2 ml-2 h-20 rounded-l-2xl" :class="{'bg-back':state.nav[3], 'text-main':state.nav[3], 'text-back':!state.nav[3]}" @click="clickNav(3)">
                <svg version="1.0" xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 512.000000 512.000000" preserveAspectRatio="xMidYMid meet"
                class="fill-current mx-auto mt-5 cursor-pointer">
                    <g transform="translate(0.000000,512.000000) scale(0.100000,-0.100000)" stroke="none">
                    <path d="M2245 5111 c-50 -13 -112 -49 -151 -88 -65 -64 -81 -117 -111 -354
                    -14 -118 -31 -219 -36 -226 -5 -7 -25 -17 -45 -23 -20 -7 -83 -32 -140 -57
                    l-103 -46 -37 30 c-173 137 -304 234 -342 253 -61 31 -159 38 -236 15 -57 -16
                    -67 -25 -291 -248 -223 -224 -232 -234 -248 -291 -23 -78 -16 -175 15 -236 13
                    -25 77 -115 143 -200 66 -85 125 -161 130 -167 6 -9 -5 -46 -36 -115 -25 -57
                    -50 -120 -57 -140 -6 -20 -16 -40 -23 -45 -7 -5 -109 -22 -227 -37 -238 -30
                    -289 -46 -353 -110 -21 -20 -49 -60 -65 -89 l-27 -52 0 -320 0 -320 26 -56
                    c15 -31 43 -73 64 -94 66 -66 118 -82 356 -112 118 -14 219 -31 226 -36 7 -5
                    17 -25 23 -45 7 -20 32 -83 57 -140 31 -69 42 -106 36 -115 -5 -6 -64 -82
                    -130 -167 -66 -85 -130 -175 -143 -200 -31 -62 -38 -158 -15 -236 16 -56 26
                    -68 233 -277 259 -261 275 -272 407 -272 76 0 97 4 135 24 35 19 172 121 368
                    274 8 7 36 -1 90 -26 42 -19 108 -46 145 -58 49 -17 69 -30 72 -44 2 -11 16
                    -112 30 -225 28 -227 45 -279 110 -345 21 -21 63 -49 94 -64 l56 -26 315 0
                    315 0 56 26 c31 15 73 43 94 64 66 67 82 118 112 356 14 118 31 219 37 225 6
                    6 45 24 86 39 41 15 102 41 135 58 70 35 47 46 250 -113 83 -64 170 -127 195
                    -140 62 -31 158 -38 236 -15 56 16 68 26 277 233 261 259 272 275 272 407 0
                    113 -8 128 -168 335 -66 85 -125 161 -130 167 -6 9 5 46 36 115 25 57 50 120
                    57 140 6 20 16 40 23 45 7 5 108 22 226 36 238 30 287 45 355 112 21 21 51 64
                    65 94 l26 56 0 315 0 315 -26 56 c-15 31 -43 73 -64 94 -66 66 -118 82 -356
                    112 -118 14 -219 31 -226 36 -7 5 -17 25 -23 45 -7 20 -32 83 -57 140 -31 69
                    -42 106 -36 115 5 6 64 82 130 167 66 85 130 175 143 200 31 61 38 158 15 236
                    -16 57 -25 67 -248 291 -224 223 -234 232 -291 248 -78 23 -175 16 -236 -15
                    -25 -13 -112 -76 -195 -140 -203 -159 -180 -148 -250 -113 -33 17 -94 43 -135
                    58 -41 15 -80 33 -86 39 -6 6 -23 107 -37 225 -30 238 -46 289 -112 356 -21
                    21 -63 49 -94 64 l-56 26 -300 2 c-165 1 -313 -2 -330 -6z m425 -1491 c124
                    -14 229 -44 335 -94 238 -112 409 -283 520 -519 192 -407 102 -894 -223 -1209
                    -322 -311 -790 -392 -1188 -204 -121 58 -203 114 -296 204 -325 315 -415 802
                    -223 1209 176 372 538 609 950 622 22 0 78 -4 125 -9z"/>
                    </g>
                </svg>
            </div>
            <div class="absolute h-20 text-back bottom-0">
                <img class="w-10 h-10 ml-5 rounded-full" src="https://picsum.photos/40/40" alt="user">
                <div class="ml-5 mt-1">User</div>
            </div>
        </div>
    </div>
</template>
<script>
// import abc from '@/components/'
import { reactive, watch, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

import logo_0 from '@/assets/logo/logo_0.png'
import logo_1 from '@/assets/logo/logo_1.png'
import logo_2 from '@/assets/logo/logo_2.png'
import logo_3 from '@/assets/logo/logo_3.png'
import logo_4 from '@/assets/logo/logo_4.png'

export default {
    name: 'MainSidebar',
    components: {
    },
    props:{
        detailFlag:{
            type: Boolean,
            default: false,
        }
    },
    setup(props, {emit}){
        const router = useRouter()
        const store = useStore()
        const state = reactive({
            nav: [true,false,false,false],
            theme: computed(() => store.getters['root/getThemeId']),
            logo: [logo_0, logo_1, logo_2, logo_3, logo_4],
            today: 0
        })

        const clickNav = (target)=>{
            switch (target) {
                case 0:
                    state.nav = [true,false,false,false]
                    emit("clickOther")
                    router.push("/main")
                    break;
                case 1:
                    state.nav = [false,true,false,false]
                    router.push("/main/detail/"+state.today)
                    break;
                case 2:
                    state.nav = [false,false,true,false]
                    emit("clickOther")
                    router.push("/main/notice")
                    break;
                case 3:
                    state.nav = [false,false,false,true]
                    emit("clickOther")
                    router.push("/main/myPage")
                    break;
            }

        }

        watch(()=> props.detailFlag, (detailFlag, prevDetailFlag)=>{
            //console.log(props)
            if(!prevDetailFlag && detailFlag){
                state.nav = [false,true,false,false]
            }
        })

        let today = new Date()
        state.today = today.getFullYear().toString() + (today.getMonth()+1).toString() + today.getDate()

        const init = ()=>{
            let nowLocation = router.currentRoute.value.fullPath
            if(nowLocation === "/main"){
                state.nav = [true,false,false,false]
            }else if(nowLocation === "/main/myPage"){
                state.nav = [false,false,false,true]
            }else if(nowLocation === "/main/notice"){
                state.nav = [false,false,true,false]
            }else{
                state.nav = [false,true,false,false]
            }
        }
        init()
        return { state, clickNav }
    }
};
</script>

<style scoped>
</style>