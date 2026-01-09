package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val email: String,
    val password: String,
    val nickname: String,
)
