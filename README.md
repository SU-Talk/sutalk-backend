# SU-Talk Backend

이 프로젝트는 SU-Talk(수톡) 중고거래 플랫폼의 백엔드 서버입니다.  
Spring Boot 프레임워크를 기반으로 하며, 데이터베이스는 AWS의 MariaDB RDS를 사용하고 있습니다.

---

## 기술 스택

- **Backend** : Spring Boot
- **Database** : MariaDB (Amazon RDS)
- **Deployment** : Docker, AWS EC2

---

## 프로젝트 DB 연결 예시

개발 환경에서 RDS 데이터베이스 연결 설정의 예시는 다음과 같습니다.  
실제 정보를 입력하여 사용하시기 바랍니다.

**`application.properties` 설정 예시:**

```properties
spring.datasource.url=jdbc:mariadb://[엔드포인트]:3306/[데이터베이스이름]
spring.datasource.username=[사용자명]
spring.datasource.password=[비밀번호]
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
