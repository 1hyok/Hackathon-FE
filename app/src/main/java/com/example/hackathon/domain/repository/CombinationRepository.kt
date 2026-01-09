package com.example.hackathon.domain.repository

import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.RecipeDetail

interface CombinationRepository {
    suspend fun getCombinations(category: Category? = null, page: Int = 1, pageSize: Int = 10): Result<List<Combination>>

    suspend fun getCombinationById(id: String): Result<Combination>

    suspend fun createCombination(
        title: String,
        description: String,
        category: Category,
        ingredients: List<String>,
        tags: List<String> = emptyList(),
        imageUri: android.net.Uri? = null,
    ): Result<Combination>

    suspend fun updateCombination(
        id: String,
        title: String,
        description: String,
        category: Category,
        ingredients: List<String>,
        tags: List<String> = emptyList(),
        imageUri: android.net.Uri? = null,
    ): Result<Combination>

    suspend fun deleteCombination(id: String): Result<Unit>

    suspend fun getMyCombinations(): Result<List<Combination>>

    suspend fun getLikedCombinations(): Result<List<Combination>>

    suspend fun likeCombination(id: String): Result<Unit>

    suspend fun searchCombinations(query: String): Result<List<Combination>>

    suspend fun getRecipeDetail(id: Long): Result<RecipeDetail>
}
