### 오픈소스 프로젝트

# MBotC
> Mattermost bot calendar plugin 프로젝트 🤖

MBotC는 Mattermost 서버에 설치된 플러그인을 이용하여 공지와 일정을 쉽게 등록, 관리, 검색하고 원하는 팀과 채널의 공지만 모아 확인하며 스케쥴을 관리 할 수 있는 서비스입니다.

---

## 목차

1. [주요기능](#주요기능)
2. [세부기능](#세부기능)
3. [아키텍처](#아키텍처)
4. [설치 및 실행](#설치-및-실행)
5. [사용 예시](#사용-예시)
6. [개발 설정](#개발-설정)
7. [팀원 역할](#팀원-역할)
8. [릴리즈 히스토리](#릴리즈-히스토리)

---

## 주요기능

- Mattermost 공지 달력 (Mattermost calendar)
- 일 별 공지 조회 (Daily notification)
- Mattermost 공지 검색기능 (Search notification)
- Markdown 에디터를 통한 공지 작성 (Markdown editor)
- 팀, 채널 별 구독 설정 (Channel subscribe)
- 개인 테마, 팀 컬러 커스터 마이징 (Theme customizing)

## 세부기능
|구분|기능|설명|
|:---|:---|:---|
|1|Mattermost 공지 달력 (Mattermost calendar)|Mattermost 서버에 등록된 공지들을 한 눈에 모아 봄|
|2|일 별 공지 조회 (Daily notification)|일 별로 기간에 포함되는 공지들을 확인|
|3|Mattermost 공지 검색기능 (Search notification)|내용 기반으로 공지를 검색 가능|
|4|Markdown 에디터를 통한 공지 작성 (Markdown editor)|TOAST Editor를 활용하여 Markdown문서를 미리보며 작성 할 수 있음|
|5|채널 별 구독 설정 (Channel subscribe)|채널별 구독을 설정하여 필요한 공지만 확인 가능함|
|6|개인 테마, 팀 컬러 커스터 마이징 (Theme customizing)|여러 테마를 적용하고, 공지 달력에 표시될 팀 별 생상을 커스터마이징 가능|


## 아키텍처

![아키텍처](/uploads/f1223539006b18d9e9f6226f5d658bf1/캡처.JPG)


## 설치 및 실행

[배포 및 설치 매뉴얼](./exec)


## 사용 예시

메인 달력 화면   
![0](/uploads/8607822a3ba5564add0cc4738e3a2c2d/0.JPG)

데일리 공지 화면 사용   
![page_1](/uploads/2b2edb838534b273b9859cf708d6508e/page_1.gif)

MD 에디터를 사용한 MM 공지 등록   
![page_2](/uploads/e48b6042e67bd3a564c49fe8993159fb/page_2.gif)

MM 공지 검색기능   
![page_3](/uploads/a290eec7c038166dc6db9ca88e33f82b/page_3.gif)

테마 변경기능   
![page_theme](/uploads/8040e2b50e80f50c62a3633465cba7bf/page_theme.gif)

## 팀원 역할
|구분|이름|역할|비고|
|:---|:---|:---|:---|
|**팀장**|신지예|**Bot Plugin**||
|팀원|노진혁|**Frontend**||
|팀원|박서희|**Backend**||
|팀원|안영진|**Bot Plugin**||
|팀원|이진영|**Backend**||

## 릴리즈 히스토리

* 0.0.1 - 2021.11.19
    * Work in progress
    



