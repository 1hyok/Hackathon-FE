package com.example.hackathon.domain.repository

import com.example.hackathon.domain.entity.User

interface UserRepository {
    suspend fun getProfile(): Result<User>

    suspend fun updateProfile(
        nickname: String,
        profileImageUrl: String? = null,
    ): Result<User>
}
