package com.example.dogify.breeds.model

data class Breed(
    val breedName: String,
    val subBreedName: String? = null,
    var breedImageUrl: String = ""
)
