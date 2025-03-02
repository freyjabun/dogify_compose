package com.example.dogify.favorites.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FavoritesModel(
    val breedName: String,
    val subBreedName: String? = null,
    val breedImage: String
)

@Entity
data class Favorite(
    @PrimaryKey val breedImage: String,
    val fullBreedName: String
)


fun Favorite.toModel(): FavoritesModel{
    val parts = fullBreedName.split("-", limit = 2)
    val breedName = parts[0]
    val subBreedName = parts[1]

    return FavoritesModel(
        breedName = breedName,
        subBreedName = subBreedName,
        breedImage = breedImage
    )
}