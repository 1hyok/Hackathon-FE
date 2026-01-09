# Hackathon - 음식 조합 공유 앱

본 프로젝트는 사용자들이 자신만의 조합(레시피)을 등록하고 공유할 수 있는  
Android 기반 조합 공유 애플리케이션이다.

사용자는 조합을 검색하고 카테고리별로 탐색할 수 있으며,  
마음에 드는 조합에 좋아요를 누르거나 직접 새로운 조합을 등록할 수 있다.  
또한 마이 페이지를 통해 자신이 작성한 조합과 좋아요한 조합을 한눈에 관리할 수 있다.

## 📱 주요 기능

- ✅ **로그인/로그아웃**: 일반 로그인 기능 및 로그아웃 기능
- ✅ **홈 화면**: 조합 목록 조회, 카테고리 필터, 검색 기능
- ✅ **조합 상세**: 조합 상세 정보 보기, 좋아요 기능
- ✅ **조합 등록**: 새 조합 등록 (이미지, 제목, 재료, 만드는 방법)
- ✅ **마이 페이지**: 나의 레시피, 좋아요한 조합 모아 보기 기능

## 서비스 기능별 코드 설명
## 1️⃣ 로그인 / 로그아웃 기능

### 📌 기능 설명
- 사용자 로그인 및 회원가입
- 토큰 기반 인증 관리
- 로그아웃 및 인증 상태 초기화

### 📂 코드 위치

#### UI
- `presentation/screen/auth/LoginScreen.kt`
- `presentation/screen/auth/RegistrationScreen.kt`
- `presentation/screen/auth/RegistrationSuccessScreen.kt`

#### ViewModel
- `presentation/viewmodel/LoginViewModel.kt`
- `presentation/viewmodel/RegistrationViewModel.kt`

#### Repository
- `domain/repository/AuthRepository.kt`
- `data/repositoryimpl/AuthRepositoryImpl.kt`

#### Network / Data
- `data/service/AuthService.kt`
- `data/dto/request/LoginRequest.kt`
- `data/dto/request/SignupRequest.kt`
- `data/dto/response/LoginResponse.kt`
- `data/local/TokenManager.kt`
- `data/network/AuthInterceptor.kt`

### ✏️ 주요 코드 설명
- 인증 로직을 Repository로 분리하여 UI와 분리
- Interceptor를 통해 모든 요청에 토큰 자동 삽입
- TokenManager로 로그인 상태 일관성 유지

#### 🔑 AuthInterceptor (인증 인터셉터)

`AuthInterceptor`는 모든 네트워크 요청에 대해  
**Access Token을 자동으로 추가하는 역할**을 담당하는 핵심 인증 컴포넌트이다.


- 인증이 필요한 API 요청에 `Authorization: Bearer <token>` 헤더 자동 삽입
- 로그인 / 회원가입 / 토큰 재발급 요청은 인증 없이 통과
- UI나 Repository에서 인증 처리를 직접 하지 않도록 분리


1. 모든 네트워크 요청이 전송되기 전 Interceptor를 거침
2. 요청 URL을 기준으로 인증 필요 여부 판단
3. 인증이 필요한 경우:
   - `TokenManager`에서 Access Token 조회
   - 토큰이 존재하면 Authorization 헤더 추가
4. 인증이 필요 없는 경우:
   - 원본 요청 그대로 서버로 전달

---

## 2️⃣ 홈 화면 (조합 목록 / 카테고리 / 검색)

### 📌 기능 설명
- 조합(레시피) 목록 조회
- 카테고리 기반 필터링
- 키워드 검색 기능 제공

### 📂 주요 코드 위치

#### UI
- `presentation/screen/home/HomeScreen.kt`
- `presentation/screen/home/HomeContent.kt`
- `presentation/screen/home/HomeCombinationList.kt`
- `presentation/screen/home/HomeTopBar.kt`
- `presentation/screen/home/SearchScreen.kt`

#### ViewModel
- `presentation/viewmodel/HomeViewModel.kt`
- `presentation/viewmodel/SearchViewModel.kt`

#### Repository
- `domain/repository/CombinationRepository.kt`
- `data/repositoryimpl/CombinationRepositoryImpl.kt`

#### Data
- `data/service/HomeService.kt`
- `data/dto/response/CombinationResponse.kt`
- `data/mapper/CombinationMapper.kt`

#### 공통 컴포넌트
- `core/component/CombinationCard.kt`
- `core/component/CategoryChip.kt`
- `core/component/SearchBar.kt`

### ✏️ 주요 코드 설명
- 홈 화면의 상태 관리 및 비즈니스 로직은 ViewModel에서 처리
- 카테고리 선택 및 검색어 입력에 따라 조합 목록을 동적으로 갱신
- 공통 UI 컴포넌트를 분리하여 재사용성과 UI 일관성 확보

#### 🏠 HomeViewModel (홈 화면 상태 관리)

`HomeViewModel`은 홈 화면에서 발생하는  
**조합 목록 조회, 카테고리 필터링, 검색 상태**를 통합 관리하는 핵심 ViewModel이다.

- 홈 화면 진입 시 조합 목록을 서버로부터 조회
- 선택된 카테고리 상태를 기반으로 목록 필터링
- 검색어 입력 시 검색 결과를 상태로 관리
- UI는 ViewModel이 제공하는 상태(StateFlow)를 관찰하여 렌더링

1. 화면 진입 또는 사용자 액션 발생
2. ViewModel에서 Repository를 통해 데이터 요청
3. 서버 응답을 Domain Entity로 변환
4. 상태(State) 갱신 → UI 자동 리렌더링


#### 🧩 CombinationCard (조합 카드 컴포넌트)

`CombinationCard`는 조합(레시피)을 표현하는  
홈 화면의 **기본 UI 단위 컴포넌트**이다.

- 조합 이미지, 제목, 간단한 정보 표시
- 클릭 이벤트를 외부에서 주입받아 화면 이동 처리
- 홈 / 검색 / 마이 페이지 등 여러 화면에서 재사용 가능

- UI 중복 제거
- 컴포넌트 단위 설계로 유지보수성 향상
- 화면 간 일관된 사용자 경험 제공

---

## 3️⃣ 조합 상세 화면 (상세 조회 / 좋아요)

### 📌 기능 설명
- 선택한 조합의 상세 정보 조회
- 좋아요(찜) 기능 제공

### 📂 주요 코드 위치

#### UI
- `presentation/screen/combination/DetailScreen.kt`

#### ViewModel
- `presentation/viewmodel/DetailViewModel.kt`

#### Repository
- `domain/repository/RecipeRepository.kt`
- `data/repositoryimpl/RecipeRepositoryImpl.kt`

#### Data / Mapper
- `data/service/RecipeService.kt`
- `data/dto/response/RecipeDetailResponse.kt`
- `data/mapper/RecipeMapper.kt`
- `domain/entity/RecipeDetail.kt`

### ✏️ 설명
- 단일 조합 상태만 관리하도록 ViewModel 구성
- 서버 응답 DTO를 Mapper를 통해 Domain Entity로 변환
- UI는 상태 변화에 따라 자동 렌더링

### ✏️ 주요 코드 설명
- 조합 상세 화면은 **단일 조합 데이터 상태만** 관리하도록 구성
- 상세 조회 및 좋아요 상태 변경 로직은 ViewModel에서 처리
- 서버 응답 DTO는 Mapper를 통해 Domain Entity로 변환하여 사용
- UI는 ViewModel이 제공하는 상태(State)를 관찰하여 자동 렌더링

#### 📄 DetailViewModel (조합 상세 상태 관리)

`DetailViewModel`은 선택된 조합 하나에 대한  
**상세 정보 조회 및 좋아요 상태를 관리하는 핵심 ViewModel**이다.

- 조합 ID를 기반으로 상세 정보 요청
- 서버로부터 받은 조합 데이터를 상태로 관리
- 좋아요 버튼 클릭 시 좋아요 상태 갱신
- UI는 상태 변화에 따라 자동으로 업데이트됨

1. 상세 화면 진입 시 조합 ID 전달
2. ViewModel에서 Repository를 통해 상세 정보 요청
3. 응답 데이터를 상태(State)로 저장
4. 좋아요 버튼 클릭 시 상태 업데이트
5. 상태 변경 → UI 자동 리렌더링

#### 🔄 RecipeMapper (DTO → Domain 변환)

`RecipeMapper`는 서버로부터 전달받은  
`RecipeDetailResponse`를 앱 내부에서 사용하는  
`RecipeDetail` Domain Entity로 변환하는 역할을 담당한다.

- 네트워크 모델(DTO)과 앱 내부 모델(Entity) 분리
- 데이터 계층과 도메인 계층 간 의존성 제거
- 서버 응답 구조 변경 시 영향 범위를 Mapper로 한정

- Clean Architecture 기반 계층 분리 강화
- ViewModel과 UI에서 서버 모델에 직접 의존하지 않도록 설계

---

## 4️⃣ 조합 등록 기능

### 📌 기능 설명
- 새로운 조합(레시피) 등록
- 이미지, 재료, 만드는 방법 입력

### 📂 주요 코드 위치

#### UI
- `presentation/screen/combination/CreateCombinationScreen.kt`

#### ViewModel
- `presentation/viewmodel/CreateCombinationViewModel.kt`

#### Data
- `data/dto/request/CreateCombinationRequest.kt`
- `data/service/CombinationService.kt`

### ✏️ 주요 코드 설명
- 조합 등록 화면은 사용자 입력을 기반으로 동작하는 폼(Form) 형태의 화면
- 입력 값(제목, 재료, 만드는 방법, 이미지 등)은 ViewModel에서 상태로 관리
- 등록 요청은 Request DTO로 묶어 서버에 전달
- 등록 성공 시 홈 화면으로 이동하도록 흐름 구성


#### ✍️ CreateCombinationViewModel (조합 등록 상태 관리)

`CreateCombinationViewModel`은 조합 등록 화면에서 발생하는  
**모든 입력 상태와 등록 이벤트를 관리하는 핵심 ViewModel**이다.

- 제목, 재료, 만드는 방법 등 입력 값 상태 관리
- 이미지 선택 결과를 상태로 저장
- 등록 버튼 클릭 시 입력값을 Request DTO로 변환하여 서버 요청
- 등록 성공/실패 결과에 따라 UI 상태 갱신

1. 사용자 입력 발생 (텍스트 / 이미지 선택)
2. ViewModel에서 입력 상태 업데이트
3. 등록 버튼 클릭 시 `CreateCombinationRequest` 생성
4. Repository(Service)를 통해 서버에 등록 요청
5. 성공 시 화면 이동, 실패 시 에러 상태 처리

#### 📦 CreateCombinationRequest (등록 요청 DTO)

`CreateCombinationRequest`는 조합 등록 시 서버로 전달되는  
**데이터 구조를 정의하는 Request DTO**이다.

- 제목, 재료 목록, 조리 방법, 이미지 정보 포함
- 서버 API 스펙과 1:1로 매핑
- UI 및 ViewModel이 서버 구조에 직접 의존하지 않도록 분리

- 네트워크 계층과 UI 계층 간 명확한 역할 분리
- 요청 데이터 구조 변경 시 영향 범위를 DTO로 한정

---

## 5️⃣ 마이 페이지 (내 조합 / 좋아요한 조합)

### 📌 기능 설명
- 사용자 프로필 조회
- 내가 작성한 조합 목록 조회
- 좋아요한 조합 모아보기
- 프로필 수정 기능

### 📂 주요 코드 위치

#### UI
- `presentation/screen/profile/MyScreen.kt`
- `presentation/screen/profile/EditProfileScreen.kt`

#### ViewModel
- `presentation/viewmodel/MyPageViewModel.kt`
- `presentation/viewmodel/EditProfileViewModel.kt`

#### Repository
- `domain/repository/UserRepository.kt`
- `data/repositoryimpl/UserRepositoryImpl.kt`

#### Data
- `data/service/UserService.kt`
- `data/dto/request/UpdateProfileRequest.kt`
- `data/dto/response/UserResponse.kt`

### ✏️ 설명
- 사용자 관련 기능을 UserRepository로 통합 관리
- 프로필 수정과 조회 로직을 분리하여 책임 명확화


### ✏️ 주요 코드 설명
- 마이 페이지는 사용자 기준 기능을 담당하는 화면으로 구성
- 사용자 프로필 정보, 작성한 조합, 좋아요한 조합을 한 화면에서 제공
- 사용자 관련 데이터 접근은 UserRepository를 통해 일괄 관리
- 프로필 조회와 수정 로직을 ViewModel 단위로 분리하여 책임 명확화

#### 👤 MyPageViewModel (마이 페이지 상태 관리)

`MyPageViewModel`은 마이 페이지에서 필요한  
**사용자 정보 및 사용자 관련 조합 데이터를 관리하는 핵심 ViewModel**이다.

- 사용자 프로필 정보 조회
- 내가 작성한 조합 목록 조회
- 좋아요한 조합 목록 조회
- 선택된 탭 또는 화면 상태에 따른 데이터 관리

1. 마이 페이지 진입 시 사용자 정보 요청
2. Repository를 통해 사용자 관련 데이터 조회
3. 조회 결과를 상태(State)로 저장
4. UI는 상태 변화를 관찰하여 화면 갱신

---

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

- **일혁**: 로그인 화면, 회원가입 화면, 온보딩 화면, 조합 등록 화면, 검색 화면
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

## 📌 주요 특징

- **다크 모드 미지원**: 항상 라이트 모드만 사용
- **Material 3**: 최신 Material Design 3 적용
- **Clean Architecture**: 레이어 분리로 유지보수성 향상
- **코드 품질**: Ktlint + Detekt로 자동 코드 품질 검사

## 🚀 APK 자동 배포

메인 브랜치에 푸시될 때마다 APK가 자동으로 빌드되고 팀원들에게 배포됩니다.

### 설정 방법

#### 옵션 1: Firebase App Distribution (추천)

1. **Firebase 프로젝트 생성**
   - [Firebase Console](https://console.firebase.google.com)에서 프로젝트 생성
   - 안드로이드 앱 추가 (패키지명: `com.example.hackathon`)

2. **google-services.json 파일 다운로드 및 보안 처리**
   - Firebase 콘솔에서 `google-services.json` 파일 다운로드
   - **⚠️ 보안 주의**: 이 파일은 민감한 정보를 포함하므로 GitHub 저장소에 직접 올리지 마세요!
   - 파일을 Base64로 인코딩:
     ```powershell
     # Windows (PowerShell)
     [Convert]::ToBase64String([IO.File]::ReadAllBytes("google-services.json")) | Out-File google-services-base64.txt
     ```
     ```bash
     # Mac/Linux
     base64 -i google-services.json > google-services-base64.txt
     ```
   - `google-services-base64.txt` 파일의 전체 내용을 복사

3. **Firebase CLI 토큰 발급**
   ```bash
   firebase login:ci
   ```
   발급된 토큰을 복사

4. **Firebase App ID 확인**
   - Firebase 콘솔 → 프로젝트 설정 → 일반 탭
   - App ID 복사

5. **GitHub Secrets 설정**
   - GitHub 저장소 → Settings → Secrets and variables → Actions
   - 다음 Secrets 추가:
     - `GOOGLE_SERVICES_JSON`: Base64로 인코딩된 `google-services.json` 내용 (2단계에서 복사한 전체 문자열)
     - `FIREBASE_APP_ID`: Firebase App ID
     - `FIREBASE_TOKEN`: Firebase CLI 토큰
     - `DISCORD_WEBHOOK`: Discord 웹훅 URL (선택사항)

6. **Firebase 테스터 그룹 생성**
   - Firebase 콘솔 → App Distribution → 테스터 및 그룹
   - "testers" 그룹 생성 및 팀원 이메일 추가

7. **워크플로우 활성화**
   - `.github/workflows/deploy-apk.yml` 파일이 자동으로 사용됩니다
   - 워크플로우는 GitHub Secret에서 `google-services.json` 파일을 자동으로 생성합니다

#### 옵션 2: GitHub Releases (Firebase 없이)

1. **GitHub Secrets 설정**
   - `DISCORD_WEBHOOK`: Discord 웹훅 URL (선택사항)

2. **워크플로우 활성화**
   - `.github/workflows/deploy-apk-github-releases.yml` 파일 사용
   - `deploy-apk.yml` 파일을 비활성화하거나 삭제

### Discord 웹훅 설정 (선택사항)

1. Discord 채널 설정 → 연동 → 웹훅 생성
2. 웹훅 URL을 GitHub Secrets에 `DISCORD_WEBHOOK`으로 저장

### 팀원 사용 방법

#### Firebase App Distribution 사용 시:
1. 처음 한 번만 [Firebase App Tester 앱](https://play.google.com/store/apps/details?id=com.google.firebase.appdistribution) 설치
2. 메인 브랜치 업데이트 시 자동으로 이메일 및 앱 푸시 알림 수신
3. 알림 클릭 → 원터치 설치

#### GitHub Releases 사용 시:
1. Discord 알림에서 링크 클릭
2. GitHub Releases 페이지에서 APK 다운로드
3. 안드로이드 기기에서 "출처를 알 수 없는 앱" 설치 권한 허용
4. APK 설치
