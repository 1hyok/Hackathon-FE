package com.example.hackathon.data.mapper

import com.example.hackathon.data.dto.request.CreateCombinationRequest
import com.example.hackathon.data.dto.response.CombinationResponse
import com.example.hackathon.data.dto.response.UserResponse
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User

fun CombinationResponse.toEntity(): Combination {
    return Combination(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        category = Category.valueOf(category.uppercase()),
        ingredients = ingredients,
        steps = steps,
        author = author.toEntity(),
        likeCount = likeCount,
        createdAt = createdAt,
    )
}

fun UserResponse.toEntity(): User {
    return User(
        id = id,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
    )
}

fun CreateCombinationRequest.toCategoryString(): String {
    return category.uppercase()
}
