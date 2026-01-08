package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.data.local.DummyData
import com.example.hackathon.data.service.CombinationService
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.repository.CombinationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CombinationRepositoryImpl
    @Inject
    constructor(
        private val combinationService: CombinationService,
    ) : CombinationRepository {
        // 임시로 새로 등록한 조합을 저장하는 리스트 (서버 API 연동 전까지 사용)
        private val createdCombinations = mutableListOf<Combination>()

        // 좋아요한 조합 ID를 저장하는 Set (서버 API 연동 전까지 사용)
        private val likedCombinationIds = mutableSetOf<String>()

        // 조합별 좋아요 수를 추적하는 Map (서버 API 연동 전까지 사용)
        private val combinationLikeCounts = mutableMapOf<String, Int>()

        // TODO: 서버 API 연동 시 실제 API 호출로 변경
        override suspend fun getCombinations(category: Category?): Result<List<Combination>> {
            return try {
                // 네트워크 시뮬레이션
                delay(500)
                // 더미 데이터 + 새로 등록한 조합 합치기
                val allCombinations = DummyData.dummyCombinations + createdCombinations
                val filtered =
                    if (category == null || category == Category.ALL) {
                        allCombinations
                    } else {
                        allCombinations.filter { it.category == category }
                    }
                // 좋아요 상태 및 좋아요 수 반영
                val combinationsWithLikeStatus =
                    filtered.map { combination ->
                        val likeCount =
                            combinationLikeCounts[combination.id] ?: combination.likeCount
                        combination.copy(
                            isLiked = likedCombinationIds.contains(combination.id),
                            likeCount = likeCount,
                        )
                    }
                Result.success(combinationsWithLikeStatus)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getCombinationById(id: String): Result<Combination> {
            return try {
                delay(300)
                // 더미 데이터 + 새로 등록한 조합에서 찾기
                val allCombinations = DummyData.dummyCombinations + createdCombinations
                val combination = allCombinations.find { it.id == id }
                if (combination != null) {
                    // 좋아요 상태 및 좋아요 수 반영
                    val likeCount = combinationLikeCounts[id] ?: combination.likeCount
                    val combinationWithLikeStatus =
                        combination.copy(
                            isLiked = likedCombinationIds.contains(id),
                            likeCount = likeCount,
                        )
                    Result.success(combinationWithLikeStatus)
                } else {
                    Result.failure(Exception("조합을 찾을 수 없습니다"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun createCombination(
            title: String,
            description: String,
            category: Category,
            ingredients: List<String>,
            steps: List<String>,
            tags: List<String>,
            imageUri: android.net.Uri?,
        ): Result<Combination> {
            return try {
                delay(500)
                // TODO: 실제 API 호출
                // 이미지 업로드가 필요한 경우 여기서 처리
                // if (imageUri != null) {
                //     val imageUrl = uploadImage(imageUri)
                // }
                // val response = combinationService.createCombination(...)
                // Result.success(response.toEntity())

                // 임시로 더미 데이터 반환
                // imageUri가 있으면 임시로 문자열로 변환 (실제로는 서버에 업로드 후 URL 받아야 함)
                val imageUrl = imageUri?.toString()
                val newCombination =
                    Combination(
                        id = System.currentTimeMillis().toString(),
                        title = title,
                        description = description,
                        imageUrl = imageUrl,
                        category = category,
                        ingredients = ingredients,
                        steps = steps,
                        tags = tags,
                        // 현재 로그인한 사용자 사용
                        author = DummyData.currentUser,
                        likeCount = 0,
                        isLiked = false,
                        createdAt =
                            java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                .format(java.util.Date()),
                    )
                // 임시로 로컬 리스트에 추가 (서버 API 연동 전까지)
                createdCombinations.add(newCombination)
                Result.success(newCombination)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getMyCombinations(): Result<List<Combination>> {
            return try {
                delay(300)
                // TODO: 실제 API 호출
                // 더미 데이터 + 새로 등록한 조합에서 내 조합만 필터링
                val allCombinations = DummyData.dummyCombinations + createdCombinations
                // 현재 로그인한 사용자의 조합만
                val myCombinations =
                    allCombinations
                        .filter {
                            it.author.id == DummyData.currentUser.id
                        }
                        .map { combination ->
                            val likeCount =
                                combinationLikeCounts[combination.id] ?: combination.likeCount
                            combination.copy(
                                isLiked = likedCombinationIds.contains(combination.id),
                                likeCount = likeCount,
                            )
                        }
                Result.success(myCombinations)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getLikedCombinations(): Result<List<Combination>> {
            return try {
                delay(300)
                val allCombinations = DummyData.dummyCombinations + createdCombinations
                val likedCombinations =
                    allCombinations
                        .filter { likedCombinationIds.contains(it.id) }
                        .map { combination ->
                            val likeCount =
                                combinationLikeCounts[combination.id] ?: combination.likeCount
                            combination.copy(
                                isLiked = true,
                                likeCount = likeCount,
                            )
                        }
                Result.success(likedCombinations)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun likeCombination(id: String): Result<Unit> {
            return try {
                delay(100)
                // 좋아요 토글 로직
                val wasLiked = likedCombinationIds.contains(id)
                if (wasLiked) {
                    likedCombinationIds.remove(id)
                    // 좋아요 수 감소
                    val allCombinations = DummyData.dummyCombinations + createdCombinations
                    val combination = allCombinations.find { it.id == id }
                    if (combination != null) {
                        val currentCount = combinationLikeCounts[id] ?: combination.likeCount
                        combinationLikeCounts[id] = (currentCount - 1).coerceAtLeast(0)
                    }
                } else {
                    likedCombinationIds.add(id)
                    // 좋아요 수 증가
                    val allCombinations = DummyData.dummyCombinations + createdCombinations
                    val combination = allCombinations.find { it.id == id }
                    if (combination != null) {
                        val currentCount = combinationLikeCounts[id] ?: combination.likeCount
                        combinationLikeCounts[id] = currentCount + 1
                    }
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
