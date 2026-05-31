package com.example.hackathon.core.data.mapper

import com.example.hackathon.core.model.Category
import com.example.hackathon.core.model.Combination
import com.example.hackathon.core.model.User
import com.example.hackathon.core.network.dto.request.CreateCombinationRequest
import com.example.hackathon.core.network.dto.response.CombinationResponse
import com.example.hackathon.core.network.dto.response.UserResponse

fun CombinationResponse.toEntity(): Combination = Combination(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
    category = Category.valueOf(category.uppercase()),
    ingredients = ingredients,
    author = author.toEntity(),
    likeCount = likeCount,
    createdAt = createdAt
)

fun UserResponse.toEntity(): User = User(
    id = id,
    nickname = nickname,
    profileImageUrl = profileImageUrl
)

fun CreateCombinationRequest.toCategoryString(): String = category.uppercase()
