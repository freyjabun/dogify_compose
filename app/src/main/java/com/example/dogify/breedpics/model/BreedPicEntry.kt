package com.example.dogify.breedpics.model

import kotlinx.serialization.Serializable

@Serializable
data class BreedPicResponse(
    val message: List<String>,
    val status: String
)

data class BreedPicEntry(
    val breedName: String,
    val subBreedName: String?,
    val breedImage: String
)