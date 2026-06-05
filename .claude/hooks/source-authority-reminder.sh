#!/usr/bin/env bash
# UserPromptSubmit hook — 버전/라이브러리/빌드툴체인/디자인 등 "공식 문서 권위"가
# 필요한 질문·작업을 키워드로 감지하면, 역할별 권위 소스 분기 규칙(A~F + Compose↔Kotlin 횡단)을
# additionalContext 로 주입한다. (상시 로드 대신 관련 프롬프트에서만 주입해 컨텍스트 절약)
input="$(cat)"
text="$(printf '%s' "$input" | jq -r '.prompt // empty' 2>/dev/null)"
[ -z "$text" ] && exit 0

# 버전/의존성/빌드툴체인/디자인 관련 키워드 (대소문자 무시)
if ! printf '%s' "$text" | grep -qiE '버전|version|라이브러리|library|의존성|depend|업그레이드|upgrade|마이그레이션|migrat|AGP|gradle|kotlin|compose|material|androidx|maven|좌표|핀|호환|compatib|릴리스|release|stable|KSP|hilt|retrofit|okhttp|coil|compilesdk|targetsdk|minsdk|jdk|libs\.versions|build\.gradle'; then
  exit 0
fi

RULES="$(cat <<'RULESEOF'
[작업 원칙 — 역할별 공식 문서 권위 소스]
소스는 평면적 우선순위가 아니라 역할로 구분 — "무엇을 찾느냐"로 분기해 질문 유형에 맞는 권위 소스를 쓸 것. 핵심 문서는 WebFetch 로 원문 확인하고, 답변·커밋 메시지에 출처 URL 을 명시한다.

A. 라이브러리(AndroidX): 사용법·API·버전
  1. 사용법·API·동작·마이그레이션 → developer.android.com (Architecture Guide, Library 문서, release notes). 단 release notes 상단 요약 박스(Latest/Stable/RC/Beta/Alpha)는 stable 승격을 늦게 반영하므로 "최신 버전 숫자"의 근거로는 쓰지 말 것.
  2. 최신 Stable 좌표(버전 권위) → Google Maven. 브라우징 maven.google.com, 좌표 확정은 아티팩트별 메타데이터 dl.google.com/dl/android/maven2/<group을 슬래시로>/<artifact>/maven-metadata.xml. 핀 박기 전 stable 존재 여부는 여기서 확정.
     - 보조: AndroidX Atom 피드 developer.android.com/feeds/androidx-release-notes.xml (라이브러리 전용 — AGP/Gradle 은 여기 안 뜸 → C). fallback 미러(캐시·지연 주의): androidx.tech 또는 mvnrepository.com (1차 실패 시에만).
  3. API 시그니처·소스·커밋 → android.googlesource.com / AndroidX GitHub. 버전 체크용 아님.

B. 언어(Kotlin) → kotlinlang.org. 문법·표준 라이브러리·coroutines/Flow 1차(developer.android.com 아님). 버전 좌표는 kotlinlang.org/docs 또는 GitHub JetBrains/kotlin releases.

C. 빌드 툴체인(AGP/Gradle/Studio/JDK/Build Tools/API level) → developer.android.com/build/releases. AGP 좌표는 dl.google.com/.../com/android/tools/build/gradle/maven-metadata.xml. 핵심인 AGP↔Gradle↔Studio↔JDK↔compileSdk 호환 행렬은 .../build/releases/about-agp + 버전별 release notes 가 권위. Gradle 쪽 행렬은 docs.gradle.org/current/userguide/compatibility.html.

D. 디자인(Material 3) → m3.material.io. 가이드라인·토큰·컴포넌트 권위. I/O 2026부터 Material Android = Compose-first, MDC-Android(Views)는 유지보수 모드 → 새 작업은 Compose Material3 기준.

E. 알려진 버그 → issuetracker.google.com (공개 Google Issue Tracker). 핀 박은 버전 오동작 시 "알려진 버그냐/픽스 예정이냐"는 release notes 아니라 여기.

F. (선택) 정준 패턴·샘플 → github.com/android (nowinandroid, architecture-samples, compose-samples).

횡단 — Compose↔Kotlin: Kotlin 2.0+ 는 Compose 컴파일러 버전 = Kotlin 버전. Compose Compiler Gradle plugin(org.jetbrains.kotlin.plugin.compose, version = Kotlin 버전)으로 적용. "Compose to Kotlin Compatibility Map"은 1.9 이하 전용 legacy — 2.0+ 에선 kotlinCompilerExtensionVersion 따로 핀 박지 말 것.
RULESEOF
)"

jq -n --arg ctx "$RULES" '{hookSpecificOutput:{hookEventName:"UserPromptSubmit",additionalContext:$ctx}}'
