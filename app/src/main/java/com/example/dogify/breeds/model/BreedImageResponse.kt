package com.example.dogify.breeds.model

import kotlinx.serialization.Serializable

@Serializable
data class BreedImageResponse(
    val message: List<String>,
    val status: String
)
