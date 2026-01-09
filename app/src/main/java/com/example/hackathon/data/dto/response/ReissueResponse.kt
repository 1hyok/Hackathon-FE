package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ReissueResponse(
    val accessToken: String,
    val refreshToken: String? = null, // 선택사항 (RTR)
)
