package com.example.dogify.breedpics.model

import coil3.Image
import kotlinx.serialization.Serializable

@Serializable
data class BreedPicResponse(
    val message: List<String>,
    val status: String
)

data class BreedPicEntry(
    var liked: Boolean? = false,
    val breedName: String,
    val breedImage: String
)