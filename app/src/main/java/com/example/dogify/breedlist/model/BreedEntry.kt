package com.example.dogify.breedlist.model

import kotlinx.serialization.Serializable


data class BreedEntry(
    val breedName: String,
    val breedImageUrl: String? = null
)

@Serializable
data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)

