package com.example.hackathon.core.network.dto.request

import com.example.hackathon.core.network.dto.response.IngredientDto
import kotlinx.serialization.Serializable

/**
 * 레시피 등록 요청 DTO
 * POST /register?userId={userId}
 *
 * Swagger 스펙: http://13.125.27.133/swagger-ui/index.html#/register-controller
 * - userId: query parameter (required)
 * - Request body: title, categories, ingredients, images, description, isPrivate
 */
@Serializable
data class RegisterRequest(
    val title: String,
    val categories: List<String>,
    val ingredients: List<IngredientDto>,
    val images: List<String>,
    val description: String,
    // false = 전체 공개, true = 나만 보기
    val isPrivate: Boolean = false
)

/**
 * 레시피 수정 요청 DTO
 * PUT /register/{postId}?userId={userId}
 */
@Serializable
data class UpdateRegisterRequest(
    val title: String,
    val categories: List<String>,
    val ingredients: List<IngredientDto>,
    val images: List<String>,
    val description: String,
    // false = 전체 공개, true = 나만 보기
    val isPrivate: Boolean = false
)
