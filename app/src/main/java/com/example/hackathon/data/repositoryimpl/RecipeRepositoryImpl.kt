package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.BuildConfig
import com.example.hackathon.data.mapper.toDomain
import com.example.hackathon.data.service.RecipeService
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.RecipeRanking
import com.example.hackathon.domain.repository.RecipeRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecipeRepositoryImpl
    @Inject
    constructor(
        private val recipeService: RecipeService,
    ) : RecipeRepository {
        // 1. 전체 랭킹 조회(홈화면)
        override suspend fun getRecipeRanking(page: Int?): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.getRecipeRanking(page)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe ranking data is null")
                data.toDomain()
            }

        // 2. 특정 카테고리 랭킹조회
        override suspend fun getRecipeRankingByCategory(categoryId: String): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.getRecipeRankingByCategory(categoryId)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe ranking data is null")
                data.toDomain()
            }

        // 3. 검색/해시태그검색
        override suspend fun searchRecipes(keyword: String): Result<RecipeRanking> =
            runCatching {
                val response = recipeService.searchRecipes(keyword)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe search data is null")
                data.toDomain()
            }

        // 4. 레시피 상세조회
        override suspend fun getRecipeDetail(postId: Long): Result<RecipeDetail> =
            runCatching {
                val response = recipeService.getRecipeDetail(postId)
                val data =
                    response.data
                        ?: throw IllegalStateException("Recipe detail data is null")
                data.toDomain()
            }

        // 5. 좋아요
        override suspend fun likeRecipe(id: Long): Result<Unit> =
            runCatching {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 네트워크 시뮬레이션
                    delay(300)
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
                    val response = recipeService.likeRecipe(id)
                    if (response.code == 200) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception(response.message))
                    }
                }
            }

        // 6. 좋아요 취소
        override suspend fun unlikeRecipe(postId: Long): Result<Unit> =
            runCatching {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 네트워크 시뮬레이션
                    delay(300)
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
                    val response = recipeService.unlikeRecipe(postId)
                    if (response.code == 200) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception(response.message))
                    }
                }
            }
    }
