## Baro Intern 백엔드 개발 과제

사용자 회원가입, 로그인, 권한 변경 기능을 제공하는 스프링 부트 기반 REST API 프로젝트입니다.  
JWT 기반 인증/인가 방식을 사용합니다.

### ✅ 주요 기능
- 회원가입 (USER,ADMIN)
- 로그인 (JWT 발급)
- 사용자 권한 변경 (USER → ADMIN)

### 🚀 Swagger API Docs 실행 방법

1. http://3.36.153.76:8080/docs 접속
    ![img.png](image/img.png)
2. 회원가입(USER) API 테스트

    - USER 회원가입 API 클릭
    ![img_1.png](image/img_1.png)
    - Try it out 버튼 클릭
    ![img_2.png](image/img_2.png)
    - Execute 버튼 클릭(기존에 example@example.com으로 회원가입을 진행한 이력이 있다면 다른 이메일을 적어서 요청하세요.)
    ![img_3.png](image/img_3.png)

3. 회원가입(ADMIN) API 테스트

    - ADMIN 회원가입 API 클릭
    ![img_4.png](image/img_4.png)
    - Try it out 버튼 클릭
    ![img_5.png](image/img_5.png)
    - Execute 버튼 클릭 (기존에 example@example.com으로 회원가입을 진행한 이력이 있다면 다른 이메일을 적어서 요청하세요.)
    ![img_6.png](image/img_6.png)

4. 로그인 API 테스트
    
    - 로그인 API 클릭
    ![img_7.png](image/img_7.png)
    - Try it out 버튼 클릭
    ![img_8.png](image/img_8.png)
    - Execute 버튼 클릭(기존에 회원가입을 진행한 이메일로 실행해주세요.)
    ![img_9.png](image/img_9.png)
    - 요청에 성공한 경우 토큰 복사
    ![img_10.png](image/img_10.png)

5. 역할 변경 API 테스트

    - Authorization 버튼 클릭
    ![img_11.png](image/img_11.png)
    - 기존에 복사해두었던 토큰 입력(Bearer eyJh... 형태로 입력합니다.)
    ![img_12.png](image/img_12.png)
    - 역할 변경 API 클릭
    ![img_13.png](image/img_13.png)
    - Try it out 클릭
    ![img_14.png](image/img_14.png)
    - Execute 클릭(관리자 역할로 변경하고 싶은 사용자의 이메일을 입력해주세요. 해당 사용자는 회원가입이 되어있는 사용자여야 합니다.)
    ![img_15.png](image/img_15.png)

### 📁 프로젝트 클론 실행 방법

1️⃣ **프로젝트 클론**

```bash
git clone https://github.com/YUBIN-githubb/baro-intern.git
cd baro-intern
```

2️⃣ **필수 환경 변수 설정**

${JWT-SECRET-KEY} -> JWT 토큰 서명에 사용할 비밀키 
- 인텔리제이 환경변수 편집을 이용하여 ${JWT-SECRET-KEY} 환경변수 설정
- 혹은 개인 환경에 맞추어 환경변수 세팅

3️⃣ **프로젝트 실행**

### 📌 API 명세

| HTTP Method | URL                          | 설명                   | 요청 Body 예시                                  | 응답 예시                                                                 | 상태 코드           |
|-------------|------------------------------|----------------------|-----------------------------------------------|-----------------------------------------------------------------------|-----------------|
| **POST**    | `http://3.36.153.76:8080/api/v1/auth/user/sign-up`  | 회원가입 (USER)          | `{ "email": "test@example.com", "password": "Qwer1234!!!" }` | `{ "email": "test@example.com", "userRole": "USER" }`                 | 200 OK          |
|             |                              |                      |                                               | `{ "code": "USER_ALREADY_EXISTS", "message": "이미 가입된 사용자 이메일 입니다." }` | 409 Conflict    |
| **POST**    | `http://3.36.153.76:8080/api/v1/auth/admin/sign-up` | 회원가입 (ADMIN)         | `{ "email": "test@example.com", "password": "Qwer1234!!!" }` | `{ "email": "test@example.com", "userRole": "ADMIN" }`                | 200 OK          |
|             |                              |                      |                                               | `{ "code": "USER_ALREADY_EXISTS", "message": "이미 가입된 사용자 이메일 입니다." }` | 409 Conflict    |
| **POST**    | `http://3.36.153.76:8080/api/v1/auth/sign-in`       | 로그인 (JWT 발급)         | `{ "email": "test@example.com", "password": "Qwer1234!!!" }` | `{ "token": "Bearer eyJhbGciOiJIUzI1NiJ9..." }`                       | 200 OK          |
|             |                              |                      |                                               | `{ "code": "INVALID_CREDENTIALS", "message": "잘못된 비밀번호입니다." }`        | 400 Bad Request |
|             |                              |                      |                                               | `{ "code": "USER_NOT_FOUND", "message": "존재하지 않는 사용자입니다." }`          | 404 Not Found   |
| **PATCH**   | `http://3.36.153.76:8080/api/v1/roles`              | 사용자 역할 변경 (ADMIN 전용) | `{ "email": "targetuser@example.com" }`           | `{ "email": "targetuser@example.com", "userRole": "ADMIN" }`          | 200 OK          |
|             |                              |                      |                                               | `{ "code": "FORBIDDEN_ACCESS", "message": "접근 권한이 없습니다." }`           | 403 Forbidden   |
|             |                              |                      |                                               | `{ "code": "USER_NOT_FOUND", "message": "존재하지 않는 사용자입니다." }`          | 404 Not Found   |


### ✅ 요청 헤더 (회원가입/로그인 외 API 요청 시 필요)
| 헤더 이름       | 설명                           |
|----------------|------------------------------|
| Authorization  | `Bearer eyJhbGciOiJIUzI1NiJ9...` (로그인 후 발급된 토큰 사용) |




