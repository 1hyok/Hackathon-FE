# 프로젝트 컨벤션

## Git 컨벤션

### 커밋 메시지 규칙
```
<type>: <subject>

예시:
feat: 조합 등록 화면 UI 구현
fix: 네비게이션 버그 수정
refactor: Repository 구조 개선
style: 코드 포맷팅
chore: 의존성 업데이트
```

**Type 종류:**
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `refactor`: 코드 리팩토링
- `style`: 코드 포맷팅, 세미콜론 누락 등
- `chore`: 빌드 업무 수정, 패키지 매니저 설정 등
- `docs`: 문서 수정

### 브랜치 전략
- **main**: 공통 코드 및 완성된 기능
- **feature/***: 기능별 작업 브랜치

자세한 내용은 `GIT_STRATEGY.md` 참고

---

## 코드 컨벤션

### Kotlin 코딩 컨벤션
- [Kotlin 공식 코딩 컨벤션](https://kotlinlang.org/docs/coding-conventions.html) 준수
- Android Studio 자동 포맷팅 사용: `Ctrl + Alt + L` (Windows)

### 네이밍 규칙

#### 파일/클래스 이름
- **파일명**: PascalCase
  - 예: `HomeScreen.kt`, `CombinationCard.kt`
- **클래스명**: PascalCase
  - 예: `HomeViewModel`, `CombinationRepository`

#### 변수/함수 이름
- **변수명**: camelCase
  - 예: `selectedCategory`, `isLoading`
- **함수명**: camelCase
  - 예: `loadCombinations()`, `updateSearchQuery()`
- **상수**: UPPER_SNAKE_CASE
  - 예: `BASE_URL`, `MAX_RETRY_COUNT`

#### 패키지 구조
```
com.example.hackathon/
├── data/              # 데이터 레이어
│   ├── dto/          # Request/Response 모델
│   ├── mapper/       # Entity ↔ DTO 변환
│   ├── repositoryimpl/  # Repository 구현
│   └── service/      # API 인터페이스
├── domain/           # 도메인 레이어
│   ├── entity/       # 비즈니스 엔티티
│   └── repository/   # Repository 인터페이스
├── presentation/     # 프레젠테이션 레이어
│   ├── navigation/   # 네비게이션
│   ├── route/       # 라우트 정의
│   ├── screen/       # 화면 컴포저블
│   └── viewmodel/   # ViewModel
├── core/             # 공통
│   └── component/   # 공통 컴포넌트
├── di/               # 의존성 주입
└── ui/theme/         # 테마 설정
```

---

## 아키텍처

### MVVM 패턴 사용
- **Model**: Entity, Repository
- **View**: Compose Screen
- **ViewModel**: 상태 관리 및 비즈니스 로직

### Clean Architecture
- **data**: 데이터 소스 (API, Local DB)
- **domain**: 비즈니스 로직
- **presentation**: UI 레이어

---

## 작업 규칙

### 충돌 방지
- 작업 전 항상 `git pull origin main`
- 공통 파일 수정 시 팀원과 사전 협의

---

## 코드 리뷰

### 해커톤 특성상
- 시간 부족으로 상세한 코드 리뷰는 생략
- 커밋은 기능별로 나눠서 진행 (롤백 용이)
- 큰 변경사항은 팀원과 사전 논의

---

## TODO 관리

### TODO 주석 규칙
```kotlin
// TODO: [내용]
// TODO: 디자인 확인 후 UI 조정
// TODO: 이미지 업로드 기능 추가
// TODO: 서버 API 연동 필요
```

---

## 테스트

### 해커톤 특성상
- 단위 테스트는 생략
- 수동 테스트로 기능 확인
- 주요 기능만 동작 확인

---

## 참고 자료

- [Kotlin 코딩 컨벤션](https://kotlinlang.org/docs/coding-conventions.html)
- [Android 개발 가이드](https://developer.android.com/)
- [Material Design 3](https://m3.material.io/)

