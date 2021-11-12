let serverData = require("./serverData.json");
// 추후 json 위치 변경해줘야 함

const getServerData = () =>{
    return serverData
}

const getServerURL = () =>{
    return serverData.SERVER_URL
}

const getMbotcURL = () =>{
    return serverData.MBOTC_URL
}

const getDayPicker = (date)=>{
    return parseInt(date.substring(8,10))
}

const getTitle = (content)=>{
    //console.log(content)
    let replaceText = content.replace(new RegExp('@here','g'), ''); 
    //console.log(replaceText)
    return replaceText.substring(0,10)
}

const getTime = (date)=>{
    //console.log(date)
    let replaceText = date.substring(0,10) + " " + date.substring(11,16)
    return replaceText
}

exports.getServerData = getServerData;
exports.getServerURL = getServerURL;
exports.getMbotcURL = getMbotcURL;
exports.getDayPicker = getDayPicker;
exports.getTitle = getTitle;
exports.getTime = getTime;
