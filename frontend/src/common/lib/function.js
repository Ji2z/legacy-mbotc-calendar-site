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

exports.getServerData = getServerData;
exports.getServerURL = getServerURL;
exports.getMbotcURL = getMbotcURL;