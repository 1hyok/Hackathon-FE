안드로이드 모바일 앱의 "로그인" 화면을 디자인해줘.

**디자인 시스템 (기존 스타일 가이드 준수):**
- Primary 색상: #E10818 (빨간색)
- 배경: White (#FFFFFF)
- 텍스트: Black (#000000), Gray700 (#383838)
- 폰트: Pretendard (Bold, SemiBold, Medium)
- 타이포그래피:
  - Head1_bold: 24px, Bold, lineHeight 28px
  - Head2_bold: 22px, Bold, lineHeight 24px
  - Sub1_semibold: 18px, SemiBold, lineHeight 20px
  - Body_semibold: 14px, SemiBold, lineHeight 16px
  - Caption_medium: 12px, Medium, lineHeight 14px

**화면 구성 요소:**
1. 상단 바 (Top Bar)
   - 배경색: Primary 색상 (#E10818) 전체 배경
   - 제목: "로그인" 또는 앱 로고 "쩝쩝박사" (Head2_bold, 22px, White 텍스트)
   - 뒤로가기 아이콘 (왼쪽, White 아이콘) - 선택 사항 (스플래시 화면에서 진입 시 제외)
   - 상태 바(상태 표시줄)도 Primary 색상으로 통일

2. 로고/타이틀 영역 (선택 사항)
   - 앱 로고 또는 "쩝쩝박사" 텍스트 (Head1_bold 또는 Head2_bold)
   - 중앙 정렬 또는 상단 정렬
   - Primary 색상 또는 Black 텍스트

3. 입력 필드 영역
   - 아이디/이메일 입력 필드 (OutlinedTextField 스타일)
     - 플레이스홀더: "아이디 또는 이메일"
     - White 배경, Gray700 테두리, Primary 포커스 색상
     - 둥근 모서리 (15px)
   - 비밀번호 입력 필드 (OutlinedTextField 스타일)
     - 플레이스홀더: "비밀번호"
     - 비밀번호 표시/숨김 토글 아이콘 (오른쪽)
     - White 배경, Gray700 테두리, Primary 포커스 색상
     - 둥근 모서리 (15px)
   - 입력 필드 간 간격: 16dp

4. 로그인 버튼
   - Primary 색상 배경 (#E10818), White 텍스트
   - 전체 너비
   - 둥근 모서리 (15px)
   - 적절한 패딩 (세로 16dp)
   - 텍스트: "로그인" (Body_semibold 또는 Sub1_semibold)
   - 비활성화 상태: Gray700 배경, Gray700 텍스트 (입력 필드가 비어있을 때)

5. 추가 옵션
   - "비밀번호 찾기" 링크 (Body_medium, Primary 색상, 오른쪽 정렬)
   - 또는 "아이디 찾기" / "비밀번호 찾기" 두 개의 링크 (가로 배치)

6. 회원가입 섹션
   - 구분선 또는 텍스트: "또는" (Body_medium, Gray700, 중앙 정렬)
   - 회원가입 버튼: White 배경, Primary 테두리 (1px), Primary 텍스트
     - 전체 너비
     - 둥근 모서리 (15px)
     - 텍스트: "회원가입" (Body_semibold 또는 Sub1_semibold)

7. 소셜 로그인 (선택 사항)
   - 구분선 또는 텍스트: "간편 로그인" (Body_medium, Gray700, 중앙 정렬)
   - 소셜 로그인 버튼들 (가로 배치 또는 세로 배치)
     - 카카오 로그인: 카카오 브랜드 색상 (#FEE500)
     - 네이버 로그인: 네이버 브랜드 색상 (#03C75A)
     - 구글 로그인: 구글 브랜드 색상 또는 White 배경 + 테두리
     - 각 버튼: 아이콘 + 텍스트 또는 아이콘만
     - 둥근 모서리 (15px)

**레이아웃:**
- 화면 크기: 390x844 (Android 기본)
- 패딩: 16dp (좌우)
- 요소 간 간격: 16dp
- 섹션 간 간격: 24dp
- 중앙 정렬 또는 상단 정렬 (로그인 폼은 화면 중앙 또는 상단 1/3 지점에 배치)
- 스크롤 가능한 세로 레이아웃 (키보드가 올라올 때 대비)

**스타일 가이드:**
- 입력 필드: White 배경, Gray700 테두리 (1px), Primary 포커스 색상, 15px 둥근 모서리
- 버튼: Primary 배경, 15px 둥근 모서리, 적절한 패딩
- 링크: Primary 색상, Body_medium 또는 Caption_medium
- 그림자: 버튼에 4px 4px 4px rgba(0,0,0,0.25) (선택 사항)

**예원이 디자인한 기존 화면 스타일 (반드시 참고하여 동일한 톤 유지):**

**홈 화면 디자인:**
- 상단 바: Primary 색상 (#E10818) 전체 배경, 높이 약 56dp
  - 왼쪽: "쩝쩝박사" 로고 텍스트 (White, Head2_bold 또는 Head1_bold)
  - 오른쪽: 검색 아이콘 (White, Material Icons 스타일)
  - 상태 바(상태 표시줄)도 Primary 색상으로 통일
- 탭 영역: 상단 바 바로 아래
  - "전체" / "추천" 두 개의 탭 (TabRow 스타일)
  - 선택된 탭: Primary 색상 언더라인 또는 배경
  - 미선택 탭: Gray700 텍스트
- 조합 카드 리스트:
  - 각 카드: White 배경, Primary 테두리 (1px), 둥근 모서리 (15px)
  - 카드 내용: 상단에 이미지 썸네일, 제목 (Head2_bold 또는 Sub1_semibold), 설명 (Body_medium), 하단에 해시태그 칩들
  - 해시태그 칩: Primary 배경 (#E10818), White 텍스트, 작은 크기 (약 20px 높이), 15px 둥근 모서리, 예시: "#서브웨이", "#편의점"
  - 카드 간 간격: 12dp
- 하단 네비게이션: 5개 아이콘 (홈, 검색, 플러스, 하트, 마이페이지), 선택된 아이콘은 Primary 색상

**상세 화면 디자인:**
- 상단 바: Primary 색상 (#E10818) 전체 배경, 높이 약 56dp
  - 왼쪽: 뒤로가기 아이콘 (White, Material Icons ArrowBack)
  - 중앙: 제목 텍스트 (White, Head2_bold, 예: "건희 소스")
  - 오른쪽: 하트 아이콘 (White, Material Icons Favorite)
  - 상태 바(상태 표시줄)도 Primary 색상으로 통일
- 탭 영역: 상단 바 바로 아래
  - "설명" / "사진" 두 개의 탭 (TabRow 스타일)
  - 선택된 탭: Primary 색상 언더라인 또는 배경
  - 미선택 탭: Gray700 텍스트
- 내용 영역: White 배경, 스크롤 가능
  - 제목: "사전" (Head2_bold 또는 Head1_bold)
  - 불릿 포인트 리스트: Body_medium 또는 Body_semibold, 각 항목 앞에 불릿(•) 또는 번호

**스타일 일관성:**
- 모든 상단 바는 Primary 색상 배경, White 텍스트/아이콘
- 입력 필드는 White 배경 + Gray700 테두리 + 15px 둥근 모서리
- 버튼은 Primary 배경 + White 텍스트 + 15px 둥근 모서리
- 모든 텍스트는 Pretendard 폰트 사용

**추가 고려 사항:**
- 키보드가 올라올 때 입력 필드가 가려지지 않도록 레이아웃 조정
- 로그인 실패 시 에러 메시지 표시 영역 (빨간색 텍스트, Body_medium 또는 Caption_medium)
- 로딩 상태 표시 (로그인 버튼 클릭 시 CircularProgressIndicator 또는 버튼 내부에 표시)
