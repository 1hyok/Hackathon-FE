package com.example.hackathon.core.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val refreshToken: String,
)
