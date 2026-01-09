package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCombinationRequest(
    val title: String,
    val description: String,
    val category: String,
    val ingredients: List<String>,
)
