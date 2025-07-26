# Flow Assignment - 파일 확장자 차단 시스템

파일 확장자에 따라 특정 형식의 파일을 첨부하거나 전송하지 못하도록 제한하는 웹 애플리케이션입니다.

## 📋 프로젝트 개요

이 프로젝트는 프론트엔드(React)와 백엔드(Spring Boot)로 구성된 파일 확장자 차단 시스템입니다. 사용자는 고정 확장자 목록에서 차단할 확장자를 선택하거나, 커스텀 확장자를 추가하여 파일 업로드를 제한할 수 있습니다.

## 🏗️ 프로젝트 구조

```
Flow_Assignment/
├── flow-app/                 # React 프론트엔드
│   ├── src/
│   │   ├── components/
│   │   │   ├── ExtensionBlocker.jsx    # 메인 컴포넌트
│   │   │   └── ExtensionBlocker.css    # 스타일링
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── package.json
│   └── vite.config.js
└── FlowServer/              # Spring Boot 백엔드
    ├── src/main/java/com/assignment/flow/server/
    │   ├── controller/
    │   │   └── ExtensionController.java
    │   ├── entity/
    │   │   ├── CustomExtension.java
    │   │   └── FixedExtension.java
    │   ├── repository/
    │   │   ├── CustomExtensionRepository.java
    │   │   └── FixedExtensionRepository.java
    │   ├── service/
    │   │   └── ExtensionService.java
    │   └── FlowServerApplication.java
    └── build.gradle
```

## 🚀 주요 기능

### 고정 확장자 관리
- 미리 정의된 확장자 목록에서 차단 여부를 체크박스로 선택
- 실시간으로 차단 상태 변경 가능

### 커스텀 확장자 관리
- 사용자가 직접 확장자를 입력하여 추가 (최대 200개)
- 영문 및 숫자만 허용 (20자 이내)
- 고정 확장자와의 중복 검사
- 태그 형태로 표시되며 개별 삭제 가능

### 데이터 검증
- 입력값 유효성 검사 (영문/숫자만, 길이 제한)
- 중복 확장자 검사
- 대소문자 구분 없이 처리 (모두 소문자로 변환)

## 🛠️ 기술 스택

### Frontend
- **React 19.1.0** - 사용자 인터페이스
- **Vite 7.0.6** - 빌드 도구
- **Axios 1.11.0** - HTTP 클라이언트
- **CSS3** - 스타일링

### Backend
- **Spring Boot 3.5.3** - 웹 서버
- **Java 21** - 프로그래밍 언어
- **Spring Data JPA** - 데이터 접근 계층
- **MySQL** - 데이터베이스
- **Gradle** - 빌드 도구

## 📦 설치 및 실행

### Prerequisites
- Node.js 18+ 
- Java 21
- MySQL 8.0+

### Frontend 실행
```bash
cd flow-app
npm install
npm run dev
```

### Backend 실행
```bash
cd FlowServer
./gradlew bootRun
```

### 데이터베이스 설정
1. MySQL 서버 실행
2. `flowapp` 데이터베이스 생성
3. `application.properties`에서 데이터베이스 연결 정보 수정

## 🔧 환경 설정

### Frontend 환경변수
프로젝트 루트에 `.env` 파일을 생성하고 다음을 추가:
```
VITE_API_URL=https://localhost:443
```

### Backend 설정
`FlowServer/src/main/resources/application.properties`에서 다음 설정을 확인:
- 데이터베이스 연결 정보
- SSL 인증서 설정
- 서버 포트 (443)

## 📡 API 엔드포인트

### 고정 확장자
- `GET /api/extensions/fixed` - 고정 확장자 목록 조회
- `PUT /api/extensions/fixed/{name}/status` - 고정 확장자 차단 상태 변경

### 커스텀 확장자
- `GET /api/extensions/custom` - 커스텀 확장자 목록 조회
- `POST /api/extensions/custom` - 커스텀 확장자 추가
- `DELETE /api/extensions/custom/{name}` - 커스텀 확장자 삭제

## 🎨 UI/UX 특징

- **직관적인 인터페이스**: 체크박스와 태그 형태로 쉬운 조작
- **실시간 피드백**: 에러 메시지와 로딩 상태 표시
- **반응형 디자인**: 다양한 화면 크기에 대응
- **접근성**: 키보드 네비게이션 지원 (Enter 키로 추가)

## 🔒 보안 고려사항

- **입력 검증**: 클라이언트와 서버 양쪽에서 입력값 검증
- **SQL 인젝션 방지**: JPA를 통한 안전한 데이터베이스 접근
- **HTTPS**: SSL 인증서를 통한 암호화된 통신
- **확장자 정규화**: 대소문자 구분 없이 소문자로 통일하여 처리

## 📝 개발 가이드

### 코드 스타일
- React 컴포넌트는 함수형 컴포넌트와 Hooks 사용
- Java 클래스는 Spring Boot 컨벤션 준수
- 한글 주석으로 코드 가독성 향상

### 테스트
```bash
# Frontend 테스트
npm run lint

# Backend 테스트
./gradlew test
```


## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 👥 개발자

- **Frontend**: React, Vite, Axios
- **Backend**: Spring Boot, JPA, MySQL
- **Architecture**: RESTful API, Client-Server Architecture 