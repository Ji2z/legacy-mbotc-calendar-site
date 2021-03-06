# Convention

### Git Convention

| 이름             | 설명                                                         | 종류 |
| ---------------- | ------------------------------------------------------------ | ---- |
| feat             | 새로운 기능 추가                                             | 기능 |
| fix              | 버그 수정, 기능 수정                                         | 기능 |
| docs             | 문서 수정에 대한 커밋 (READMD.md)                            | 그외 |
| refactor         | 코드 리팩토링 (변수명 수정, 주석 추가, 코드 스타일 변경 등)  | 개선 |
| build            | 빌드 관련 파일 수정에 대한 커밋                              | 그외 |
| design           | CSS 등 사용자 UI 디자인 변경                                 | 기능 |
| resource         | 이미지 리소스 등 코드와 상관없는 리소스 추가                 | 그외 |
| test             | 테스트 코드, 리팩토링 테스트 코드 추가                       | 그외 |
| delete           | 파일 또는 코드, 리소스 제거                                  | 그외 |
| rename           | 파일 혹은 폴더명을 수정하거나 옮기는 작업                    | 그외 |
| !BREAKING CHANGE | 커다란 API 변경 (API의 arguments, return 값의 변경, <br>DB 테이블 변경, 급하게 치명적인 버그를 고쳐야 하는 경우) | 기능 |
| chore            | 그 외 자잘한 수정에 대한 커밋                                | 그외 |

### Git Branch

- master : 배포

- develop : 기능

- feature : 기능 추가 ( feature/FE-login , feature/BE-login )

### Code Convention

- 변수명 : Camel Case

### Team Rule

🛠 매주 금요일 merge develop into master

🕙 데일리 스크럼(Done, Todo, Problem)

🩺 Pull Request Review

- LGTM: Looks Good To Me 😎
    
    ![cat](/uploads/099e4f271f922b3b702f32cadbbd79ab/cat.png)
