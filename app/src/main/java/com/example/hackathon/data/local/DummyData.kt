package com.example.hackathon.data.local

import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User

object DummyData {
    val dummyUser =
        User(
            id = "user1",
            nickname = "쩝쩝박사",
            profileImageUrl = null,
        )

    // 현재 로그인한 사용자 정보 (임시 저장)
    var currentUser: User = dummyUser

    val dummyCombinations =
        listOf(
            Combination(
                id = "1",
                title = "서브웨이 꿀조합: 에그마요 + 아보카도",
                description = "에그마요에 아보카도를 추가하면 완벽한 조합이에요!",
                imageUrl = null,
                category = Category.SUBWAY,
                ingredients =
                    listOf(
                        "에그마요 15cm",
                        "아보카도 추가",
                        "올리브 오일",
                        "후추",
                    ),
                steps =
                    listOf(
                        "에그마요 15cm 주문",
                        "아보카도 추가 요청",
                        "올리브 오일 뿌리기",
                        "후추로 마무리",
                    ),
                tags = listOf("#서브웨이", "#에그마요", "#아보카도"),
                author = dummyUser,
                likeCount = 42,
                createdAt = "2024-01-05",
            ),
            Combination(
                id = "2",
                title = "하이디라오 소스 조합: 마늘 + 고추기름",
                description = "하이디라오에서 가장 맛있는 소스 조합입니다!",
                imageUrl = null,
                category = Category.HAIDILAO,
                ingredients =
                    listOf(
                        "마늘 다진 것",
                        "고추기름",
                        "참기름",
                        "간장",
                    ),
                steps =
                    listOf(
                        "마늘을 다져서 준비",
                        "고추기름과 참기름 섞기",
                        "간장으로 간 맞추기",
                        "고기와 함께 즐기기",
                    ),
                tags = listOf("#하이디라오", "#소스조합", "#마늘"),
                author = dummyUser,
                likeCount = 38,
                createdAt = "2024-01-04",
            ),
            Combination(
                id = "3",
                title = "편의점 꿀조합: 불닭볶음면 + 치즈",
                description = "편의점에서 바로 먹을 수 있는 간단한 조합",
                imageUrl = null,
                category = Category.CONVENIENCE,
                ingredients =
                    listOf(
                        "불닭볶음면",
                        "슬라이스 치즈 2장",
                        "계란",
                    ),
                steps =
                    listOf(
                        "불닭볶음면 끓이기",
                        "치즈 2장 넣기",
                        "계란 넣고 저어주기",
                        "완성!",
                    ),
                tags = listOf("#편의점", "#불닭볶음면", "#치즈"),
                author = dummyUser,
                likeCount = 56,
                createdAt = "2024-01-03",
            ),
        )
}
