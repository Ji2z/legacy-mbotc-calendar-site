export function settingInit(state){
    let settings = JSON.parse(localStorage.getItem("settings"))

    if(settings){
        state.themeId = settings.id
        state.theme = settings.theme
        state.bgOpacity = settings.opacity
        state.token = settings.token
    }else{
        settings = {
            id: 0,
            theme: "default",
            bgOpacity: "",
            token: "",
        }
        localStorage.setItem("settings",JSON.stringify(settings))
    }
}

export function setTheme(state, data){
    state.themeId = data.id
    state.theme = data.theme
    state.bgOpacity = data.opacity
    let settings = JSON.parse(localStorage.getItem("settings"))
    settings.id = data.id
    settings.theme = data.theme
    settings.bgOpacity = data.opacity
    localStorage.setItem("settings",JSON.stringify(settings))
}