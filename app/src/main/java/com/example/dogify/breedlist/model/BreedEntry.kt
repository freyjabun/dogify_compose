package com.example.dogify.breedlist.model

import kotlinx.serialization.Serializable


data class BreedEntry(
    val breedName: String,
    val subBreedName: String? = null,
    var breedImageUrl: String? = null
)

@Serializable
data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)

@Serializable
data class BreedListPictureResponse(
    val message: String,
    val status: String
)

