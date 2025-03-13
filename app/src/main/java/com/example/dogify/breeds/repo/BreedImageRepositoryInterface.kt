package com.example.dogify.breeds.repo

import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.model.BreedImageResponse

interface BreedImageRepositoryInterface {
    suspend fun getPicturesByBreed(breedName: String, subBreedName: String?): BreedImageResponse
    suspend fun isImageInFavorites(breedImage: String): Boolean
    suspend fun addToFavorites(entry: Breed)
    suspend fun removeFromFavorites(entry: Breed)
}