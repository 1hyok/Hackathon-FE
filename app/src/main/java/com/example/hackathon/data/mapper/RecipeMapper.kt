package com.example.hackathon.data.mapper

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
