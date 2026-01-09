package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.BuildConfig
import com.example.hackathon.data.mapper.toDomain
import com.example.hackathon.data.service.RecipeService
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.RecipeRanking
import com.example.hackathon.domain.repository.RecipeRepository
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl
    @Inject
    constructor(
        private val recipeService: RecipeService,
    ) : RecipeRepository {
        // 1. 전체 랭킹 조회(홈화면)
        override suspend fun getRecipeRanking(page: Int?): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.getRecipes(page = page, category = null)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe ranking data is null")
                // List<PostPreviewDto>를 RecipeRanking으로 변환
                data.toDomain(currentCategory = "ALL")
            }

        // 2. 특정 카테고리 랭킹조회
        override suspend fun getRecipeRankingByCategory(categoryId: String): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.getRecipes(page = null, category = categoryId)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe ranking data is null")
                // List<PostPreviewDto>를 RecipeRanking으로 변환
                data.toDomain(currentCategory = categoryId)
            }

        // 3. 검색/해시태그검색
        override suspend fun searchRecipes(keyword: String): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.searchRecipes(keyword)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe search data is null")
                // List<PostPreviewDto>를 RecipeRanking으로 변환
                data.toDomain(currentCategory = "SEARCH")
            }

        // 4. 레시피 상세조회
        override suspend fun getRecipeDetail(postId: Long): Result<RecipeDetail> =
            runCatching {
                val response = recipeService.getRecipeDetail(postId)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe detail data is null")
                // DetailResponse를 RecipeDetail로 변환
                data.toDomain()
            }

        // 5. 좋아요
        override suspend fun likeRecipe(id: Long): Result<Unit> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 네트워크 시뮬레이션
                    delay(300)
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
                    try {
                        val response = recipeService.likeRecipe(id)
                        if (response.isSuccessful) {
                            // 서버는 200 OK with empty body를 반환
                            Result.success(Unit)
                        } else {
                            Result.failure(Exception("서버 오류: ${response.code()} ${response.message()}"))
                        }
                    } catch (e: HttpException) {
                        // HTTP 에러 (4xx, 5xx) 처리
                        val errorMessage = when (e.code()) {
                            500 -> "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                            404 -> "레시피를 찾을 수 없습니다."
                            401 -> "인증이 필요합니다."
                            403 -> "권한이 없습니다."
                            else -> "좋아요 처리에 실패했습니다. (${e.code()})"
                        }
                        Result.failure(Exception(errorMessage))
                    } catch (e: IOException) {
                        // 네트워크 에러
                        Result.failure(Exception("네트워크 연결을 확인해주세요."))
                    }
                }
            } catch (e: Exception) {
                // 기타 예외 처리
                Result.failure(e)
            }
        }

        // 6. 좋아요 취소
        override suspend fun unlikeRecipe(postId: Long): Result<Unit> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 네트워크 시뮬레이션
                    delay(300)
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
                    try {
                        val response = recipeService.unlikeRecipe(postId)
                        if (response.isSuccessful) {
                            // 서버는 200 OK with empty body를 반환
                            Result.success(Unit)
                        } else {
                            Result.failure(Exception("서버 오류: ${response.code()} ${response.message()}"))
                        }
                    } catch (e: HttpException) {
                        // HTTP 에러 (4xx, 5xx) 처리
                        val errorMessage = when (e.code()) {
                            500 -> "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                            404 -> "레시피를 찾을 수 없습니다."
                            401 -> "인증이 필요합니다."
                            403 -> "권한이 없습니다."
                            else -> "좋아요 취소에 실패했습니다. (${e.code()})"
                        }
                        Result.failure(Exception(errorMessage))
                    } catch (e: IOException) {
                        // 네트워크 에러
                        Result.failure(Exception("네트워크 연결을 확인해주세요."))
                    }
                }
            } catch (e: Exception) {
                // 기타 예외 처리
                Result.failure(e)
            }
        }
    }
