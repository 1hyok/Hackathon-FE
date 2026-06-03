# Android Project Conventions

## Project Context
- 모듈 구조: **멀티모듈** — `:app` + `:core:*`(model·designsystem·datastore·network·domain·data) + `:feature:*`(auth·home·combination·onboarding·profile) + `build-logic` 컨벤션 플러그인. Clean Architecture + MVVM.
- 패키지: `com.example.hackathon`.
- minSdk / targetSdk / compileSdk: 모두 **36** (JVM 17). 정의 위치: `app/build.gradle.kts`.
- 빌드 툴체인: AGP 9.2.1 + Kotlin 2.3.21 + KSP 2.3.9, Compose BOM 2026.05.01. Version Catalog(`gradle/libs.versions.toml`) 강제.
- 정적 분석: **ktlint** — CI `.github/workflows/lint.yml` 의 ScaCap/action-ktlint 1.8.0, `.editorconfig` 의 `ktlint_code_style=android_studio` (call-site trailing comma 미사용). + Android Lint(`./gradlew lintDebug`). (`config/detekt*` 은 미사용 잔재 — `detekt` Gradle 태스크 없음.)
- 핵심 도메인: **음식 조합·레시피 공유 앱** — 사용자가 카테고리(하이디라오·서브웨이·편의점)별로 재료·사진·해시태그를 담은 '조합'을 등록·탐색·검색하고 좋아요·랭킹으로 큐레이션. 인증·온보딩·프로필 포함. 서버 연동 전까지 `BuildConfig.USE_MOCK_API` 로 Mock/실 API 토글, `BuildConfig.BASE_URL` 로 엔드포인트 주입.

## 작업 시작/마무리 규약 (위반 금지)
- **이슈 우선 + 브랜치 링크**: 새 브랜치는 메타데이터 완비된 GitHub 이슈에 정식 링크된 상태여야 한다. 절차 자동화는 `android-issue-branch` skill 에 위임 — 사용자가 "브랜치 파"라고만 해도 skill 진입 후 이슈부터 작성. `git checkout -b` 직행은 `git-branch-guard.sh` 가 차단.
- **자율 커밋 금지**: 변경사항을 임의로 `git commit` 하지 말 것. 빌드/lint/test 검증 결과만 보고하고 멈춤. 사용자가 Android Studio 커밋 탭에서 직접 검토·커밋한다.
- **git state 변경은 명시 지시 시에만**: `git push --force`, `git reset --hard`, `git rebase`, `git clean -f`, `git checkout -- <file>`, `git restore <file>` 등 손실 위험 동작은 사용자의 명시 지시가 있을 때만 수행. (일반 `git push` 는 `/create-pr` 같은 명시 워크플로에서 허용.)
- 위 규약은 hook으로 강제(`.claude/settings.local.json` + `.claude/hooks/`). 우회·예외 처리 시도 금지.

## 작업 원칙
- 기억으로 답하지 말 것. 라이브러리 좌표·버전·API 시그니처는 매번 검증.
- 검증 우선순위:
    1. developer.android.com (Architecture Guide, Library 공식 문서)
    2. AndroidX 릴리즈 노트 (`developer.android.com/jetpack/androidx/releases/*`)
    3. mvnrepository.com / androidx.tech (최신 Stable Maven 좌표)
    4. 필요 시 android.googlesource.com / AndroidX GitHub (시그니처·소스)
- 핵심 문서는 `web_fetch`로 원문 확인. 답변·커밋 메시지에 출처 URL 명시.
- 공식 문서와 다른 판단을 내릴 땐 근거와 트레이드오프를 명시한 뒤 진행.

## Architecture (Google 'Guide to app architecture'만)
- Layer: **UI → Domain(선택) → Data**
- SSOT: 각 데이터 타입은 단일 소스에서만 흐름.
- UDF: 상태는 위→아래, 이벤트는 아래→위.
- Data Layer 진입점은 **Repository**로 한정. ViewModel/UseCase는 DataSource(네트워크/DB/센서)에 직접 의존 금지.
- **금지 용어/패턴**: Hexagonal Architecture, Ports & Adapters, Port, Interactor 등 안드로이드 비표준 Clean Architecture 용어.

### ViewModel ↔ Repository ↔ UseCase
- ViewModel은 Repository를 직접 주입받아 호출.
- Repository를 1:1로 감싸는 프록시 UseCase는 **만들지 말 것**.
- UseCase는 다음 중 하나일 때만 도입:
    1. 여러 Repository를 조합하는 비즈니스 로직
    2. 여러 ViewModel에서 재사용되는 로직
    3. ViewModel 복잡도가 임계치를 넘었을 때
- UseCase 네이밍: `동사(현재형) + 명사 + UseCase`
  예: `GetLatestNewsWithAuthorsUseCase`, `LogOutUserUseCase`, `FormatDateUseCase`
- **Stateless** — UseCase 클래스 멤버 필드로 mutable 데이터(`var`, `MutableStateFlow`, mutable collection 등) 보유 금지. mutable 상태는 UI / Data 레이어가 보유. 공식 가이드: *"각 사용 사례에서는 기능 하나만 담당해야 하고, 변경 가능한 데이터를 포함해서는 안 됩니다"*.
- **Main-safe** — UseCase 는 main thread 에서 호출돼도 안전해야 함. 차단 작업(파일 I/O, 무거운 계산 등)은 `withContext(defaultDispatcher) { ... }` 로 background 이동. Repository 호출이 이미 suspend 면 그대로 위임 가능.
- **UseCase → UseCase 호출 허용** — 재사용 단위라 다른 UseCase 를 종속 항목으로 받아 호출 가능 (예: `GetLatestNewsWithAuthorsUseCase(... formatDateUseCase)`). 다층 도메인 정상.
- **데이터 레이어 캐싱 우선** — 복잡한 계산이라도 도메인으로 무조건 빼지 말 것. *재사용·캐싱이 더 자연스러우면 Repository / DataSource 에 두는 게 우선*. 공식 가이드: *"복잡한 계산은 재사용이나 캐싱을 유도하기 위해 데이터 레이어에서 이루어집니다"*.
- 출처: https://developer.android.com/topic/architecture/domain-layer?hl=ko

## UI Layer
- **한 화면당 단일 UI State 객체** (data class 또는 sealed class). loading/error/data 독립 스트림 분리 금지.
- 상태 노출은 `StateFlow`. `MutableStateFlow`는 반드시 `private` 캡슐화.
- 상태 수집은 `collectAsStateWithLifecycle()`. `collectAsState()` 신규 사용 금지.
- 신규 화면은 `@Composable` destination. Fragment 신규 생성은 원칙적 지양(불가피한 경우만 사유 명시 후 허용).
- 일회성 이벤트도 UI state 에 흡수 (Google 공식 권고 — "ViewModel events should always result in a UI state update"). `UiState` 의 nullable 필드(`userMessage`·`navigateTo` 등) + 화면이 소비 후 VM `onConsumed()` 콜백으로 null 처리. `Channel`/`SharedFlow` 신규 도입 금지.

## 필수 라이브러리
- DI: **Hilt** (`@HiltViewModel`). 수동 `ViewModelProvider.Factory`·Service Locator 금지.
- Navigation: **Compose Navigation** (Navigation 3 우선 검토), type-safe routes.
- 비동기: **Coroutines + Flow**.
- 직렬화: **kotlinx-serialization** (Gson 금지).
- 네트워킹: **Retrofit + KotlinxSerializationConverterFactory + OkHttp**.
- 이미지 로딩: **Coil 3.x** — `coil3` 패키지 사용 (`import coil3.compose.AsyncImage`). Coil 2.x(`coil` 패키지) 금지.
- 로컬 저장소: **DataStore (Preferences)**. 관계형 DB 필요 시 Room.
- 어노테이션 처리: **KSP only**.

## 신규 도입 금지(구버전·비표준 차단)
- LiveData → `StateFlow` / `SharedFlow`
- kapt → KSP
- findViewById / XML UI → Jetpack Compose
- AsyncTask, RxJava → Coroutines + Flow
- Deprecated Fragment 인자 전달 → `by navArgs()` 또는 `SavedStateHandle`
- GsonConverterFactory → KotlinxSerializationConverterFactory
- Coil 2.x(`coil` 패키지) → Coil 3.x(`coil3` 패키지)
- `collectAsState()` → `collectAsStateWithLifecycle()`
- Manual `ViewModelProvider.Factory` → `@HiltViewModel`
- Bare annotation on constructor `val` (`@StringRes val x: Int`) → use-site target 명시 (`@param:StringRes val x: Int`). KT-73255 deprecation warning 회피, 본 repo 컨벤션은 param-only.

## 의존성 작성 규칙
- 모든 의존성은 `libs.versions.toml`에 등록 후 모듈 `build.gradle.kts`에서 alias로 참조.
- 라이브러리 추가/업데이트 시 릴리즈 노트 URL을 PR 설명에 첨부.
- BOM이 존재하는 라이브러리(Compose·Firebase 등)는 BOM 우선 사용.

## 출력 형식
- Kotlin only. 불필요한 주석·서론·맺음말 배제.
- 코드 외 설명은 핵심 의도와 트레이드오프만 간결히.
- 라이브러리 버전 언급 시 Maven 좌표 + 출처 URL 동봉.
- 정당한 예외(레거시 통합 등)는 완화 사유를 먼저 설명한 뒤 진행.

## 빌드 / 테스트 명령
```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest                 # 또는 ./gradlew :app:testDebugUnitTest
./gradlew lintDebug                         # Android Lint (CI lint.yml). ktlint 은 CI(ScaCap/action-ktlint)로 실행 — 로컬 Gradle 태스크 없음
./gradlew :app:connectedDebugAndroidTest    # 인스트루멘티드 테스트
```

## 코드 변경 시 체크리스트
- [ ] 새 라이브러리 좌표·버전을 검색으로 검증했는가
- [ ] `libs.versions.toml`에 등록했는가
- [ ] UI 상태가 단일 객체 + `StateFlow` + `collectAsStateWithLifecycle()` 패턴인가
- [ ] Repository를 우회해 DataSource에 직접 의존하지 않는가
- [ ] 새로 만든 UseCase가 위 3가지 조건 중 하나를 충족하는가
