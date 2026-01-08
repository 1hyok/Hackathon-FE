안드로이드 모바일 앱의 "프로필 수정" 화면을 디자인해줘.

**디자인 시스템:**
- Primary 색상: #E10818 (빨간색)
- 배경: White (#FFFFFF)
- 텍스트: Black (#000000), Gray700 (#383838)
- Error 색상: Material 3 기본 Error 색상 (빨간색 계열)
- 폰트: Pretendard (Bold, SemiBold, Medium)
- 타이포그래피:
  - Head1_bold: 24px, Bold, lineHeight 28px
  - Head2_bold: 22px, Bold, lineHeight 24px
  - Sub1_semibold: 18px, SemiBold, lineHeight 20px
  - Body_semibold: 14px, SemiBold, lineHeight 16px
  - Caption_medium: 12px, Medium, lineHeight 14px
  - DisplaySmall: Material 3 기본 크기 (큰 텍스트용)

**화면 구성 요소 (위에서 아래 순서):**

1. 상단 바 (TopAppBar)
   - 배경색: Primary (#E10818) 전체 배경, 높이 56dp
   - 제목: "프로필 수정" (중앙 정렬, White, Head2_bold)
   - 왼쪽: 뒤로가기 아이콘 (White, Material Icons ArrowBack)
   - 상태 바(상태 표시줄)도 Primary 색상으로 통일

2. 화면 내용 영역 (상단 바 아래, 16dp 패딩)
   - 상태에 따라 다른 UI 표시:

   a) 로딩 상태
      - 중앙 정렬 CircularProgressIndicator
      - 전체 화면 사용

   b) 에러 상태 (프로필 로드 실패)
      - 에러 메시지 텍스트 (Error 색상, 중앙 정렬, BodyMedium)
      - "다시 시도" 버튼 (Primary 배경, White 텍스트, 중앙 정렬)
      - 메시지와 버튼 간격: 16dp
      - 전체 화면 중앙 정렬

   c) 정상 상태 (프로필 수정 폼)
      - 세로 스크롤 가능한 Column 레이아웃
      - 요소 간 세로 간격: 24dp
      - 좌우 패딩: 16dp

      구성 요소 (위에서 아래 순서):

      (1) 프로필 이미지 영역
          - 중앙 정렬
          - 프로필 이미지: 원형 120dp, Primary Container 색상 배경
          - 이미지 없을 때: 닉네임의 첫 글자 표시 (DisplaySmall 타이포그래피, 중앙 정렬)
          - 이미지 선택 기능은 추후 추가 가능 (현재는 표시만)

      (2) 닉네임 입력 필드
          - OutlinedTextField 스타일
          - 전체 너비 사용
          - 라벨: "닉네임"
          - 단일 라인 입력
          - 포커스 시: Primary 색상 테두리 및 라벨
          - 비활성화 상태: 저장 중일 때 비활성화 (enabled = false)
          - White 배경, Gray700 기본 테두리

      (3) 저장 버튼
          - 전체 너비 사용
          - Primary 색상 배경, White 텍스트
          - Material 3 버튼 스타일, 둥근 모서리
          - 비활성화 조건: 저장 중이거나 닉네임이 비어있을 때
          - 저장 중 상태:
            - 버튼 내부에 CircularProgressIndicator (20dp, White 색상)
            - 인디케이터와 텍스트 간격: 8dp
            - 텍스트: "저장 중..."
          - 일반 상태:
            - 텍스트: "저장"

      (4) 에러 메시지 (저장 실패 시)
          - 저장 버튼 아래 표시
          - Error 색상 텍스트
          - BodySmall 타이포그래피
          - 저장 성공 시 숨김

**레이아웃:**
- 화면 크기: 390x844 (Android 기본)
- 전체: Scaffold 레이아웃
- 내부 패딩: 16dp (좌우 및 상하)
- 요소 간 세로 간격: 24dp (정상 상태)
- 스크롤: 세로 스크롤 가능 (키보드가 올라올 때 대비)

**스타일 가이드:**
- 상단 바: Primary 배경, White 텍스트/아이콘
- 프로필 이미지: 원형, Primary Container 배경 (이미지 없을 때)
- 입력 필드: White 배경, Gray700 기본 테두리, Primary 포커스 색상
- 버튼: Material 3 스타일, Primary 배경, 둥근 모서리
- 에러 메시지: Error 색상, BodySmall 타이포그래피

**인터랙션:**
- 뒤로가기 아이콘 클릭: 이전 화면으로 이동
- 닉네임 입력: 실시간으로 상태 업데이트
- 저장 버튼 클릭:
  - 저장 중: 로딩 인디케이터 표시, 버튼 비활성화
  - 저장 성공: 이전 화면(마이페이지)으로 자동 이동
  - 저장 실패: 에러 메시지 표시
- "다시 시도" 버튼 클릭: 프로필 정보 다시 로드

**상태 관리:**
- 화면 진입 시: 프로필 정보 자동 로드
- 로딩 중: CircularProgressIndicator 표시
- 에러 발생: 에러 메시지 + 재시도 버튼 표시
- 저장 중: 입력 필드 비활성화, 저장 버튼에 로딩 인디케이터 표시
- 저장 실패: 에러 메시지 표시 (저장 버튼 아래)
- 저장 성공: 자동으로 이전 화면으로 이동

**유효성 검사:**
- 닉네임이 비어있으면 저장 버튼 비활성화
- 저장 중에는 모든 입력 필드 비활성화
