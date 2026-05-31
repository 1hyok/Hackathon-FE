package com.example.hackathon.core.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: String, val nickname: String, val profileImageUrl: String? = null)
