export function settingInit(state){
    let settings = JSON.parse(localStorage.getItem("settings"))

    if(settings){
        state.themeId = settings.id
        state.theme = settings.theme
        state.token = settings.token
    }else{
        settings = {
            id: 0,
            theme: "default",
            token: "",
        }
        localStorage.setItem("settings",JSON.stringify(settings))
    }
}

export function setTheme(state, data){
    state.themeId = data.id
    state.theme = data.theme
    let settings = JSON.parse(localStorage.getItem("settings"))
    settings.id = data.id
    settings.theme = data.theme
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function setToken(state, token){
    state.token = token
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.token = token
    localStorage.setItem("settings",JSON.stringify(settings))
}

export function deleteToken(state){
    let settings = JSON.parse(localStorage.getItem("settings"))

    settings.token = ""
    localStorage.setItem("settings",JSON.stringify(settings))
}