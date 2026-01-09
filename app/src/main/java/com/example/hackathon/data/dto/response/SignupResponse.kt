package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val id: Long,
    val email: String,
)
