let serverData = require("./serverData.json");
// 추후 json 위치 변경해줘야 함

const getServerData = () =>{
    return serverData
}

const getServerURL = () =>{
    return serverData.SERVER_URL
}

const getClientURL = () =>{
    return serverData.CLIENT_URL
}

const getMbotcURL = () =>{
    return serverData.MBOTC_URL
}

const getDayPicker = (date)=>{
    return parseInt(date.substring(8,10))
}

exports.getServerData = getServerData;
exports.getServerURL = getServerURL;
exports.getClientURL = getClientURL;
exports.getMbotcURL = getMbotcURL;
exports.getDayPicker = getDayPicker;