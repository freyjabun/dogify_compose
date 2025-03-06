package com.example.dogify.favorites.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dogify.breeds.model.Breed

@Entity
data class Favorite(
    @PrimaryKey val breedImage: String,
    val fullBreedName: String
)

fun Favorite.toModel(): Breed {
    val parts = fullBreedName.split("-", limit = 2)
    val breedName = parts[0]
    val subBreedName = parts[1]

    return Breed(
        breedName = breedName,
        subBreedName = subBreedName,
        breedImageUrl = breedImage
    )
}
