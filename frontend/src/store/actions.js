import $axios from 'axios'
import { getServerURL } from '../../../preference/function.js';

// user API
export function userLoginMM({state}, payload){
    const url =  '/api/v4/users/login'
    const body = payload.loginData
    //수정해야됨

    return $axios.post(url, body);
}

export function getUserMM({state}){
    const url =  '/api/v4/users/me'

    return $axios.get(url);
}

export function userLogin({state}, payload){
    //console.log("userLogin")
    const url = '/api/v1/user'
    const body = {
        "token" : payload.token,
        "userEmail" : payload.userEmail,
        "userName" : payload.userName,
        "userId": payload.userId,
        "url" : getServerURL(),
    }
    return $axios.post(url, body);
}

export function userTokenRefresh({state}, payload){
    const url = '/api/v1/user'

    return $axios({
        method: 'patch',
        url: url,
        data:{
            "userEmail" : payload.email, 
            "token" : payload.token, 
            "url" : getServerURL(),
            "userId" : payload.userId
        }
    })
}
export function deleteUser({state}, payload){
    const url = '/api/v1/user'

    return $axios({
        method: 'delete',
        url: url,
        data:{
            "token": payload.token,
            "url": payload.url,
            "userEmail": payload.userEmail,
            "userId": payload.userId,
            "userName": payload.userName,
        }
    })
}
export function userSync({state}, payload){
    const url = '/api/v1/sync'
    const headers = { "auth": payload.token }
    //수정해야됨

    return $axios.get(url, {headers});
}

export function setUserSetting({state}, payload){
    const url = '/api/v1/redis/user'
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
    const url = '/api/v1/redis/user'
    const headers = {
        'auth' : payload.token
    }

    return $axios.get(url, {headers});
}

// notice API

export function getMonthNotice({state}, payload){
    const url = '/api/v1/notification/month?year=' + payload.year + '&month=' + payload.month
    const headers = { 
        'auth': payload.token 
    }

    return $axios.get(url, {headers});
}

export function getDayNotice({state}, payload){
    const url = '/api/v1/notification/month?year=' + payload.year + '&month=' + payload.month + '&day=' + payload.day
    const headers = { 
        'auth': payload.token 
    }
    
    return $axios.get(url, {headers});
}

export function getNoticeDetail({state}, payload){
    const url = '/api/v1/notification/post/' + payload.postId
    const headers = { "auth": payload.token }
    
    return $axios.get(url, {headers});
}

export function uploadNotice({state}, payload){
    const url = '/api/v1/notification'
    const headers = {
        'auth' : payload.token
    }
    const body = payload.notice
    return $axios.post(url, {headers}, body);
}

// bot API