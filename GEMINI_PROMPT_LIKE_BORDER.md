# 좋아요 버튼 테두리 표시 문제 해결

## 문제 설명
`CombinationCard.kt` 파일의 좋아요 버튼에서 좋아요를 눌렀을 때(`combination.isLiked == true`) 테두리가 표시되지 않는 문제가 있습니다.

## 현재 코드 상태
```kotlin
Box(
    modifier =
        Modifier
            .size(24.dp)
            .then(
                if (combination.isLiked) {
                    Modifier
                        .background(Color.Red, CircleShape)
                        .border(
                            width = 1.5.dp,
                            color = Color.Red,
                            shape = CircleShape,
                        )
                } else {
                    Modifier.border(
                        width = 1.5.dp,
                        color = Color.Gray,
                        shape = CircleShape,
                    )
                },
            ),
    contentAlignment = Alignment.Center,
) {
    Icon(...)
}
```

## 요구사항
1. 좋아요를 눌렀을 때(`isLiked == true`): 빨간색 배경 + 빨간색 테두리 (현재는 테두리가 보이지 않음)
2. 좋아요를 누르지 않았을 때(`isLiked == false`): 회색 테두리만 (현재 정상 작동)
3. 두 상태 모두 테두리가 명확하게 보여야 함 (디자인 일관성)

## 예상 원인
- `background`와 `border`의 적용 순서 문제
- `border`가 `background`에 가려지는 문제
- Compose의 modifier 체이닝 순서 문제

## 해결 방법 제안
1. `background`와 `border`의 순서를 조정
2. 또는 `background`를 사용하지 않고 `border`만으로 스타일링
3. 또는 다른 Compose API 사용 (예: `Surface` 등)

## 파일 위치
`app/src/main/java/com/example/hackathon/core/component/CombinationCard.kt` (약 177-199번 줄)

## 참고
- 좋아요를 누르지 않았을 때는 회색 테두리가 정상적으로 표시됨
- 좋아요를 눌렀을 때만 테두리가 보이지 않는 문제
- 디자인 일관성을 위해 두 상태 모두 테두리가 보여야 함
