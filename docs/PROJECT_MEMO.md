# 해커톤 프로젝트 메모

## 📋 프로젝트 개요

**프로젝트명**: 음식점 꿀조합 공유 앱  
**기간**: 
- 사전 작업: 2일 (준비 기간)
- 해커톤 본 대회: 1일 (18:00 입장 → 08:00 발표)
**팀 구성**: 안드로이드 개발자 2명

### 서비스 컨셉
- 사용자들이 직접 레시피 등록
- 서브웨이 조합, 하이디라오 소스 조합, 편의점 꿀조합 등 공유
- 예시: 서브웨이 조합, 하이디라오 소스 조합, 편의점 꿀조합

---

## 👥 팀 상황

### 전체 팀 구성 (총 5명)
- **안드로이드**: 예원, 일혁 2명 - 디자인 + 프론트엔드 개발
- **서버**: 2명 - API 개발
- **PM**: 1명 - 기획 및 프로젝트 관리

### 작업 방식
- 디자인: Figma로 와이어프레임 제작 (둘 다 참여)
- 개발: Jetpack Compose로 안드로이드 앱 개발
- 협업: 병렬 작업 + 주기적 동기화

---

## 🛠 기술 스택

### 현재 프로젝트 상태
- **언어**: Kotlin
- **UI**: Jetpack Compose (Material3)
- **아키텍처**: Clean Architecture (data/domain/presentation)
- **의존성 주입**: Hilt
- **네트워크**: Retrofit + OkHttp + Kotlinx Serialization
- **이미지 로딩**: Coil
- **네비게이션**: Navigation Compose

### 프로젝트 구조
```
app/src/main/java/com/example/hackathon/
├── core/component/          # 공통 컴포넌트
├── data/                    # 데이터 레이어
│   ├── dto/                 # Request/Response 모델
│   ├── mapper/               # Entity ↔ DTO 변환
│   ├── repositoryimpl/      # Repository 구현
│   └── service/             # API 인터페이스
├── domain/                  # 도메인 레이어
│   ├── entity/              # 비즈니스 엔티티
│   └── repository/          # Repository 인터페이스
├── presentation/            # 프레젠테이션 레이어
│   ├── navigation/          # 네비게이션 설정
│   ├── route/               # 라우트 정의
│   └── screen/              # 화면 컴포저블
├── di/                      # 의존성 주입 모듈
└── ui/theme/                # 테마 설정
```

### 현재 구현된 화면
- ✅ MainActivity (하단 네비게이션 포함)
- ✅ HomeScreen (기본 구조만)
- ✅ DiaryScreen (기본 구조만) - **변경 필요: 조합 상세 화면으로**
- ✅ Navigation 설정 완료
- ✅ NetworkModule, ApiModule 설정 완료

---

## 📱 MVP 기능 정의

### 필수 기능
1. **홈 화면**: 조합 목록 보기
   - 카테고리 필터 (서브웨이/하이디라오/편의점)
   - 검색 기능
   - 조합 카드 리스트

2. **조합 상세 화면**: 조합 상세 정보
   - 이미지, 제목, 설명
   - 재료/비율
   - 만드는 방법
   - 좋아요, 댓글

3. **조합 등록 화면**: 새 조합 등록
   - 이미지 업로드
   - 제목, 카테고리, 재료 입력
   - 설명 입력

---

## 🔌 API 스펙 (서버팀과 협의 필요)

### 필수 API 엔드포인트
- [ ] `GET /combinations` - 조합 목록 조회
- [ ] `GET /combinations/{id}` - 조합 상세 조회
- [ ] `POST /combinations` - 조합 등록
- [ ] `GET /users/me/combinations` - 내 조합 목록
- [ ] `POST /combinations/{id}/like` - 좋아요 (선택)
- [ ] `GET /combinations/{id}/comments` - 댓글 조회 (선택)
- [ ] `POST /combinations/{id}/comments` - 댓글 작성 (선택)

### Base URL
- 현재: `https://api.example.com/` (변경 필요)

---

## 📝 역할 분담

### 예원
- 홈 화면 (조합 목록)
- 조합 상세 화면
- 관련 ViewModel, Repository

### 일혁
- 로그인 화면
- 회원가입 화면 (Registration/Sign Up)
- 온보딩 화면 (Onboarding Screen)
- 회원가입 완료 화면 (RegistrationSuccessScreen)
- 조합 등록 화면
- 검색 화면 (Search Screen)
- 공통 컴포넌트 (카드, 칩, 검색바 등)
- 폰트 설정
- API 연동 (일혁 담당 화면들)
- 에러 처리 개선
- 로딩 상태 개선
- 이미지 서버 업로드
- 검색 기능 고도화

---

## 🚨 주의사항

1. **DiaryScreen 변경 필요**: 현재 "일기" 화면인데, "조합 상세" 화면으로 변경해야 함
2. **하단 네비게이션 수정**: 현재 "홈/일기/마이페이지" → "홈/등록"으로 변경 필요
3. **API Base URL**: 서버팀과 협의 후 변경 필요
4. **더미 데이터**: API 준비 전까지 더미 데이터로 UI 먼저 완성

---

## 📚 참고 자료

### Figma 관련
- **KUIT 5기 해커톤 기획디자인 참고자료.fig** (54.4MB) - 필수 참고
- **KUIT PM 3주차_와이어프레임 sample.fig** (10.6MB) - 연습용 (선택)
- 필수 플러그인: Material Symbols, 한글입숨, Autoflow
- 선택 플러그인: Unsplash, Coolers

### 디자인 시스템 참고
- **Material Design 3**: https://m3.material.io/ (안드로이드 개발자 권장)
- LINE Design System
- millie Design Library 2.0

---

## ✅ 체크리스트

### 필수 작업
- [ ] 서버팀과 API 스펙 확정
- [ ] Figma 와이어프레임 완성
- [ ] UI Style Guide 작성
- [ ] 홈 화면 구현
- [ ] 조합 상세 화면 구현
- [ ] 조합 등록 기능 구현
- [ ] API 연동 완료

### 발표 준비 (필수 항목)
- [ ] 페인포인트 (As-Is/To-Be)
- [ ] 서비스 Overview/MVP 설명
- [ ] **UI Style Guide** (필수)
- [ ] **서비스 IA or Flow Chart** (필수)
- [ ] 프로토타입 설명 (시연 영상으로 대체 가능)

---

## 📄 참고 문서

- `CONVENTION.md`: 코드 컨벤션 및 작업 규칙
- `GIT_STRATEGY.md`: Git 브랜치 전략
- `CHECKLIST.md`: 해커톤 작업 체크리스트
- `ACTION_PLAN.md`: 지금 당장 해야 할 일
