# API 상세 명세서

안드로이드 클라이언트에서 필요한 API Request/Response 구조를 정리한 문서입니다.

## 공통 사항

### Base Response 구조
모든 API 응답은 다음 구조를 따릅니다:
```json
{
  "code": 200,
  "message": "성공 메시지",
  "data": { /* 실제 데이터 */ }
}
```

### 인증
- 대부분의 API는 JWT 토큰을 `Authorization` 헤더에 포함해야 합니다.
- 형식: `Authorization: Bearer {accessToken}`

---

## 1. Auth API (인증)

### 1.1 회원가입
**POST** `/api/auth/signup`

#### Request Body
```json
{
  "nickname": "string (필수, 최대 15자)",
  "password": "string (필수)"
}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "회원가입 성공",
  "data": {
    "userId": "string",
    "nickname": "string",
    "accessToken": "string",
    "refreshToken": "string"
  }
}
```

#### 에러 응답
- `400`: 필수 필드 누락, 닉네임 중복
- `409`: 이미 존재하는 닉네임

---

### 1.2 로그인
**POST** `/api/auth/login`

#### Request Body
```json
{
  "nickname": "string (필수)",
  "password": "string (필수)"
}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "로그인 성공",
  "data": {
    "userId": "string",
    "nickname": "string",
    "profileImageUrl": "string | null",
    "accessToken": "string",
    "refreshToken": "string"
  }
}
```

#### 에러 응답
- `401`: 잘못된 닉네임/비밀번호

---

### 1.3 토큰 재발급
**POST** `/api/auth/reissue`

#### Request Body
```json
{
  "refreshToken": "string (필수)"
}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "토큰 재발급 성공",
  "data": {
    "accessToken": "string",
    "refreshToken": "string"
  }
}
```

---

### 1.4 로그아웃
**POST** `/api/auth/logout`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Request Body
```json
{
  "refreshToken": "string (필수)"
}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "로그아웃 성공",
  "data": null
}
```

---

## 2. Users API (사용자)

### 2.1 마이페이지 조회
**GET** `/api/users/mypage`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": {
    "id": "string",
    "nickname": "string",
    "profileImageUrl": "string | null"
  }
}
```

---

### 2.2 내가 등록한 조합 목록
**GET** `/api/users/mypage/recipes`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "imageUrl": "string | null",
      "category": "string (예: '서브웨이', '하이디라오', '편의점')",
      "ingredients": ["string"],
      "steps": ["string"],
      "tags": ["string"],
      "author": {
        "id": "string",
        "nickname": "string",
        "profileImageUrl": "string | null"
      },
      "likeCount": 0,
      "isLiked": false,
      "createdAt": "string (ISO 8601 형식, 예: '2024-01-09T15:30:00Z')"
    }
  ]
}
```

---

### 2.3 좋아요 누른 조합 목록
**GET** `/api/users/mypage/likes`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "imageUrl": "string | null",
      "category": "string",
      "ingredients": ["string"],
      "steps": ["string"],
      "tags": ["string"],
      "author": {
        "id": "string",
        "nickname": "string",
        "profileImageUrl": "string | null"
      },
      "likeCount": 0,
      "isLiked": true,
      "createdAt": "string"
    }
  ]
}
```

---

### 2.4 프로필 수정
**PATCH** `/api/users/mypage`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Request Body
```json
{
  "nickname": "string (선택, 최대 15자)",
  "profileImageUrl": "string | null (선택)"
}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "수정 성공",
  "data": {
    "id": "string",
    "nickname": "string",
    "profileImageUrl": "string | null"
  }
}
```

---

## 3. Recipes API (레시피/조합)

### 3.1 전체 랭킹 조회 (홈 화면)
**GET** `/api/recipes`

#### Query Parameters
- `page`: number (선택, 기본값: 0)
- `size`: number (선택, 기본값: 20)

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "imageUrl": "string | null",
      "category": "string",
      "ingredients": ["string"],
      "steps": ["string"],
      "tags": ["string"],
      "author": {
        "id": "string",
        "nickname": "string",
        "profileImageUrl": "string | null"
      },
      "likeCount": 0,
      "isLiked": false,
      "createdAt": "string"
    }
  ]
}
```

**참고**: 홈 화면에서는 좋아요 수 기준으로 정렬된 랭킹을 보여줍니다.

---

### 3.2 특정 카테고리 랭킹 조회
**GET** `/api/recipes?category={id}`

#### Query Parameters
- `category`: string (필수, 예: '서브웨이', '하이디라오', '편의점')
- `page`: number (선택)
- `size`: number (선택)

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "imageUrl": "string | null",
      "category": "string",
      "ingredients": ["string"],
      "steps": ["string"],
      "tags": ["string"],
      "author": {
        "id": "string",
        "nickname": "string",
        "profileImageUrl": "string | null"
      },
      "likeCount": 0,
      "isLiked": false,
      "createdAt": "string"
    }
  ]
}
```

---

### 3.3 검색 / 해시태그 검색
**GET** `/api/recipes/search?keyword={word}`

#### Query Parameters
- `keyword`: string (필수)
  - 일반 검색: `keyword=매운맛`
  - 해시태그 검색: `keyword=%23매운맛` (URL 인코딩된 #)

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "imageUrl": "string | null",
      "category": "string",
      "ingredients": ["string"],
      "steps": ["string"],
      "tags": ["string"],
      "author": {
        "id": "string",
        "nickname": "string",
        "profileImageUrl": "string | null"
      },
      "likeCount": 0,
      "isLiked": false,
      "createdAt": "string"
    }
  ]
}
```

**참고**: 
- 키워드가 `#`으로 시작하면 해시태그 검색으로 처리
- 제목, 설명, 태그에서 검색

---

### 3.4 레시피 상세 조회
**GET** `/api/recipes/{id}`

#### Headers (선택)
```
Authorization: Bearer {accessToken}
```
- 토큰이 있으면 `isLiked` 필드가 정확하게 반환됨
- 토큰이 없으면 `isLiked: false`로 반환

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": {
    "id": "string",
    "title": "string",
    "description": "string",
    "imageUrl": "string | null",
    "category": "string",
    "ingredients": [
      {
        "name": "string",
        "quantity": "string"
      }
    ],
    "steps": ["string"],
    "tags": ["string"],
    "author": {
      "id": "string",
      "nickname": "string",
      "profileImageUrl": "string | null"
    },
    "likeCount": 0,
    "isLiked": false,
    "createdAt": "string"
  }
}
```

**참고**: 
- `ingredients`는 상세 화면에서는 객체 배열로 반환 (이름과 수량 분리)
- 목록 조회에서는 문자열 배열로 반환 가능

---

### 3.5 좋아요
**POST** `/api/recipes/{id}/likes`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "좋아요 성공",
  "data": {
    "likeCount": 1
  }
}
```

---

### 3.6 좋아요 취소
**DELETE** `/api/recipes/{id}/likes`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "좋아요 취소 성공",
  "data": {
    "likeCount": 0
  }
}
```

---

## 4. Register API (레시피 등록/수정)

### 4.1 레시피 등록 화면 (선택사항)
**GET** `/api/register`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "조회 성공",
  "data": {
    "categories": ["서브웨이", "하이디라오", "편의점"]
  }
}
```

**참고**: 이 API는 선택사항입니다. 카테고리 목록을 동적으로 가져올 때 사용합니다.

---

### 4.2 레시피 등록
**POST** `/api/register`

#### Headers
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

#### Request Body (Form Data)
```
title: string (필수, 최대 15자)
description: string (필수, 최대 300자)
category: string (필수, '서브웨이' | '하이디라오' | '편의점')
tags: string[] (선택, 해시태그 배열, 예: ["#매운맛", "#달콤한"])
ingredients: string[] (필수, 재료 배열, 예: ["빵", "치즈"])
  또는
ingredients: object[] (선택, 재료 객체 배열)
  [
    {
      "name": "string",
      "quantity": "string"
    }
  ]
steps: string[] (필수, 만드는 방법 배열)
images: File[] (선택, 이미지 파일들, 최대 5개)
isPublic: boolean (선택, 기본값: true)
```

#### Request Body (JSON - 이미지 제외)
```json
{
  "title": "string",
  "description": "string",
  "category": "string",
  "tags": ["string"],
  "ingredients": ["string"],
  "steps": ["string"],
  "isPublic": true
}
```

**이미지 업로드는 별도 API로 처리할 수도 있습니다.**

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "등록 성공",
  "data": {
    "id": "string",
    "title": "string",
    "description": "string",
    "imageUrl": "string | null",
    "category": "string",
    "ingredients": ["string"],
    "steps": ["string"],
    "tags": ["string"],
    "author": {
      "id": "string",
      "nickname": "string",
      "profileImageUrl": "string | null"
    },
    "likeCount": 0,
    "isLiked": false,
    "createdAt": "string"
  }
}
```

---

### 4.3 레시피 수정
**PUT** `/api/register/{id}`

#### Headers
```
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

#### Request Body
레시피 등록과 동일한 구조

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "수정 성공",
  "data": {
    "id": "string",
    "title": "string",
    "description": "string",
    "imageUrl": "string | null",
    "category": "string",
    "ingredients": ["string"],
    "steps": ["string"],
    "tags": ["string"],
    "author": {
      "id": "string",
      "nickname": "string",
      "profileImageUrl": "string | null"
    },
    "likeCount": 0,
    "isLiked": false,
    "createdAt": "string",
    "updatedAt": "string"
  }
}
```

---

### 4.4 레시피 삭제
**DELETE** `/api/register/{id}`

#### Headers
```
Authorization: Bearer {accessToken}
```

#### Response (200 OK)
```json
{
  "code": 200,
  "message": "삭제 성공",
  "data": null
}
```

---

## 데이터 타입 상세

### Category (카테고리)
- `"서브웨이"`
- `"하이디라오"`
- `"편의점"`

### Ingredient (재료)
**목록 조회 시:**
```json
"ingredients": ["빵", "치즈", "토마토"]
```

**상세 조회 시 (선택):**
```json
"ingredients": [
  {
    "name": "빵",
    "quantity": "1개"
  },
  {
    "name": "치즈",
    "quantity": "2장"
  }
]
```

### Tag (해시태그)
```json
"tags": ["#매운맛", "#달콤한", "#추천"]
```
- `#` 기호 포함
- 최대 10개

### Date Format
- ISO 8601 형식: `"2024-01-09T15:30:00Z"`
- 또는 Unix timestamp (밀리초)

---

## 에러 코드

### 공통 에러
- `400`: Bad Request (잘못된 요청)
- `401`: Unauthorized (인증 실패)
- `403`: Forbidden (권한 없음)
- `404`: Not Found (리소스 없음)
- `500`: Internal Server Error (서버 오류)

### 커스텀 에러
- `409`: Conflict (중복된 리소스)

---

## 추가 요청사항

1. **이미지 업로드**: 
   - 레시피 등록 시 이미지를 함께 업로드하는 방식과 별도 API로 분리하는 방식 중 선택
   - 이미지 최대 개수: 5개
   - 이미지 최대 크기: 5MB

2. **페이지네이션**:
   - 목록 조회 API는 페이지네이션 지원 권장
   - 기본값: page=0, size=20

3. **정렬**:
   - 홈 화면: 좋아요 수 기준 내림차순
   - 최신순 정렬도 필요할 수 있음

4. **검색**:
   - 제목, 설명, 태그에서 검색
   - 해시태그 검색은 `#`으로 시작하는 키워드로 구분

5. **좋아요**:
   - 목록 조회 시 현재 사용자가 좋아요를 눌렀는지 여부(`isLiked`) 포함 필요

---

## 화면별 필요한 데이터 요약

### 홈 화면
- 조합 목록 (제목, 이미지, 좋아요 수, 작성자)
- 카테고리 필터링
- 좋아요 수 기준 정렬

### 조합 상세 화면
- 모든 조합 정보
- 재료 상세 (이름, 수량)
- 만드는 방법
- 좋아요 상태

### 조합 등록 화면
- 제목, 설명, 카테고리, 재료, 만드는 방법, 태그, 이미지, 공개 여부

### 마이페이지
- 사용자 정보 (닉네임, 프로필 이미지)
- 내가 등록한 조합 목록
- 좋아요한 조합 목록

### 검색 화면
- 검색 결과 목록
- 해시태그 검색 지원

---

**작성일**: 2024-01-09  
**작성자**: 안드로이드 팀
