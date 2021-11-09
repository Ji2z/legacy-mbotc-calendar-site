export function settingInit(state){
    let settings = JSON.parse(localStorage.getItem("settings"))

    if(settings){
        state.themeId = settings.id
        state.theme = settings.theme
        state.userData = settings.userData
    }else{
        settings = {
            id: 0,
            theme: "light",
            userData:{
                token: "",
                url: "",
                userEmail: "",
                userId: "",
                userName: "",
            }
        }
        localStorage.setItem("settings",JSON.stringify(settings))
    }
}

export function logout(state){
    let settings = JSON.parse(localStorage.getItem("settings"))
    settings.userData = {
        token: "",
        url: "",
        userEmail: "",
        userId: "",
        userName: "",
    }
    state.userData = settings.userData
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function setTheme(state, data){
    state.themeId = data.id
    state.theme = data.theme
    let settings = JSON.parse(localStorage.getItem("settings"))
    settings.id = data.id
    settings.theme = data.theme
    localStorage.setItem("settings", JSON.stringify(settings))
}

export function setUserData(state, userData){
    state.userData = userData
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData = userData
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function setToken(state, token){
    state.userData.token = token
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.token = token
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function deleteToken(state){
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.token = ""
    state.userData.token = ""
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function setURL(state, url){
    state.userData.url = url
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.url = url
    localStorage.setItem("settings",JSON.stringify(settings))
}
export function setUserEmail(state, userEmail){
    state.userData.userEmail = userEmail
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.userEmail = userEmail
    localStorage.setItem("settings",JSON.stringify(settings))
}
export function setUserId(state, userId){
    state.userData.userId = userId
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.userId = userId
    localStorage.setItem("settings",JSON.stringify(settings))
}
export function setUserName(state, userName){
    state.userData.userName = userName
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.userData.userName = userName
    localStorage.setItem("settings",JSON.stringify(settings))
}