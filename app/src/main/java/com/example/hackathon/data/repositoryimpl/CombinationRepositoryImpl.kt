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
        // TODO: 서버 API 연동 시 실제 API 호출로 변경
        override suspend fun getCombinations(category: Category?): Result<List<Combination>> {
            return try {
                delay(500) // 네트워크 시뮬레이션
                val allCombinations = DummyData.dummyCombinations
                val filtered =
                    if (category == null || category == Category.ALL) {
                        allCombinations
                    } else {
                        allCombinations.filter { it.category == category }
                    }
                Result.success(filtered)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getCombinationById(id: String): Result<Combination> {
            return try {
                delay(300)
                val combination = DummyData.dummyCombinations.find { it.id == id }
                if (combination != null) {
                    Result.success(combination)
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
                        author = DummyData.dummyUser,
                        likeCount = 0,
                        createdAt =
                            java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                .format(java.util.Date()),
                    )
                Result.success(newCombination)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getMyCombinations(): Result<List<Combination>> {
            return try {
                delay(300)
                // TODO: 실제 API 호출
                val myCombinations =
                    DummyData.dummyCombinations.filter {
                        it.author.id == DummyData.dummyUser.id
                    }
                Result.success(myCombinations)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun likeCombination(id: String): Result<Unit> {
            return try {
                delay(300)
                // TODO: 실제 API 호출
                // combinationService.likeCombination(id)
                // id 파라미터는 실제 API 호출 시 사용 예정
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
