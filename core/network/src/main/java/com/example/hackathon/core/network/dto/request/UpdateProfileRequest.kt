package com.example.hackathon.core.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val nickname: String,
    val profileImageUrl: String? = null,
)
