// const kakaoAPI = 'https://kapi.kakao.com/'
// const backendAPI = 'https://localhost:8080/'
// const backendAPI = 'https://j5a506.p.ssafy.io/'

const path = require('path')
module.exports = {
    devServer: {
        https: true,
        port: 8083,
        open: true,
        // proxy: {
        //     '/api': {
        //         target: backendAPI,
        //     },
        //     '/v1': {
        //         target: kakaoAPI,
        //         changeOrigin: true
        //     },
        //     '/v2': {
        //         target: kakaoAPI,
        //         changeOrigin: true
        //     },
        // },
        historyApiFallback: true,
        hot: true,
        contentBase: path.join(__dirname,'')
    },
    lintOnSave: false, 
}