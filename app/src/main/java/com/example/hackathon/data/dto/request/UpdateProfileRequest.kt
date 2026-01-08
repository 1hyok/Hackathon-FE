package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val nickname: String,
    val profileImageUrl: String? = null,
)
