# File Sync Rule (CRITICAL)
- **This file (`.claude/CLAUDE.md`) and `.cursorrules` MUST stay in sync.**
- When modifying either file, apply the same changes to both files.
- **Flexible Sync**: Cursor와 Claude Code의 특성 차이로 인한 부분은 각 도구에 맞게 융통성 있게 적용:
  - Cursor 전용: Custom Commands (`/doc`, `/test` 등), Rule Management 섹션
  - Claude Code 전용: 이모지 미사용, 간결한 톤 유지
  - 공통: 아키텍처, 코딩 표준, 테스트, Git 브랜치 전략 등 핵심 규칙
- If you notice discrepancies in **core rules**, immediately synchronize them.

# Role
You are a Senior Android Developer at a Hackathon.
Prioritize build speed and crash prevention.
If a solution is complex, suggest the simplest working alternative first.
When stuck, provide a working workaround before investigating the perfect solution.

# Project Context & Tech Stack
- Project Type: Android Native App
- Language: Kotlin (Latest Stable)
- UI Framework: Jetpack Compose (Material 3)
- Build System: Gradle (Kotlin DSL / .kts)
- Dependency Management: Version Catalog (libs.versions.toml)

# User Preferences
- Language: Korean (모든 설명과 주석은 한국어로 작성)
- Tone: Concise and Technical (간결하고 전문적으로)

# Architecture Guidelines
- Pattern: MVVM (Model-View-ViewModel)
  - **Model**: Entity, Repository (Domain + Data layers)
  - **View**: Compose Screen (Presentation layer)
  - **ViewModel**: State management and business logic
  - **Rule**: Logic goes to ViewModel, not UI. UI only observes StateFlow.
- Architecture: Clean Architecture (Presentation -> Domain -> Data)
  - **data**: Data sources (API, Local DB)
  - **domain**: Business logic (Entities, Repository interfaces)
  - **presentation**: UI layer (Screens, ViewModels)
- State Management: Unidirectional Data Flow (UDF) using StateFlow/SharedFlow
- DI: Hilt (Dagger)
- Async: Coroutines & Flow
  - Use `viewModelScope` in ViewModels.
  - Use `suspend` functions for I/O operations.

# Key Libraries & Versions
- Image Loading: Coil 3.x (Note: Use `coil3` package, NOT `coil` package)
  - Import: `import coil3.compose.AsyncImage`
  - Do NOT use: `import coil.compose.AsyncImage` (Coil 2, deprecated)
- Networking: Retrofit2 + OkHttp + Kotlinx Serialization
- Serialization: Kotlin Serialization (not Gson)

## Do NOT Use (금지 라이브러리)
- **Gson**: Use Kotlin Serialization instead
- **Kotlin Synthetics**: Deprecated, use View Binding or Compose
- **AsyncTask**: Deprecated, use Coroutines
- **RxJava**: Use Coroutines & Flow instead (simpler for hackathon)
- **Coil 2.x (`coil` package)**: Use Coil 3.x (`coil3` package)
- **LiveData in new code**: Use StateFlow/SharedFlow (UDF pattern)

# Coding Standards

## Compose UI
1. Always use `@Composable` annotation properly.
2. Use `Modifier` as the first optional parameter in Composable functions.
3. Separate UI state from UI implementation (State Hoisting).
4. For Experimental Material3 APIs (TopAppBar, Scaffold topBar, etc.), always add:
   ```kotlin
   @OptIn(ExperimentalMaterial3Api::class)
   @Composable
   fun MyScreen(...) { ... }
   ```
5. Use `@Preview(showBackground = true)` for UI components when possible.
6. **KtLint Compliance (CRITICAL - Prevents Build Failures)**:
   - **Max Line Length**: Keep lines under 120 characters. If a line exceeds this, break it into multiple lines.
   - **Import Order**: Group imports: 1) Kotlin stdlib, 2) Android/AndroidX, 3) Third-party, 4) Project. Separate groups with blank lines.
   - **Indentation**: Use 4 spaces (not tabs).
   - **Trailing Commas**: Use trailing commas in multi-line parameter lists, function calls, and data classes.
   - **Spacing Rules**:
     - No space before `:` in type declarations: `val name: String` (not `val name : String`)
     - Space after `:` in when expressions: `when (x) { ... }`
     - Space around operators: `a + b` (not `a+b`)
   - **Function Formatting**: If parameters exceed line length, put each on a new line with proper indentation and trailing commas.
   - **String Formatting**: Prefer string templates: `"Hello $name"` (not `"Hello " + name`)
   - **Before Any Code Change**: Always ensure code follows KtLint rules. If unsure, break long lines and use proper spacing.
7. **Performance Optimization**:
   - Use `remember` and `derivedStateOf` to avoid unnecessary recompositions.
   - Always use standard modifiers ordering: `Modifier.size().padding().background().clickable()`.
   - Avoid hardcoding values; use `MaterialTheme.typography` and `MaterialTheme.colorScheme`.
   - Prefer `LazyColumn` over `Column` for lists to ensure recycling.
8. **State Collection**:
   - Use `collectAsStateWithLifecycle()` instead of `collectAsState()` when observing StateFlow in Compose screens.
   - Import: `import androidx.lifecycle.compose.collectAsStateWithLifecycle`

## Kotlin Conventions
1. Prefer `val` over `var`.
2. Use `when` expressions for exhaustiveness.
3. Avoid `!!` (double-bang) operator; use safe calls (`?.`) or `let`.
4. **KtLint Pre-Compliance (WRITE CODE CORRECTLY FROM THE START)**:
   - **NEVER write code that violates KtLint rules. Check BEFORE writing, not after.**
   - **Comments in parameter lists**: ALWAYS put comments on a SEPARATE LINE above the parameter, never inline.
     ```kotlin
     // Wrong - causes build failure
     fun example(
         param: String, // this is a comment
     )

     // Correct
     fun example(
         // this is a comment
         param: String,
     )
     ```
5. **KtLint Compliance (CRITICAL - Prevents Build Failures)**:
   - **Max Line Length**: Keep lines under 120 characters. If a line exceeds this, break it into multiple lines:
     ```kotlin
     // Wrong (too long)
     val result = repository.getData().fold(onSuccess = { data -> updateState(data) }, onFailure = { error -> showError(error) })

     // Correct (broken into multiple lines)
     val result = repository.getData().fold(
         onSuccess = { data -> updateState(data) },
         onFailure = { error -> showError(error) },
     )
     ```
   - **Import Order**: Group imports: 1) Kotlin stdlib, 2) Android/AndroidX, 3) Third-party, 4) Project. Separate groups with blank lines.
   - **Indentation**: Use 4 spaces (not tabs).
   - **Trailing Commas**: Always use trailing commas in multi-line parameter lists, function calls, and data classes.
   - **Spacing Rules**:
     - No space before `:` in type declarations: `val name: String` (not `val name : String`)
     - Space after `:` in when expressions: `when (x) { ... }`
     - Space around operators: `a + b` (not `a+b`)
   - **Function Formatting**: If parameters exceed line length, put each on a new line with proper indentation and trailing commas.
   - **String Formatting**: Prefer string templates: `"Hello $name"` (not `"Hello " + name`)
   - **Code Writing**: When writing code, proactively check line length and break long lines before they cause build failures.
5. **Smart Cast for Delegated Properties**:
   - Do NOT rely on smart casts for delegated properties (e.g., `by collectAsState()`, `by viewModels()`).
   - Always assign to a local variable (snapshot) before checking nullability:
   ```kotlin
   // Wrong
   if (uiState.error != null) {
       Text(text = uiState.error) // Smart cast impossible
   }

   // Correct
   val errorMessage = uiState.error ?: "오류가 발생했습니다"
   if (errorMessage != null) {
       Text(text = errorMessage)
   }
   // OR
   uiState.error?.let { error ->
       Text(text = error)
   }
   ```
6. Follow [Kotlin Official Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
7. Use Android Studio auto-formatting: `Ctrl + Alt + L` (Windows) before committing.

## Naming Conventions
- **File/Class names**: PascalCase (e.g., `HomeScreen.kt`, `CombinationCard.kt`, `HomeViewModel`)
- **Variable names**: camelCase (e.g., `selectedCategory`, `isLoading`)
- **Function names**: camelCase (e.g., `loadCombinations()`, `updateSearchQuery()`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `BASE_URL`, `MAX_RETRY_COUNT`)

## Dependency Management
1. Do NOT hardcode versions in `build.gradle.kts`.
2. Add new libraries to `gradle/libs.versions.toml` first:
   ```toml
   [versions]
   myLibrary = "1.0.0"

   [libraries]
   my-library = { group = "com.example", name = "mylib", version.ref = "myLibrary" }
   ```
3. Then reference them in `build.gradle.kts` using `libs.my.library`.

## File Naming & Structure
- Screen files: `*Screen.kt` (e.g., `HomeScreen.kt`, `DetailScreen.kt`)
- ViewModel files: `*ViewModel.kt` (e.g., `HomeViewModel.kt`)
- Component files: `*Card.kt`, `*Chip.kt`, etc.
- Use descriptive names, avoid abbreviations like "Diary" for "Detail".

## Package Structure (Clean Architecture)
```
com.example.hackathon/
├── data/                    # 데이터 레이어
│   ├── dto/                # Request/Response 모델
│   ├── mapper/              # Entity <-> DTO 변환
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

## Error Handling
- Wrap network calls in `runCatching` or `try-catch` blocks within Repository.
- Use `Result<T>` type for repository methods:
  ```kotlin
  suspend fun getData(): Result<List<Item>> {
      return runCatching {
          // API call
      }
  }
  ```
- Expose UI states as data classes with nullable error field:
  ```kotlin
  data class UiState(
      val data: List<Item> = emptyList(),
      val isLoading: Boolean = false,
      val error: String? = null
  )
  ```
- Handle errors in ViewModel using `fold()`:
  ```kotlin
  repository.getData().fold(
      onSuccess = { data -> /* update state */ },
      onFailure = { error -> /* set error state */ }
  )
  ```

## Documentation
- Write all comments and KDoc in Korean (한국어).
- Explain 'Why' we did this, not just 'What' the code does.
- Add KDoc for complex business logic.
- Keep function names descriptive.
- Add `// 담당자: 이름` comment at the top of files for hackathon collaboration.
- Add `// TODO: 내용` for future improvements.

## Testing Guidelines
- **Testing Strategy**: Focus on ViewModel unit tests (business logic validation).
  - ViewModel tests are fast and catch most bugs.
  - UI tests are optional for hackathon (time-consuming).
- **Testing Libraries**:
  - **MockK**: Mocking dependencies (`io.mockk`)
  - **Turbine**: Flow testing (`app.cash.turbine`)
  - **Kotlinx Coroutines Test**: Coroutine testing (`kotlinx.coroutines.test`)
- **Test Structure**:
  - Test files: `*Test.kt` (e.g., `CreateCombinationViewModelTest.kt`)
  - Test utilities: `util/` package (e.g., `MainDispatcherRule.kt`)
  - Use `@get:Rule val mainDispatcherRule = MainDispatcherRule()` for coroutine tests.
- **Test Naming**: Use descriptive test names with backticks:
  ```kotlin
  @Test
  fun `updateTitle updates the title in uiState`() = runTest { ... }
  ```
- **Test Execution**:
  - **Automatic**: Tests run automatically on commit (Git Hook) and code quality checks.
  - **Manual**: `./gradlew test` or `.\scripts\run-tests.ps1`
  - **Specific test**: `./gradlew test --tests "*ViewModelTest"`
- **Test Best Practices**:
  - Mock all external dependencies (Repository, API services).
  - Test state changes using Turbine: `viewModel.uiState.test { ... }`
  - Use `runTest` for coroutine-based tests.
  - Test both success and failure scenarios.
  - Keep tests fast and isolated (no shared state).

# Project-Specific Rules
- This is a hackathon project (2 days prep + 1 day event).
- Focus on working code over perfect code.
- Use dummy data when server API is not ready.
- All team members work on both design and development.

## Troubleshooting: File Lock Issues (Android Studio + Cursor)
- **Problem**: `R.jar: The process cannot access the file because it is being used by another process`
- **Cause**: Cursor's file indexing conflicts with Android Studio's Gradle build process.
- **Solution**:
  1. **Primary**: `.cursorignore` file already excludes `build/` folders (prevents indexing).
  2. **If still occurs**: Run `./gradlew --stop` (or `.\gradlew.bat --stop` on Windows) to gracefully stop Gradle daemon.
  3. **Alternative**: Use Android Studio's `Build > Clean Project` menu.
  4. **Windows Defender**: Consider excluding project folder from real-time scanning (Settings > Virus & threat protection > Exclusions).
- **Never**: Force-kill JDK processes via Task Manager (risks data corruption).

## Hackathon Mindset
- **Working code first**: `// TODO: Refactor later for hackathon` 주석을 적극 허용
- **Speed over perfection**: 완벽한 클린 코드보다 작동하는 코드 우선
- **Quick workarounds**: 복잡한 해결책보다 간단한 우회책 먼저 제시

# Git Branch Strategy
- **CRITICAL**: Always work on your own branch, NOT on main branch.
- **Branch naming**: Use your name as branch name (e.g., `ilhyuk`, `yewon`).
- **Role-based branches**:
  - **ilhyuk (일혁)**:
    - CreateCombinationScreen (조합 등록 화면)
    - MyScreen (마이페이지)
    - Related ViewModels (CreateCombinationViewModel, MyPageViewModel)
  - **yewon (예원)**:
    - HomeScreen (홈 화면)
    - DetailScreen (조합 상세 화면)
    - Related ViewModels (HomeViewModel, DetailViewModel)
- **MANDATORY: Check current branch before starting work**:
  - **ALWAYS** check current branch with `git branch --show-current` or `git status` before making any code changes.
  - If you're working on ilhyuk's tasks (CreateCombinationScreen, MyScreen), you MUST be on `ilhyuk` branch.
  - If you're working on yewon's tasks (HomeScreen, DetailScreen), you MUST be on `yewon` branch.
  - If on wrong branch, switch to correct branch first: `git checkout ilhyuk` or `git checkout yewon`.
  - **NEVER** work on `main` branch for personal tasks.
- **Workflow**:
  1. **FIRST**: Check current branch with `git branch --show-current`
  2. If wrong branch, switch: `git checkout ilhyuk` (or your branch name)
  3. Pull latest changes: `git pull origin main` (if needed)
  4. Make changes on your branch
  5. Commit and push to your branch: `git push origin ilhyuk`
  6. Merge to main only after review/approval
- **NEVER commit directly to main branch** for personal work.
- **Common code** (shared components, theme, navigation) can go to main after team discussion.
- **Commit Policy (CRITICAL)**:
  - **Documentation files only**: Only commit documentation files (`.md`, `.txt`, `.cursorrules`, etc.) automatically without user approval.
  - **Documentation merge to main**: After committing documentation files, merge them to remote main branch automatically.
  - **Code files**: NEVER commit code changes (`.kt`, `.kts`, `.xml`, etc.) without explicit user approval.
  - **Always ask**: Before committing any non-documentation files, always ask the user for permission first.
  - **Exception**: Documentation updates can be committed and merged to main automatically, but code changes require explicit confirmation.
