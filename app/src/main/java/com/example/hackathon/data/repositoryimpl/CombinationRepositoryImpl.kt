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
                Result.success(filtered)
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
                    allCombinations.filter {
                        it.author.id == DummyData.currentUser.id
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
