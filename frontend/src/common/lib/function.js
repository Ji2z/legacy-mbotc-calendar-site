let serverData = {
    SERVER_URL : "http://k5a103.p.ssafy.io:8065/",
    //SERVER_URL : "https://meeting.ssafy.com/"
}

const getServerData = () =>{
    return serverData
}

const getServerURL = () =>{
    return serverData.SERVER_URL
}

exports.getServerData = getServerData;
exports.getServerURL = getServerURL;