# CQRS 시스템 설계/구축 챌린지 구현 상세내용

## 목표

- CQRS 시스템을 설계하고 구축하는 방법을 배우고, 이를 통해 실무에서의 적용 가능성을 높인다.

## 아키텍처 요약

### 주요 설계 구조

| 컴포넌트                        | 역할          |
|-----------------------------|-------------|
| Spring Boot                 | REST API    |
| Spring Security             | 인증, 권한 처리   |
| JPA + QueryDSL + PostgreSQL | 도메인 모델 저장소  |
| Redis                       | 세션 및 데이터 캐싱 |
| flyway                      | 마이그레이션      |

### 통신 방식

- **통신**: RESTful API 기반

## Security

- Spring Security를 사용하여 인증 및 권한 처리를 구현하였습니다.
- 기본적으로 인증되지 않은 사용자는 모든 API에 접근할 수 없습니다.
- JWT를 사용하여 인증을 처리합니다.
- 간단한 회원가입, 로그인 페이지가 구현되어 있습니다.

### 회원가입
- [회원가입](http://localhost:8080/register)
- 이메일, 이름, 비밀번호 입력 후, 회원가입을 진행합니다.

### 로그인
- [로그인](http://localhost:8080/login)
- ID(email)/PW 로그인이 가능합니다.
  - 로그인 성공시 swagger 페이지로 이동합니다.
  - PW는 기존 Table에 컬럼이 없는 관계로, 아무값이나 입력해도 인증됩니다.
    - 로그인 가능 ID
      - ```text
        jiwon.kim@example.com
        minjun.lee@example.com
        seoyeon.park@example.com
        doyoon.jung@example.com
        haeun.choi@example.com
        junho.kang@example.com
        soyul.yoon@example.com
        minseo.hwang@example.com
        jihoon.song@example.com
        serin.lim@example.com
        ```


### Token 발급
- 로그인 후, API 를 통해 JWT를 발급받습니다. 
- `JWT 발급 API`
    ```http
    GET http://localhost:8080/api/issue/token
    ```
- 발급된 JWT는 Authorization 헤더에 Bearer Token으로 포함되어야 합니다.


## API
### Swagger
- Swagger를 사용하여 API 문서를 자동으로 생성합니다.
- Swagger UI는 `/swagger-ui/index.html`에서 확인할 수 있습니다.
