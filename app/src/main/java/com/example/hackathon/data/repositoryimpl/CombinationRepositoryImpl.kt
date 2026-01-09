package com.example.hackathon.data.repositoryimpl

import android.content.Context
import android.net.Uri
import com.example.hackathon.BuildConfig
import com.example.hackathon.data.dto.request.CreateCombinationRequest
import com.example.hackathon.data.dto.request.RegisterRequest
import com.example.hackathon.data.dto.request.UpdateCombinationRequest
import com.example.hackathon.data.dto.request.UpdateRegisterRequest
import com.example.hackathon.data.dto.response.IngredientDto
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.data.mapper.toEntity
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.service.CombinationService
import com.example.hackathon.data.service.RecipeService
import com.example.hackathon.data.service.UserService
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.Ingredient
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.Stats
import com.example.hackathon.domain.entity.UserInteraction
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.Base64
import javax.inject.Inject

class CombinationRepositoryImpl
    @Inject
    constructor(
        private val combinationService: CombinationService,
        private val recipeService: RecipeService,
        private val userService: UserService,
        private val tokenManager: TokenManager,
        @ApplicationContext private val context: Context,
    ) : CombinationRepository {
        
        /**
         * Uri를 Base64 인코딩된 문자열로 변환
         * Swagger 스펙: images는 문자열 배열 (Base64 인코딩된 이미지 데이터)
         */
        private suspend fun encodeImageToBase64(uri: Uri?): String? {
            if (uri == null) return null
            return try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    Base64.getEncoder().encodeToString(bytes)
                }
            } catch (e: Exception) {
                null
            }
        }
        // 임시로 새로 등록한 조합을 저장하는 리스트 (서버 API 연동 전까지 사용)
        private val createdCombinations = mutableListOf<Combination>()

        // 좋아요한 조합 ID를 저장하는 Set (서버 API 연동 전까지 사용)
        private val likedCombinationIds = mutableSetOf<String>()

        // 조합별 좋아요 수를 추적하는 Map (서버 API 연동 전까지 사용)
        private val combinationLikeCounts = mutableMapOf<String, Int>()

        // TODO: 서버 API 연동 시 실제 API 호출로 변경
        override suspend fun getCombinations(category: Category?, page: Int, pageSize: Int): Result<List<Combination>> {
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
                
                // 페이지네이션 적용
                val startIndex = (page - 1) * pageSize
                val endIndex = startIndex + pageSize
                val paginatedCombinations = combinationsWithLikeStatus.subList(
                    startIndex.coerceAtMost(combinationsWithLikeStatus.size),
                    endIndex.coerceAtMost(combinationsWithLikeStatus.size)
                )
                
                Result.success(paginatedCombinations)
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
            tags: List<String>,
            imageUri: android.net.Uri?,
            isPublic: Boolean,
        ): Result<Combination> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 더미 데이터 반환
                    delay(500)
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
                            tags = tags,
                            // 현재 로그인한 사용자 사용
                            author = DummyData.currentUser ?: DummyData.dummyUser, // 로그인 안 됨 시 더미 사용자 사용
                            likeCount = 0,
                            isLiked = false,
                            createdAt =
                                java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                    .format(java.util.Date()),
                        )
                    // 임시로 로컬 리스트에 추가 (서버 API 연동 전까지)
                    createdCombinations.add(newCombination)
                    Result.success(newCombination)
                } else {
                    // 실제 API 호출 (Swagger 스펙: POST /register 사용)
                    // 이미지를 Base64 인코딩된 문자열로 변환
                    // 디버깅: 이미지가 문제일 수 있으므로 빈 배열로 테스트 가능
                    val imageStrings = if (imageUri != null) {
                        val base64String = encodeImageToBase64(imageUri)
                        if (base64String != null) {
                            // Base64 문자열 크기 체크 (너무 크면 서버에서 처리 실패 가능)
                            // 일반적으로 1MB 이상이면 문제가 될 수 있음
                            if (base64String.length > 1_000_000) {
                                android.util.Log.w("CombinationRepository", "Image Base64 string is too large: ${base64String.length} bytes. Consider compressing the image.")
                            }
                            listOf(base64String)
                        } else {
                            emptyList()
                        }
                    } else {
                        emptyList()
                    }
                    
                    // 디버깅: 요청 데이터 로깅 (개발 중에만)
                    if (BuildConfig.DEBUG) {
                        android.util.Log.d("CombinationRepository", "RegisterRequest: title=$title, categories=${listOf(category.name)}, ingredients count=${ingredients.size}, images count=${imageStrings.size}, description length=${description.length}")
                    }
                    
                    // 현재 사용자 ID 가져오기
                    // 1. JWT 토큰에서 추출 시도 (가장 안정적)
                    // 2. /users/mypage API 호출 시도
                    // 3. DummyData에서 fallback
                    val userId = tokenManager.getUserIdFromToken()
                        ?: try {
                            val userProfile = userService.getMyPage()
                            userProfile.id?.toLongOrNull()
                        } catch (e: Exception) {
                            // /users/mypage가 실패하면 null
                            null
                        }
                        ?: DummyData.currentUser?.id?.toLongOrNull()
                        ?: throw IllegalStateException("User ID is not available. Please login again.")
                    
                    // Swagger 스펙에 맞게 RegisterRequest 생성
                    // ingredients는 "재료명 용량" 형태의 문자열 리스트이므로 파싱 필요
                    val ingredientDtos = ingredients.map { ingredientString ->
                        // "재료명 용량" 형태를 파싱
                        // 마지막 공백을 기준으로 name과 amount 분리
                        val lastSpaceIndex = ingredientString.lastIndexOf(' ')
                        if (lastSpaceIndex > 0 && lastSpaceIndex < ingredientString.length - 1) {
                            IngredientDto(
                                name = ingredientString.substring(0, lastSpaceIndex).trim(),
                                amount = ingredientString.substring(lastSpaceIndex + 1).trim(),
                            )
                        } else {
                            // 공백이 없거나 형식이 잘못된 경우 전체를 name으로 사용
                            IngredientDto(
                                name = ingredientString.trim(),
                                amount = "",
                            )
                        }
                    }
                    
                    val registerRequest = RegisterRequest(
                        title = title,
                        categories = listOf(category.name), // 단일 카테고리를 배열로 변환
                        ingredients = ingredientDtos,
                        images = imageStrings,
                        description = description,
                        isPrivate = !isPublic, // isPublic = true면 isPrivate = false (전체 공개), isPublic = false면 isPrivate = true (나만 보기)
                    )
                    
                    val response = combinationService.register(userId, registerRequest)
                    
                    // Response<Unit>을 사용하면 빈 응답도 안전하게 처리됨
                    if (response.isSuccessful) {
                        // 200 OK면 성공으로 간주하고 임시 Combination 생성
                        val newCombination = Combination(
                            id = System.currentTimeMillis().toString(),
                            title = title,
                            description = description,
                            imageUrl = imageUri?.toString(),
                            category = category,
                            ingredients = ingredients,
                            tags = tags,
                            author = DummyData.currentUser ?: DummyData.dummyUser,
                            likeCount = 0,
                            isLiked = false,
                            createdAt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                .format(java.util.Date()),
                        )
                        createdCombinations.add(newCombination)
                        Result.success(newCombination)
                    } else {
                        throw IllegalStateException("Register failed: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun updateCombination(
            id: String,
            title: String,
            description: String,
            category: Category,
            ingredients: List<String>,
            tags: List<String>,
            imageUri: android.net.Uri?,
            isPublic: Boolean,
        ): Result<Combination> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 로컬 데이터 업데이트
                    delay(500)
                    val allCombinations = DummyData.dummyCombinations + createdCombinations
                    val existingCombination = allCombinations.find { it.id == id }
                        ?: return Result.failure(Exception("조합을 찾을 수 없습니다"))

                    val imageUrl = imageUri?.toString() ?: existingCombination.imageUrl
                    val updatedCombination =
                        existingCombination.copy(
                            title = title,
                            description = description,
                            category = category,
                            ingredients = ingredients,
                            tags = tags,
                            imageUrl = imageUrl,
                        )

                    // 로컬 리스트 업데이트
                    val index = createdCombinations.indexOfFirst { it.id == id }
                    if (index >= 0) {
                        createdCombinations[index] = updatedCombination
                    }

                    Result.success(updatedCombination)
                } else {
                    // 실제 API 호출 (Swagger 스펙: PUT /register/{postId} 사용)
                    // 이미지를 Base64 인코딩된 문자열로 변환
                    // 디버깅: 이미지가 문제일 수 있으므로 빈 배열로 테스트 가능
                    val imageStrings = if (imageUri != null) {
                        val base64String = encodeImageToBase64(imageUri)
                        if (base64String != null) {
                            // Base64 문자열 크기 체크
                            if (base64String.length > 1_000_000) {
                                android.util.Log.w("CombinationRepository", "Image Base64 string is too large: ${base64String.length} bytes. Consider compressing the image.")
                            }
                            listOf(base64String)
                        } else {
                            emptyList()
                        }
                    } else {
                        emptyList()
                    }
                    
                    // 현재 사용자 ID 가져오기
                    // 1. JWT 토큰에서 추출 시도 (가장 안정적)
                    // 2. /users/mypage API 호출 시도
                    // 3. DummyData에서 fallback
                    val userId = tokenManager.getUserIdFromToken()
                        ?: try {
                            val userProfile = userService.getMyPage()
                            userProfile.id?.toLongOrNull()
                        } catch (e: Exception) {
                            // /users/mypage가 실패하면 null
                            null
                        }
                        ?: DummyData.currentUser?.id?.toLongOrNull()
                        ?: throw IllegalStateException("User ID is not available. Please login again.")
                    
                    val postId = id.toLongOrNull()
                        ?: throw IllegalStateException("Post ID is not a valid number")
                    
                    // 디버깅: 요청 데이터 로깅 (개발 중에만)
                    if (BuildConfig.DEBUG) {
                        android.util.Log.d("CombinationRepository", "UpdateRegisterRequest: postId=$postId, title=$title, categories=${listOf(category.name)}, ingredients count=${ingredients.size}, images count=${imageStrings.size}")
                    }
                    
                    // Swagger 스펙에 맞게 UpdateRegisterRequest 생성
                    // ingredients는 "재료명 용량" 형태의 문자열 리스트이므로 파싱 필요
                    val ingredientDtos = ingredients.map { ingredientString ->
                        // "재료명 용량" 형태를 파싱
                        // 마지막 공백을 기준으로 name과 amount 분리
                        val lastSpaceIndex = ingredientString.lastIndexOf(' ')
                        if (lastSpaceIndex > 0 && lastSpaceIndex < ingredientString.length - 1) {
                            IngredientDto(
                                name = ingredientString.substring(0, lastSpaceIndex).trim(),
                                amount = ingredientString.substring(lastSpaceIndex + 1).trim(),
                            )
                        } else {
                            // 공백이 없거나 형식이 잘못된 경우 전체를 name으로 사용
                            IngredientDto(
                                name = ingredientString.trim(),
                                amount = "",
                            )
                        }
                    }
                    
                    val updateRequest = UpdateRegisterRequest(
                        title = title,
                        categories = listOf(category.name), // 단일 카테고리를 배열로 변환
                        ingredients = ingredientDtos,
                        images = imageStrings,
                        description = description,
                        isPrivate = !isPublic, // isPublic = true면 isPrivate = false (전체 공개), isPublic = false면 isPrivate = true (나만 보기)
                    )
                    
                    val response = combinationService.updateRegister(postId, userId, updateRequest)
                    
                    // Response<Unit>을 사용하면 빈 응답도 안전하게 처리됨
                    if (response.isSuccessful) {
                        // 200 OK면 성공으로 간주: 로컬 데이터 업데이트
                        val updatedCombination = Combination(
                            id = id,
                            title = title,
                            description = description,
                            imageUrl = imageUri?.toString(),
                            category = category,
                            ingredients = ingredients,
                            tags = tags,
                            author = DummyData.currentUser ?: DummyData.dummyUser,
                            likeCount = 0,
                            isLiked = false,
                            createdAt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                .format(java.util.Date()),
                        )
                        val index = createdCombinations.indexOfFirst { it.id == id }
                        if (index >= 0) {
                            createdCombinations[index] = updatedCombination
                        }
                        Result.success(updatedCombination)
                    } else {
                        throw IllegalStateException("Update register failed: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun deleteCombination(id: String): Result<Unit> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 로컬 데이터 삭제
                    delay(300)
                    createdCombinations.removeAll { it.id == id }
                    Result.success(Unit)
                } else {
                    // 실제 API 호출 (Swagger 스펙: DELETE /register/{postId} 사용)
                    // 현재 사용자 ID 가져오기
                    // 1. JWT 토큰에서 추출 시도 (가장 안정적)
                    // 2. /users/mypage API 호출 시도
                    // 3. DummyData에서 fallback
                    val userId = tokenManager.getUserIdFromToken()
                        ?: try {
                            val userProfile = userService.getMyPage()
                            userProfile.id?.toLongOrNull()
                        } catch (e: Exception) {
                            // /users/mypage가 실패하면 null
                            null
                        }
                        ?: DummyData.currentUser?.id?.toLongOrNull()
                        ?: throw IllegalStateException("User ID is not available. Please login again.")
                    
                    val postId = id.toLongOrNull()
                        ?: throw IllegalStateException("Post ID is not a valid number")
                    
                    val response = combinationService.deleteRegister(postId, userId)
                    
                    // Response<Unit>을 사용하면 빈 응답도 안전하게 처리됨
                    if (response.isSuccessful) {
                        Result.success(Unit)
                    } else {
                        throw IllegalStateException("Delete register failed: ${response.code()}")
                    }
                }
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
                            it.author.id == DummyData.currentUser?.id
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

        override suspend fun searchCombinations(query: String): Result<List<Combination>> {
            return try {
                delay(300)
                val allCombinations = DummyData.dummyCombinations + createdCombinations
                val searchResults =
                    allCombinations
                        .filter {
                            it.title.contains(query, ignoreCase = true) ||
                                it.description.contains(query, ignoreCase = true) ||
                                it.tags.any { tag -> tag.contains(query, ignoreCase = true) } ||
                                it.ingredients.any { ingredient -> ingredient.contains(query, ignoreCase = true) }
                        }
                        .map { combination ->
                            val likeCount =
                                combinationLikeCounts[combination.id] ?: combination.likeCount
                            combination.copy(
                                isLiked = likedCombinationIds.contains(combination.id),
                                likeCount = likeCount,
                            )
                        }
                Result.success(searchResults)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getRecipeDetail(id: Long): Result<RecipeDetail> =
            runCatching {
                DummyData.getRecipeDetailById(id)
                    ?: throw IllegalStateException("Recipe detail data is null")
            }

            /*
             * TODO: 나중에 API 연동 후 아래 코드 사용
             *
             * override suspend fun getRecipeDetail(id: Long): Result<RecipeDetail> =
             *     runCatching {
             *         val response = recipeService.getRecipeDetail(id)
             *         val data =
             *             response.data
             *                 ?: throw IllegalStateException("Recipe detail data is null")
             *
             *         data.toDomain()
             *     }
             */
    }
