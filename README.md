📌 SU-Talk Backend안녕하세요! 이 프로젝트는 SU-Talk(수톡) 중고거래 플랫폼의 백엔드 서버입니다. 주 기술 스택은 Spring Boot와 MariaDB이며, AWS의 Amazon RDS 서비스를 사용하고 있습니다!
🌱 기술 스택Backend: Spring Boot
Database: MariaDB (Amazon RDS)
Deployment: Docker, AWS EC2, AWS RDS
🚨 주의 사항!현재 RDS 데이터베이스 접속 정보(엔드포인트, 사용자명, 비밀번호 등)는 민감한 정보입니다. 이러한 정보를 GitHub의 Public Repository에 공개하는 것은 절대 권장하지 않습니다. 데이터베이스 접속 정보는 로컬 설정이나 AWS Secrets Manager와 같은 별도의 비공개 환경에서 관리해주세요.
🛠️ 프로젝트 DB 연결 예시로컬 개발 환경에서 설정 예시입니다. 본인이 사용 중인 실제 RDS 정보는 개인적으로 관리해주세요!
spring.datasource.url=jdbc:mariadb://[엔드포인트]:3306/[데이터베이스 이름]
spring.datasource.username=[사용자명]
spring.datasource.password=[비밀번호]
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true🐳 Docker 및 EC2 배포SU-Talk 백엔드는 곧 Docker 컨테이너로 만들어서 AWS EC2에 배포할 계획입니다. 배포 가이드는 준비되는 대로 친절하게 안내해 드릴게요!
📂 프로젝트 구조프로젝트의 기본적인 폴더 구조는 다음과 같아요!
src
├── main
│   ├── java
│   │   └── com.example.sutalk
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       └── entity
│   └── resources
│       └── application.properties
└── test
    └── java🚧 앞으로 할 일!비즈니스 로직 개발하기
API 테스트하기
Docker 배포하고 EC2에 올리기
🎯 현재까지 진행한 내용✅ RDS 데이터베이스 설정 완료
✅ Entity와 Repository 기본 설정 완료
🔜 비즈니스 로직 개발 및 Docker 배포 준비 중
📩 도움 또는 문의 사항프로젝트에 대해 궁금한 점이나 필요한 도움은 언제든지 이슈를 열어주세요. 친절히 도와드리겠습니다! 🚀
