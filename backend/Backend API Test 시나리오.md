# Back-end Test

# Step 1. User

---

- [ ]  [POST] `/user` : body에 user 정보 담아서 보냄
- [ ]  [PATCH] `/user` : mmApi로 다시 login 후 바뀐 token값으로 업데이트

# Step 2. Redis

---

- [ ]  [GET] `/redis/user` : 헤더에 token을 포함해서 기본 설정값 저장되었는지 확인
- [ ]  [POST] `/redis/user` : 위에서 갖고 온 유저세팅에서 일부 채널의 show를 false로 바꿔서 update

# Step 3. DB

---

- [ ]  mysql workbench에서 db 접속 후 select * from user을 통해 Step1에서 저장된 user PK 확인
- [ ]  mysql workbench에서 db 접속 후 select * from channel을 통해 channel PK를 확인
    - [ ]  Step2에서 show의 속성이 true인 channel_id와 false인 channel_id의 PK(id) 각각 하나씩 확인
    - [ ]  notice table에서 위에서 확인한 channel PK 2개와 user PK를 사용하여 insert
      
        ```sql
        # userPK = Step 1에서 저장된 user 정보의 PK (id)
        # channelPK = show 속성이 true인 channel의 PK (id)
        insert into notice(start_time,end_time,channel_id,user_id,token) values (now(),now(),channelPK,userPK,'notice1'); # 오늘 날짜에만 표시될 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-01-01",channelPK,userPK,'notice2'); # 2021년 1월 1일 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-04-05",channelPK,userPK,'notice3'); # 2021.01.01~2021.04.05 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-12-22",channelPK,userPK,'notice4'); # 2021.01.01~2021.12.22 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-12-01","2021-12-17",channelPK,userPK,'notice5'); # 2021.12.01~2021.12.17 공지 
        
        # channelPK = show 속성이 false인 channel의 PK (id)
        insert into notice(start_time,end_time,channel_id,user_id,token) values (now(),now(),channelPK,userPK,'notice6'); # 오늘 날짜에만 표시될 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-01-01",channelPK,userPK,'notice7'); # 2021년 1월 1일 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-04-05",channelPK,userPK,'notice8'); # 2021.01.01~2021.04.05 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-01-01","2021-12-22",channelPK,userPK,'notice9'); # 2021.01.01~2021.12.22 공지
        insert into notice(start_time,end_time,channel_id,user_id,token) values ("2021-12-01","2021-12-17",channelPK,userPK,'notice10'); # 2021.12.01~2021.12.17 공지 
        ```

# Step 4. Notice

---

### day

- [ ]  [GET] `/notification/day?year=2021&month=10&day=29`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice1, notice4만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2021&month=01&day=01`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice2, notice3, notice4만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2021&month=12&day=23`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : 아무것도 안나오는지 확인

### month

- [ ]  [GET] `/notification/day?year=2021&month=10`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice1, notice4만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2021&month=01`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice2, notice3, notice4만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2021&month=03`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice3, notice4만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2021&month=12`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : notice4, notice5만 나오는지 확인
- [ ]  [GET] `/notification/day?year=2019&month=12`: header에 auth : user'token 추가
    - [ ]  notifications>channel>token 값 확인 : 아무것도 안나오는지 확인

### postId

- [ ]  [GET] `/notification/post/notice6`: header에 auth : user'token 추가
    - [ ]  startTime이 오늘인지 확인

# Step 5. Sync

---

- [ ]  db에 저장된 mattermost url에서 새로운 팀이나 채널 추가
- [ ]  [GET] `/sync`: SUCCES 뜨는지 확인
    - [ ]  [GET] `/redis/user` : header에 auth : user's token 추가 후 mattermost에서 추가한 팀이나 채널 포함 되어있는지 확인
    - [ ]  mysql workbench에서 새로운 팀이나 채널 insert 되어있는지 select 확인
- [ ]  첫 단계에서 추가했던 팀이나 채널 떠나기
- [ ]  [GET] `/sync`: SUCCES 뜨는지 확인
    - [ ]  [GET] `/redis/user` : header에 auth : user's token 추가 후 mattermost에서 떠난 팀이나 채널 삭제 되어있는지 확인
