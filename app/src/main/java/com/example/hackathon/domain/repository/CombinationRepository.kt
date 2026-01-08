package com.example.hackathon.domain.repository

import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination

interface CombinationRepository {
    suspend fun getCombinations(category: Category? = null): Result<List<Combination>>

    suspend fun getCombinationById(id: String): Result<Combination>

    suspend fun createCombination(
        title: String,
        description: String,
        category: Category,
        ingredients: List<String>,
        steps: List<String>,
        tags: List<String> = emptyList(),
        imageUri: android.net.Uri? = null,
    ): Result<Combination>

    suspend fun getMyCombinations(): Result<List<Combination>>

    suspend fun getLikedCombinations(): Result<List<Combination>>

    suspend fun likeCombination(id: String): Result<Unit>
}
