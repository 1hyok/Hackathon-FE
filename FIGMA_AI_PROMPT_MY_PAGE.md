안드로이드 모바일 앱의 "마이페이지" 화면을 디자인해줘.

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
   - 배경색: Primary (#E10818)
   - 제목: "마이페이지" 또는 앱 로고 (White 텍스트)
   - 설정 아이콘 (오른쪽, White)

2. 프로필 섹션
   - 프로필 이미지: 원형 (64dp), Primary 배경 또는 이미지
   - 사용자 이름: Head2_bold (22px)
   - 통계 정보: "내가 등록한 조합 N개" (Body_medium, Gray700)
   - 레이아웃: 프로필 이미지 + 텍스트 (왼쪽), 설정 아이콘 (오른쪽)

3. 내가 등록한 조합 목록
   - 섹션 제목: "내가 등록한 조합" (Head2_bold)
   - 조합 카드 리스트 (LazyColumn)
     - 카드 스타일: White 배경, Primary 테두리 (1px), 둥근 모서리 (15px)
     - 카드 내용: 썸네일 이미지, 제목, 설명, 좋아요 수, 작성일
     - 카드 간 간격: 12dp
   - 빈 상태: "등록한 조합이 없습니다" 메시지 (중앙 정렬)

**레이아웃:**
- 화면 크기: 390x844 (Android 기본)
- 패딩: 16dp (좌우)
- 요소 간 간격: 16dp
- 섹션 간 간격: 24dp
- 스크롤 가능한 세로 레이아웃

**스타일 가이드:**
- 카드/박스: White 배경, Primary 테두리 (1px), 둥근 모서리 (15px)
- 그림자: 4px 4px 4px rgba(0,0,0,0.25)
- 프로필 이미지: 원형, Primary 배경 또는 그라데이션
- 아이콘: Material Icons 스타일, 적절한 크기

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
