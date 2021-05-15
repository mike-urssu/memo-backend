# memo-backend

메모장 API dev-1.1.0

## [Swagger-Ui 바로가기](http://.../swagger-ui/index.html)

## 기능 요구사항

### 일반 사용자 요구 기능
1. 인증기능
    - 인증 방식은 **토큰**을 활용해야 한다.
    - 토큰의 종류는 자유이다.
    - 토큰은 **Authorization** 헤더로 전달이 되어야 한다.

2. 회원가입 api 구현 : `POST /v1/auth/signup`
    - **사용자 이름과 비밀번호**를 받아서 **회원가입**을 하는 api를 구현해야 한다.
    - **비밀번호는 해시**되어 저장해야 한다.

3. 로그인 api 구현 : `POST /v1/auth/signin`
    - **사용자 이름과 비밀번호**를 받아서 **로그인**하는 api를 구현해야 한다.

4. 메모 구현
    - 메모를 **작성**하는 api를 구현해야 한다. : `POST /v1/memos/post`
    - (메모에는 제목(선택), 내용(필수), 작성일자(필수), 공개 여부(필수), 태그(선택)가 있다)
    - 메모를 **수정**하는 api를 구현해야 한다. : `PUT /v1/memos/patch/{memoId}`
    - 메모를 **삭제**하는 api를 구현해야 한다. : `DELETE /v1/memos/delete/{memoId}`
    - **모든 메모를 보는** api를 구현해야 한다. : `GET /v1/memos/list/{page}`
    - **특정 ID의 메모를 보는** api를 구현해야 한다. : `GET /v1/memos/{memoId}`
    - **태그가 동일한 메모를 보는** api를 구현해야 한다. : `GET /v1/memos/list/{page}/tags/{tag}`

### 관리자 요구 기능
1. 사용자 관리
    - **사용자의 정보를 볼** 수 있어야 한다. : `GET /v1/admin/users/list/{page}`
    - **사용자를 회원 탈퇴** 시킬 수 있어야 한다. : `DELETE /v1/admin/users/delete/{userId}`
    - **사용자에게 관리자 권한을 줄** 수 있어야 한다. `PUT /v1/users/grant/{userId}`

2. 메모 구현
    - 특정 사용자의 메모를 **삭제**하는 api를 구현해야 한다. : `DELETE /v1/admin/memos/{memoId}`
    - 특정 사용자의 **모든 메모를 보는** api를 구현해야 한다. : `GET /v1/admin/users/{userId}/memos/{page}`
    - 특정 사용자의 **태그가 동일한 메모를 보는** api를 구현해야 한다. : `GET /v1/admin"/users/{userId}/page/{page}/tag/{tag}"`
    - **모든 메모를 보는** api를 구현해야 한다. : `GET /v1/admin/memos/{page}`
    - **태그가 동일한 모든 메모를 보는** api를 구현해야 한다. : `GET /v1/admin/memos/{page}/tag/{tag}`
    - **사용된 태그를 내림차순으로 n개**를 얻을 수 있어야 한다. : `GET /v1/admin/tags/{count}`
  - **관리자**는 **일반 사용자**의 **모든 기능**을 사용할 수 있어야 한다.

## 프로그래밍 요구 사항
- 언어는 **Kotlin**을 이용하고, Web Framework **Spring Boot**를 이용한다.
- **코드 컨벤션**을 지키면서 프로그래밍 한다.
    - 어떤 코드 컨벤션을 선택해도 문제 없으나 **선택한 이유**가 있어야 한다.
- **indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용**한다.
    - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
    - 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은<br>
    함수(또는 메서드)를 분리하면 된다.
- 클래스, 함수(또는 메서드)가 **한 가지 일**만 하도록 **최대한 작게** 만들어라.
- 함수(또는 메서드)의 길이가 **15라인을 넘어가지 않도록** 구현한다.
- 함수(또는 메서드)의 **인자 수를 3개 까지만 허용**한다. 4개 이상은 허용하지 않는다.
- 주어진 기능이 잘 작동되는지 **JUnit을 사용해서 자동 테스트** 하게 만들어라.
- 모든 api의 request 정보, response 정보는 **swagger**에 명시되어야 한다.
- 동일한 자원을 얻는 API는 **동일한 엔드포인트**에서 구현되어야 한다. 
- API는 **RESTful**하게 설계되어야 한다.
- db는 **rdb**를 사용하며 dbms의 종류는 자유이다. 설치 방법은 docker container를 사용하거나 직접 설치하거나 rds를 이용하거나 자유이다.
