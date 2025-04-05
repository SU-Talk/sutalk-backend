# SU-Talk Backend

이 프로젝트는 SU-Talk(수톡) 중고거래 플랫폼의 백엔드 서버입니다.  
Spring Boot 기반이며, 데이터베이스는 AWS의 MariaDB RDS를 사용합니다.

---

## 📚 기술 스택

- **Backend:** Spring Boot
- **Database:** MariaDB (Amazon RDS)
- **Deployment:** Docker, AWS EC2

---

## 🚩 주의 사항

데이터베이스 접속 정보는 로컬이나 AWS Secrets Manager 등 별도의 보안 환경에서 관리하시기 바랍니다.

---

## 🔧 프로젝트 DB 연결 예시 (`application.properties`)

```properties
spring.datasource.url=jdbc:mariadb://[엔드포인트]:3306/[데이터베이스이름]
spring.datasource.username=[사용자명]
spring.datasource.password=[비밀번호]
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
```

# 📁 프로젝트 구조
현재 프로젝트의 기본 폴더 구조는 다음과 같습니다.

```
 src
├── main
│   ├── java
│   │   └── com.example.sutalk
│   │       ├── controller  - API 요청 처리
│   │       ├── service     - 비즈니스 로직 구현
│   │       ├── repository  - 데이터 접근 계층
│   │       └── entity      - 데이터베이스 엔티티 클래스
│   └── resources
│       └── application.properties - 설정 파일
└── test
    └── java - 테스트 코드 작성
```
    
# 🚧 향후 작업 계획
 비즈니스 로직 추가 개발

 API 테스트 진행

 Docker 이미지 생성 및 EC2 배포

# 📌 개발 현황
상태	작업 내용
```
✅	AWS RDS 데이터베이스 설정 완료
✅	Entity 및 Repository 구성 완료
🔜	비즈니스 로직 구현 및 배포 준비
```
