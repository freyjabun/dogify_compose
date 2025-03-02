package com.example.dogify.breeds.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomImageResponse(
    val message: String,
    val status: String
)
