# PK: Post Korea

### 문화재 탐방 어플리케이션

![logo.png](/images/logo.png)

> **어플리케이션 소개**
> 

문화재 탐방에 특화된 어플리케이션으로 

문화재 정보 및 실시간 위치 서비스를 제공하는 애플리케이션입니다.

---

> **기획 의도**
> 
- 도장만 찍고 끝인 기존의 스탬프 투어 서비스
- 스탬프 기능 외에 사용자에게 **동기부여**를 일으키는 서비스를 제공
- 사용자의 상황에 맞게 **계획**을 수립하고 **기록**을 남길 수 있는 기능을 제공
- 관광지가 아닌 **문화재**에 특화된 서비스를 제공하며 스탬프의 다양성을 제공하고자 함

> **서비스 대상**
> 
- 탐방 일정 작성부터 방문 기록까지 스마트폰으로 해결하고 싶은 사람
- 방문한 문화재에 대한 기록을 남겨두고 찾아보고 싶은 사람
- 탐방하면서 진행하는 역사/문화적 업적 및 수집에 관심이 있는 사람

---

> **핵심 기능**
> 

🔍 문화재 검색 및 상세보기 기능

📅 탐방 계획 관리

✏ 문화재 탐방일지 기록

🏆 미니게임 플레이와 업적을 통한 보상 획득

### 문화재 검색 및 상세보기 기능

![검색.gif](/images/검색.gif)

- 이름, 지역, 시대, 분류를 조합하여 검색
- 카카오 내비게이션으로 길 안내 기능 제공

### 탐방 계획 관리

- 탐방 계획 생성 및 수정
- 현재 날짜를 기반으로 진행 중인, 계획 중인, 경험한 탐방을 분류하여 목록으로 표시
- 계획 생성 및 수정 시 방문할 문화재를 지정하여 순서 관리 기능 제공

![탐방.gif](/images/탐방.gif)

### 문화재 탐방일지 기록

- 문화재 방문 시 기록 작성
- 기록 작성 시 태그가 자동으로 생성됨
- 태그를 이용하여 같은 주제/문화재에 대한 다른 사용자의 후기 탐색 가능

![방문.gif](/images/방문.gif)

### 미니게임과 업적을 통한 보상

- 문화재 방문 시 미니게임 활성화
- 미니게임 플레이 시 포인트와 수집 아이템 획득
- 포인트로 상점에서 수집 아이템 구매 가능
- 업적 달성 시 장착 가능한 칭호 획득 가능

![게임.gif](/images/게임.gif)

---

> **아키텍쳐 및 기술스택**
> 

![아키텍쳐.png](/images/architecture.png)

- **Front-end**
    - Android
    - Kotlin
    - Kakao OAuth API, Naver OAuth API
    - Kakao Navigation API
- **Back-end**
    - Spring Boot
    - Java 17
    - Spring Data JPA + QueryDSL
    - Spring Boot Mailing Service
    - JWT (Json Web Token)
    - Open API 3.0
- **Database**
    - MariaDB
    - Firebase
    - Amazon S3
- **CI/CD**
    - AWS EC2
    - Nginx
    - Docker
    - Jenkins
    - Gitlab + Jira
    

> **ERD**
> 

![ERD.png](/images/ERD.png)

> **API 명세서**
> 

![api 명세서.png](/images/API.png)