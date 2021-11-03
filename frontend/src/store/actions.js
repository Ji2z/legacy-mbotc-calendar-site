import $axios from 'axios'
const serverData = require('../common/lib/function.js')

// user API
export function userLoginMM({state}, payload){
    const url =  '/api/v4/users/login'
    const body = payload.loginData
    //수정해야됨

    return $axios.post(url, body);
}

export function userLogin({state}, payload){
    const url = '/user'
    const body = {
        "token" : payload.token,
        "userEmail" : payload.email,
        "userName" : payload.userName,
        "url" : serverData.getServerURL(),
        "userId" : payload.userId
    }
    return $axios.post(url, body);
}

export function userTokenRefresh({state}, payload){
    const url = '/user'

    return $axios({
        method: 'patch',
        url: url,
        data:{
            "userEmail" : payload.email, 
            "token" : payload.token, 
            "url" : serverData.getServerURL(),
            "userId" : payload.userId
        }
    })
}
export function deleteUser({state}, payload){
    const url = '/user'

    return $axios({
        method: 'delete',
        url: url,
        headers:{
            'auth':  payload.token
        },
        data:{
            "userEmail" : payload.email, 
        }
    })
}
export function userSync({state}, payload){
    const url = '/sync'
    const headers = { "auth": payload.token }
    //수정해야됨

    return $axios.get(url, {headers});
}

export function setUserSetting({state}, payload){
    const url = '/redis/user'
    const headers = {
        'auth' : payload.token
    }
    const body = {
        "teams" : payload.teams,
        "theme" : payload.theme
    }
    return $axios.post(url, {headers}, body);
}

export function getUserSetting({state}, payload){
    const url = '/redis/user'
    const headers = {
        'auth' : payload.token
    }

    return $axios.get(url, {headers});
}

// notice API

export function getMonthNotice({state}, payload){
    const url = '/notification/month?year=' + payload.year + '&month=' + payload.month
    const headers = { "auth": payload.token }

    return $axios.get(url, {headers});
}

export function getDayNotice({state}, payload){
    const url = '/notification/month?year=' + payload.year + '&month=' + payload.month + '&day=' + payload.day
    const headers = { "auth": payload.token }
    
    return $axios.get(url, {headers});
}

export function getNoticeDetail({state}, payload){
    const url = '/notification/post/' + payload.postId
    const headers = { "auth": payload.token }
    
    return $axios.get(url, {headers});
}

// bot API