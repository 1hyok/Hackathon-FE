package com.example.hackathon.data.mapper

import com.example.hackathon.data.dto.response.AuthorDto
import com.example.hackathon.data.dto.response.DetailResponse
import com.example.hackathon.data.dto.response.IngredientDto
import com.example.hackathon.data.dto.response.PostPreviewDto
import com.example.hackathon.data.dto.response.RecipeDetailResponse
import com.example.hackathon.data.dto.response.RecipeRankingItemResponse
import com.example.hackathon.data.dto.response.RecipeRankingResponse
import com.example.hackathon.data.dto.response.UserResponse
import com.example.hackathon.data.mapper.toEntity
import com.example.hackathon.domain.entity.Ingredient
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.RecipeRanking
import com.example.hackathon.domain.entity.RecipeRankingItem
import com.example.hackathon.domain.entity.Stats
import com.example.hackathon.domain.entity.UserInteraction

// ========== 기존 DTO Mapper (하위 호환성) ==========

fun RecipeDetailResponse.toDomain(): RecipeDetail =
    RecipeDetail(
        id = id,
        title = title,
        category = category,
        description = description,
        author = author.toEntity(),
        images = images,
        ingredients =
            ingredients.map {
                Ingredient(
                    name = it.name,
                    amount = it.amount,
                )
            },
        stats =
            Stats(
                likesCount = stats.likesCount,
            ),
        userInteraction =
            UserInteraction(
                isLiked = userInteraction.isLiked,
                isMine = userInteraction.isMine,
            ),
        tags = tags,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun RecipeRankingResponse.toDomain(): RecipeRanking =
    RecipeRanking(
        currentCategory = currentCategory,
        list = list.map { it.toDomain() },
    )

fun RecipeRankingItemResponse.toDomain(): RecipeRankingItem =
    RecipeRankingItem(
        title = title,
        description = description,
        viewCount = viewCount,
        likes = likes,
        username = username,
        thumbnailUrl = thumbnailUrl,
    )

// ========== 새로운 DTO Mapper (Swagger 스펙) ==========

/**
 * PostPreviewDto를 RecipeRankingItem으로 변환
 */
fun PostPreviewDto.toDomain(): RecipeRankingItem =
    RecipeRankingItem(
        title = title,
        description = description ?: "",
        viewCount = viewCount,
        likes = likesCount,
        username = author.nickname,
        thumbnailUrl = thumbnailUrl,
    )

/**
 * List<PostPreviewDto>를 RecipeRanking으로 변환
 * @param currentCategory 현재 카테고리 (기본값: "ALL")
 */
fun List<PostPreviewDto>.toDomain(currentCategory: String = "ALL"): RecipeRanking =
    RecipeRanking(
        currentCategory = currentCategory,
        list = map { it.toDomain() },
    )

/**
 * DetailResponse를 RecipeDetail로 변환
 */
fun DetailResponse.toDomain(): RecipeDetail =
    RecipeDetail(
        id = postId,
        title = title,
        category = category ?: "",
        description = description,
        author = author.toEntity(),
        images = images,
        ingredients =
            ingredients.map {
                Ingredient(
                    name = it.name,
                    amount = it.amount,
                )
            },
        stats =
            Stats(
                likesCount = likesCount,
            ),
        userInteraction =
            UserInteraction(
                isLiked = isLiked,
                isMine = isMine,
            ),
        tags = tags,
        createdAt = createdAt,
        updatedAt = updatedAt ?: createdAt,
    )

/**
 * AuthorDto를 User로 변환
 */
fun AuthorDto.toEntity(): com.example.hackathon.domain.entity.User =
    com.example.hackathon.domain.entity.User(
        id = userId,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
    )
