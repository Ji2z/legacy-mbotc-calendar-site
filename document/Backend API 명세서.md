![head](/uploads/db4d854ed1e2d48eb6ef35e8e5ecda64/backend-api-header-2.png)

## Contents

- [User](#user)
- [Notice](#notice)
- [SYNC](#sync)
- [Redis](#redis)

> [swagger로 확인하기](http://localhost:8080/api/v1/swagger-ui.html#/)  
> prefix : `http://localhost:8080/api/v1`

### User

<details>
    <summary> User </summary>
    <h2>회원</h2>

```
 [POST] 회원등록 & 로그인 : 우리 DB에 유저 정보를 저장한다. (최초 저장, 이미 저장되어 있다면 토큰값 갱신)
 /user

 parameter : -
 header : -
 body : {
 	"token" : "110000000",
 	"userEmail" : "kimssafy@ssafy.com",
 	"userName" : "ssafy",
 	"url" : "https://000.000.com",
 	"userId" : "00000000"
 	}

```

```
    [PATCH] 로그인 : mm api에 로그인 후 200이 뜨면 userid 값을 가지고 우리 DB에 유저 토큰값을 갱신한다.
    /user

    parameter : -
    header : -
    body : { "userEmail" : "kimssafy@ssafy.com", "token" : "000000", "url" : "https://000.000.com", "userId" : "mm API에서 user_id라고 오는 것"}
```

```
    [DELETE] 회원 탈퇴 : 우리 DB에서 유저정보를 삭제한다.
    /user

    parameter : -
    header : { "auth" : token }
    body : { "userEmail" :"kimssafy@ssafy.com" }
```

</details>

### Notice

<details>
    <summary> Notice </summary>
    <h2>공지</h2>

```
[GET] 한달 전체 공지 갖고오기 : 해당하는 연도별 + 월별 알림을 모두 가져온다.

/notification/month?year="2020"&month=05

parameter : year, month
header :  { "auth" : token }

response :
{
    "subscribe" : "s1,s2,s3",
    "notifications":{
        [
            {
                "userid":"ssafy",
                    "time" : "202001010000",
                    "content" : "blabla",
                    "starttime" : "202001010000",
                    "endttime" : "202001010000",
                    "token" :"000000000", //(mm의 post_id[token]),
                    "files":"aaaaaaa,wwwwwww"
            },
            {},
            {},
            ],[],[]
        }
}
```

```
[GET] 하루 전체 공지 갖고오기 : 해당하는 날의 알림을 모두 가져온다.

/notification/day?year="2020"&month=05&day=10

parameter : year, month, day
header :  { "auth" : token }

response :
{
    "subscribe" : "s1,s2,s3",
    "notifications":{
        [
            {
                "userid":"ssafy",
                    "time" : "202001010000",
                    "content" : "blabla",
                    "starttime" : "202001010000",
                    "endttime" : "202001010000",
                    "token" :"000000000", //(mm의 post_id[token]),
                    "files":"aaaaaaa,wwwwwww"
            },
            {},
            {},
            ],[],[]
        }
}
```

```
[GET] 오늘의 공지만 갖고오기 : 프론트가 아닌 플러그인에서 사용하는 기능
/notification/today

parameter : -
header :  { "auth" : token }

response :
[
  {
    "channel_name": "string",
    "end_time": "string",
    "message": "string",
    "start_time": "string",
    "team_name": "string",
    "user_name": "string"
  }
]
```

```
[GET] 공지 세부내용 갖고오기 : 공지의 세부 내용을 가져온다.
/notification/post/{post_id}

parameter : post_id
header :  { "auth" : token }

response :
{
    	"userid":"ssafy",
    	"time" : "202001010000",
    	"content" : "blabla",
    	"starttime" : "202001010000",
    	"endttime" : "202001010000",
    	"token" :"000000000", //(mm의 post_id[token]),
    	"files":"aaaaaaa,wwwwwww"
}
```

</details>

### SYNC

<details>
    <summary> SYNC </summary>
    <h2>동기화</h2>

```
[GET] 팀, 채널 동기화 : DB에 팀, 채널 목록을 동기화 시킨다.
/sync

parameter : -
header : {"auth" : "token"}
body : -
response : -
```

</details>

### Redis

<details>
    <summary> Redis </summary>
    <h2>Redis</h2>

```
[POST] 유저 저장 : redis에 유저 정보를 저장 및 갱신한다.
key : 유저의  userId
value : (subscribe{팀, 채널, 색}, theme )

/redis/user

parameter :  -
header : { "auth" : token }
body :
{
    "teams":[
    			{
    				"teamId":"team_token",
    				"teamName":"team name",
    				"color": "#FFFFFF",
    				"subscribe":[{"channelId":"channel_token","channelName" : "channel name", "show" : "true"},{}]
    			},{}
    		],
    "theme" : "mugwort",
}

response : -
```

```
[GET] 유저 저장 : redis 에 유저 정보를 반환한다.
key : 유저의  userId
value : (subscribe{팀, 채널, 색}, theme)

/redis/user

parameter : -
header : { "auth" : token }
body: -

response :
{
    "teams":[
    			{
    				"teamId":"team_token",
    				"teamName":"team name",
    				"color": "#FFFFFF",
    				"subscribe":[{"channelId":"channel_token","channelName" : "channel name", "show" : "true"},{}]
    			},{}
    		],
    "theme" : "mugwort",
}
```

</details>

![foot](/uploads/793e00d239703e139beaeefc2d6425f8/backend-api-footer-2.png)
