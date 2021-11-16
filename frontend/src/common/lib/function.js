let serverData = require("./serverData.json");
// 추후 json 위치 변경해줘야 함

const getServerData = () =>{
    return serverData
}

const getServerURL = () =>{
    return serverData.SERVER_URL
}

const getServerLock = () =>{
    return serverData.SERVER_LOCK === "true"
}

const getMbotcURL = () =>{
    return serverData.MBOTC_URL
}

const getDayPicker = (date)=>{
    return parseInt(date.substring(8,10))
}

const getTitle = (content)=>{
    //console.log(content)
    let replaceText = content.replace(new RegExp('@here|@HERE|[#*`]|@','g'), '');

    //console.log(replaceText)
    return replaceText.substring(0,20)
}

const getTitleLen = (content, len)=>{
    //console.log(content)
    let replaceText = content.replace(new RegExp('@here|@HERE|[#*`]|@','g'), '');

    if (replaceText.length > len)
        replaceText = replaceText.substring(0, len) + " ...";
    else
        replaceText = replaceText.substring(0, len)
    //console.log(replaceText)
    return replaceText
}

const getTime = (date)=>{
    //console.log(date)
    let replaceText = date.substring(0,10) + " " + date.substring(11,16)
    return replaceText
}

exports.getServerData = getServerData;
exports.getServerURL = getServerURL;
exports.getServerLock = getServerLock;
exports.getMbotcURL = getMbotcURL;
exports.getDayPicker = getDayPicker;
exports.getTitle = getTitle;
exports.getTime = getTime;
exports.getTitleLen = getTitleLen;
