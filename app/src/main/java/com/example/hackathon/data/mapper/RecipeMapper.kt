package com.example.hackathon.data.mapper

import com.example.hackathon.data.dto.response.RecipeDetailResponse
import com.example.hackathon.domain.entity.Ingredient
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.Stats
import com.example.hackathon.domain.entity.UserInteraction

fun RecipeDetailResponse.toDomain(): RecipeDetail =
    RecipeDetail(
        id = id,
        title = title,
        category = category,
        description = description,
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
