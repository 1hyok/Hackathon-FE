안드로이드 모바일 앱의 "조합 등록" 화면을 디자인해줘.

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
   - 배경색: Gray50 (#E0DCDC) 또는 White
   - 제목: "조합 등록" (Head2_bold, 22px)
   - 뒤로가기 아이콘 (왼쪽)

2. 이미지 업로드 영역
   - 회색 배경 박스 (Gray50 또는 Gray700)
   - 중앙에 카메라 아이콘
   - "이미지 등록" 텍스트 (Body_medium)
   - 둥근 모서리 (15px radius)

3. 입력 필드들 (OutlinedTextField 스타일)
   - 제목 입력: 단일 라인
   - 카테고리/태그 입력: 해시태그 시스템
     - 텍스트 입력 필드에서 해시태그 입력 (예: #서브웨이, #하이디라오, #편의점)
     - 입력된 해시태그는 칩 형태로 표시
     - 해시태그 칩: Primary 색상 배경 (#E10818), White 텍스트, 15px 둥근 모서리
     - 각 칩에 X 버튼으로 삭제 가능
     - 여러 개의 해시태그 추가 가능 (가로 스크롤 또는 줄바꿈)
     - 플레이스홀더: "#태그를 입력하세요" 또는 "예: #서브웨이 #하이디라오"
   - 공개 여부: 토글 또는 선택 버튼
   - 수량 선택: +/- 버튼과 숫자 표시
   - 설명 입력: 멀티라인 텍스트 영역

4. 하단 버튼
   - 등록하기 버튼: Primary 색상 배경, White 텍스트, 전체 너비
   - 취소 버튼: White 배경, Primary 테두리, Primary 텍스트

**레이아웃:**
- 화면 크기: 390x844 (Android 기본)
- 패딩: 16dp (좌우)
- 요소 간 간격: 16dp
- 섹션 간 간격: 24dp
- 스크롤 가능한 세로 레이아웃

**스타일 가이드:**
- 카드/박스: White 배경, Primary 테두리 (1px), 둥근 모서리 (15px)
- 그림자: 4px 4px 4px rgba(0,0,0,0.25)
- 입력 필드: White 배경, Gray700 테두리, Primary 포커스 색상
- 버튼: Primary 배경, 15px 둥근 모서리, 적절한 패딩
- 해시태그 칩: Primary 배경, White 텍스트, 12px 폰트, 20px 높이, X 아이콘 포함

**해시태그 시스템 상세:**
- 사용자가 자유롭게 해시태그 입력 (예: #서브웨이, #하이디라오, #편의점, #기타 등)
- 입력 필드에서 Enter 또는 쉼표로 태그 추가
- 추가된 태그는 Primary 색상 칩으로 표시
- 각 칩에 작은 X 버튼으로 삭제 가능
- 여러 태그를 가로로 나열 (필요시 줄바꿈)
- 기존 홈 화면에서 보이는 해시태그 칩 스타일과 동일하게 디자인

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
- 탭은 TabRow 스타일로 통일
- 카드는 White 배경 + Primary 테두리 + 15px 둥근 모서리
- 해시태그 칩은 Primary 배경 + White 텍스트로 통일
