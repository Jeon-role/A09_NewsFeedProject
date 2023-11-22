# A09_NewsFeedProject
- 프로젝트 명 :  일상로그
- 프로젝트 소개 :  일상을 기록하는 벨로그 형식의 뉴스피드

## 와이어프레임
https://www.figma.com/file/7SzzoTpZJxjjIbkbeUHq0w/%EB%89%B4%EC%8A%A4%ED%94%BC%EB%93%9C%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-(Community)?type=design&node-id=0-1&mode=design&t=Kgur885XRfk6Kt8a-0
![image](https://github.com/phantomrole/A09_NewsFeedProject/assets/146637795/4b646eb3-d380-4420-9ba0-f2af70a0f7ed)

## ERD 다이어그램
![image](https://github.com/phantomrole/A09_NewsFeedProject/assets/146637795/7c73ca8e-1bfb-44f3-aac9-45ba4c3fc219)

## API 명세서
https://www.notion.so/a3acf4a786f04ffb92ed84c6bf1866b4?v=120eb705896c4add951e5b1126980b87&pvs=4

### 🤎 체크리스트

- [ ]  **사용자 인증 기능**
    - 회원가입 기능
    - 로그인 및 로그아웃 기능
        - JWT 활용
        - 사용자는 자신의 계정으로 서비스에 로그인하고 로그아웃 가능
- [ ]  **계정 관리**
    - 프로필 수정 기능
        - 이름, 한 줄 소개와 같은 기본적인 정보 수정
- [ ]  **게시물 CRUD 기능**
    - 게시물 작성, 조회, 수정, 삭제 기능
        - 게시물 조회를 제외한 나머지 기능들은 전부 인가(Authorization) 개념이 적용되어야 하며 이는 JWT와 같은 토큰으로 검증 가능.
        - 오로지 본인만 게시글 삭제 가능.
    
- [ ]  **뉴스 피드 기능**
    - 뉴스 피드 페이지(전체 조회 페이지)
        - 사용자가 다른 사용자의 게시물을 한 눈에 볼 수 있는 뉴스 피드 페이지가 있어야 합니다.
