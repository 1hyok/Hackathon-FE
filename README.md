# Hackathon - 음식 조합 공유 앱

해커톤 프로젝트: 음식점 꿀조합 공유 앱 (서브웨이, 하이디라오, 편의점 등)

## 📱 주요 기능

- ✅ **로그인/로그아웃**: 일반 로그인 및 소셜 로그인 (카카오, 네이버, 구글)
- ✅ **홈 화면**: 조합 목록 조회, 카테고리 필터, 검색 기능
- ✅ **조합 상세**: 조합 상세 정보 보기, 좋아요 기능
- ✅ **조합 등록**: 새 조합 등록 (이미지, 제목, 재료, 만드는 방법)
- ✅ **마이페이지**: 내가 등록한 조합, 좋아요한 조합 관리

## 🛠️ 기술 스택

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Network**: Retrofit + OkHttp + Kotlinx Serialization
- **Image Loading**: Coil 3
- **Navigation**: Navigation Compose

## 📋 코드 품질 도구

이 프로젝트는 자동화된 코드 품질 검사를 사용합니다.

### 자동 실행 방법

#### 1. Git Hook (커밋 전 자동 실행)
커밋 시 자동으로 코드 품질 검사가 실행됩니다.

**⚠️ 주의**: Windows에서 Git Hook은 Git Bash를 사용할 때만 자동 실행됩니다.
PowerShell이나 CMD에서는 자동 실행되지 않으므로, 수동으로 검사를 실행하세요.

`````bash
# Git Bash에서 커밋 시 자동 실행
git commit -m "your message"
# 자동으로 Ktlint + Detekt 실행

# PowerShell/CMD에서는 수동 실행 필요
.\scripts\check-code-quality.ps1
```

#### 2. Gradle Task (빌드 시 자동 포맷팅)
빌드 시 자동으로 코드 포맷팅이 실행됩니다 (검사는 Git Hook에서 처리).

`````bash
./gradlew assembleDebug
# 자동으로 Ktlint 포맷팅 실행 (검사는 Git Hook에서 처리)
```

#### 3. 수동 실행

**Windows (PowerShell)**:
`````powershell
.\scripts\check-code-quality.ps1
```

**Git Bash / Linux / Mac**:
`````bash
./scripts/check-code-quality.sh
```

**Gradle 직접 실행**:
`````bash
# 코드 품질 검사 통합 실행
./gradlew codeQualityCheck

# 개별 실행
./gradlew ktlintFormat    # 코드 포맷팅
./gradlew ktlintCheck     # 코드 스타일 검사
./gradlew detekt          # 코드 품질 검사

### 테스트 실행

**Windows (PowerShell)**:
```powershell
# 모든 테스트 실행
.\scripts\run-tests.ps1

# 특정 테스트 클래스만 실행
.\scripts\run-tests.ps1 "CreateCombinationViewModelTest"
```

**Git Bash / Linux / Mac**:
```bash
# 모든 테스트 실행
./scripts/run-tests.sh

# 특정 테스트 클래스만 실행
./scripts/run-tests.sh "CreateCombinationViewModelTest"
```

**Gradle 직접 실행**:
```bash
# 모든 테스트 실행
./gradlew test

# 특정 테스트 클래스만 실행
./gradlew test --tests "CreateCombinationViewModelTest"
```


```

## 📁 프로젝트 구조

```
com.example.hackathon/
├── data/                    # 데이터 레이어
│   ├── dto/                # Request/Response 모델
│   ├── mapper/              # Entity ↔ DTO 변환
│   ├── repositoryimpl/     # Repository 구현
│   └── service/            # API 인터페이스
├── domain/                  # 도메인 레이어
│   ├── entity/             # 비즈니스 엔티티
│   └── repository/          # Repository 인터페이스
├── presentation/            # 프레젠테이션 레이어
│   ├── navigation/         # 네비게이션
│   ├── route/              # 라우트 정의
│   ├── screen/             # 화면 컴포저블
│   └── viewmodel/          # ViewModel
├── core/                    # 공통
│   └── component/          # 공통 컴포넌트
├── di/                      # 의존성 주입
└── ui/theme/               # 테마 설정
```

## 🚀 시작하기

### 환경 요구사항

- Android Studio Hedgehog (2023.1.1) 이상
- JDK 17 이상
- Android SDK 36 (compileSdk, targetSdk)
- Min SDK 36

### 실행 방법

1. 프로젝트 클론
   `````bash
   git clone https://github.com/1hyok/Hackathon-FE.git
   cd Hackathon-FE
   ```

2. Android Studio에서 열기
   - `File` → `Open` → 프로젝트 폴더 선택

3. Gradle 동기화
   - Android Studio가 자동으로 동기화하거나
   - `File` → `Sync Project with Gradle Files`

4. 실행!
   - 에뮬레이터 또는 실제 기기 연결
   - `Run` 버튼 클릭 또는 `Shift + F10`

## 👥 팀 구성

- **일혁**: 로그인 화면, 조합 등록 화면, 마이페이지, 프로필 수정 화면
- **예원**: 홈 화면, 조합 상세 화면

## 🌿 브랜치 전략

- `main`: 메인 브랜치 (안정적인 코드)
- `ilhyuk`: 일혁 작업 브랜치
- `yewon`: 예원 작업 브랜치

각자 브랜치에서 작업 후 `main`에 병합합니다.

## 📝 참고 문서

- [PROJECT_MEMO.md](./PROJECT_MEMO.md) - 프로젝트 메모 및 상세 정보
- [CHECKLIST.md](./CHECKLIST.md) - 작업 체크리스트
- [CONVENTION.md](./CONVENTION.md) - 코딩 컨벤션
- [GIT_STRATEGY.md](./GIT_STRATEGY.md) - Git 전략
- [.cursorrules](./.cursorrules) - Cursor AI 규칙

## 🎨 디자인

- Figma 디자인 파일 참고
- Figma AI 프롬프트:
  - [로그인 화면](./FIGMA_AI_PROMPT_LOGIN.md)
  - [조합 등록 화면](./FIGMA_AI_PROMPT_CREATE_COMBINATION.md)
  - [마이페이지](./FIGMA_AI_PROMPT_MY_PAGE.md)
  - [프로필 수정 화면](./FIGMA_AI_PROMPT_EDIT_PROFILE.md)

## 📌 주요 특징

- **다크 모드 미지원**: 항상 라이트 모드만 사용
- **Material 3**: 최신 Material Design 3 적용
- **Clean Architecture**: 레이어 분리로 유지보수성 향상
- **코드 품질**: Ktlint + Detekt로 자동 코드 품질 검사
