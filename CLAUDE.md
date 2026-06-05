# Android Project Conventions

## Project Context
- 핵심 도메인: **음식 조합·레시피 공유 앱** — 사용자가 카테고리(하이디라오·서브웨이·편의점)별로 재료·사진·해시태그를 담은 '조합'을 등록·탐색·검색하고 좋아요·랭킹으로 큐레이션. 인증·온보딩·프로필 포함. Mock/실 데이터는 `demo`/`prod` product flavor 로 분기(demo=Fake 로컬 데이터, prod=실 서버 API), `BuildConfig.BASE_URL` 로 엔드포인트 주입.

<!--
'작업 원칙'(역할별 공식 문서 권위 소스 A~F + Compose↔Kotlin 횡단) 규칙은
.claude/hooks/source-authority-reminder.sh (UserPromptSubmit hook) 로 이전됨.
버전·라이브러리·빌드툴체인·디자인 관련 프롬프트를 키워드로 감지했을 때만 주입되어
상시 컨텍스트 로드를 줄인다. 규칙 수정은 해당 훅 스크립트에서 한다.
-->
