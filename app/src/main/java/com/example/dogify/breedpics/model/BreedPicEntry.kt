package com.example.dogify.breedpics.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import coil3.Image
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