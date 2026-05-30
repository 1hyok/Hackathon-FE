package com.example.hackathon.core.domain.repository

import com.example.hackathon.core.model.User

interface UserRepository {
    suspend fun getProfile(): Result<User>

    suspend fun updateProfile(
        nickname: String,
        profileImageUrl: String? = null,
    ): Result<User>
}
