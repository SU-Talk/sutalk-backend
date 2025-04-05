# 🚀 SU-Talk Backend 서버

안녕하세요! 👋  
이곳은 SU-Talk(수톡) 중고거래 앱의 백엔드 서버 레포지토리입니다.  
저희는 지금 **Spring Boot**와 **MariaDB**를 이용해서 멋진 중고거래 플랫폼을 만들고 있어요!  

백엔드 데이터베이스는 **AWS RDS**에서 관리하고 있답니다 😊✨

---

## 🌱 현재 사용하고 있는 기술들

- 🌐 **Backend** : Spring Boot (Java)
- 🗃️ **Database** : MariaDB (Amazon RDS)
- 🐳 **배포 계획** : Docker + AWS EC2

---

## 📌 주의사항! (꼭 읽어주세요)

지금까지 작업한 AWS RDS 데이터베이스의 엔드포인트, 아이디, 비밀번호 등은 **민감한 정보**예요!  

**❌ 절대로 GitHub Public 레포지토리에 공개하시면 안 됩니다! ❌**

이런 정보들은 여러분의 개인 로컬 설정파일이나 AWS Secrets Manager 같은 보안이 유지된 환경에서 관리해 주세요.

---

## 🛠️ RDS 데이터베이스 설정 예시

여러분이 로컬에서 작업할 때 아래의 설정 예시를 참고하세요!  
**반드시 본인의 RDS 정보는 개인적으로 관리해 주세요! 🙏**

**`application.properties` 예시**

```properties
spring.datasource.url=jdbc:mariadb://[여기에_엔드포인트_작성]:3306/[데이터베이스이름]
spring.datasource.username=[아이디]
spring.datasource.password=[비밀번호]
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
