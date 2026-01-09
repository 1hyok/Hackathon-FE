# Git 브랜치 전략 (해커톤용)

## 기본 원칙

### 공통 코드 → main에 바로 푸시
- 데이터 모델 (Entity, DTO)
- 테마 설정 (Color, Typography, Theme)
- 공통 컴포넌트 (CombinationCard, CategoryChip, SearchBar)
- 네비게이션 설정
- 의존성 주입 모듈

### 개인 작업 → 브랜치 사용
- 화면 구현
- 관련 ViewModel, Repository

---

## 브랜치 네이밍 전략

### 옵션 1: 기능별 브랜치 (추천)
```
main
├── feature/home-screen
├── feature/detail-screen
├── feature/create-screen
└── feature/my-page
```

**장점:**
- 기능별로 명확
- 나중에 리뷰하기 쉬움

**단점:**
- 브랜치가 많아짐

### 옵션 2: 간단한 기능 브랜치 (절충안)
```
main
├── feature/ui
└── feature/api
```

---

## 추천 전략 (해커톤용)

### 브랜치 구조
```
main (공통 코드)
└── feature (기능별 작업)
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
   git checkout -b feature/create-screen
   # 작업...
   git add .
   git commit -m "feat: 조합 등록 화면 구현"
   git push origin feature/create-screen
   ```

4. **개인 작업 완료 후 main에 머지**
   ```bash
   git checkout main
   git merge feature/create-screen
   git push origin main
   ```

---

## 충돌 방지 팁

1. **작업 전 항상 pull**
   ```bash
   git checkout main
   git pull origin main
   ```

2. **브랜치 작업 전 main 최신화**
   ```bash
   git checkout feature/your-feature
   git merge main  # 또는 rebase
   ```

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

**해커톤에서는 기능별 브랜치 추천**

이유:
- 기능별로 명확
- 나중에 리뷰하기 쉬움

```bash
# 평소 작업
git checkout -b feature/your-feature
# 작업 후
git push origin feature/your-feature

# 완료되면 main에 머지
git checkout main
git merge feature/your-feature
git push origin main
```

