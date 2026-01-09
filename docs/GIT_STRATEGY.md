# Git 브랜치 전략 (해커톤용)

## 기본 원칙

### 공통 코드 → main에 바로 푸시
- 데이터 모델 (Entity, DTO)
- 테마 설정 (Color, Typography, Theme)
- 공통 컴포넌트 (CombinationCard, CategoryChip, SearchBar)
- 네비게이션 설정
- 의존성 주입 모듈

### 개인 작업 → 브랜치 사용
- 각자 담당 화면 구현
- 관련 ViewModel, Repository

---

## 브랜치 네이밍 전략

### 옵션 1: 이름만 사용 (추천 - 가장 간단)
```
main
├── yeowon          # 예원의 모든 작업
└── ilhyeok         # 일혁의 모든 작업
```

**장점:**
- 가장 간단함
- 브랜치 전환 빨리 가능
- 해커톤에 적합

**단점:**
- 여러 기능을 한 브랜치에 작업

### 옵션 2: 이름 + 기능 (권장)
```
main
├── yeowon/home-screen
├── yeowon/detail-screen
├── ilhyeok/create-screen
└── ilhyeok/my-page
```

**장점:**
- 기능별로 명확
- 나중에 리뷰하기 쉬움

**단점:**
- 브랜치가 많아짐

### 옵션 3: 이름 + 간단한 기능 (절충안)
```
main
├── yeowon/feature
└── ilhyeok/feature
```

---

## 추천 전략 (해커톤용)

### 브랜치 구조
```
main (공통 코드)
├── yeowon (예원 작업)
└── ilhyeok (일혁 작업)
```

### 작업 흐름

1. **공통 작업 시작 전**
   ```bash
   git checkout main
   git pull origin main
   ```

2. **공통 코드 작업 후**
   ```bash
   git add .
   git commit -m "feat: 공통 컴포넌트 추가"
   git push origin main
   ```

3. **개인 작업 시작**
   ```bash
   git checkout -b ilhyeok  # 또는 yeowon
   # 작업...
   git add .
   git commit -m "feat: 조합 등록 화면 구현"
   git push origin ilhyeok
   ```

4. **개인 작업 완료 후 main에 머지**
   ```bash
   git checkout main
   git merge ilhyeok
   git push origin main
   ```

---

## 역할별 브랜치

### 예원 (yeowon)
- 홈 화면 (HomeScreen)
- 조합 상세 화면 (DiaryScreen)
- 관련 ViewModel, Repository

### 일혁 (ilhyeok)
- **인증 관련**: 로그인 화면 (LoginScreen), 회원가입 화면 (RegistrationScreen), 온보딩 화면 (OnboardingScreen), 회원가입 완료 화면 (RegistrationSuccessScreen)
- **조합 관련**: 조합 등록 화면 (CreateCombinationScreen), 이미지 서버 업로드
- **기타 화면**: 검색 화면 (SearchScreen)
- **공통 작업**: 공통 컴포넌트 (이미 main에 있음), 폰트 설정, API 연동 (일혁 담당 화면들), 에러 처리 개선, 로딩 상태 개선

---

## 충돌 방지 팁

1. **작업 전 항상 pull**
   ```bash
   git checkout main
   git pull origin main
   ```

2. **브랜치 작업 전 main 최신화**
   ```bash
   git checkout ilhyeok
   git merge main  # 또는 rebase
   ```

3. **작업 영역 분리**
   - 예원: `presentation/screen/HomeScreen.kt`, `DiaryScreen.kt`
   - 일혁: `presentation/screen/CreateCombinationScreen.kt`, `LoginScreen.kt`, `RegistrationScreen.kt`, `OnboardingScreen.kt`, `SearchScreen.kt`

---

## 커밋 메시지 컨벤션 (간단하게)

```
feat: 기능 추가
fix: 버그 수정
refactor: 리팩토링
style: 스타일 변경
chore: 기타
```

예시:
- `feat: 조합 등록 화면 UI 구현`
- `feat: 검색 화면 UI 구현`
- `fix: 네비게이션 버그 수정`

---

## 최종 추천

**해커톤에서는 옵션 1 (이름만) 추천**

이유:
- 시간 부족
- 브랜치 전환 최소화
- 충돌 가능성 낮음 (작업 영역이 명확히 분리됨)

```bash
# 초기 설정
git checkout -b yeowon
git checkout -b ilhyeok

# 평소 작업
git checkout ilhyeok
# 작업 후
git push origin ilhyeok

# 완료되면 main에 머지
git checkout main
git merge ilhyeok
git push origin main
```

