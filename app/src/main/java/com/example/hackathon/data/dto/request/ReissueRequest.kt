package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ReissueRequest(
    val refreshToken: String,
)
