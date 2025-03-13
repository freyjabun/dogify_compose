package com.example.dogify.favorites.model

import com.example.dogify.breeds.model.Breed
import kotlinx.coroutines.flow.Flow

interface FavoritesRepositoryInterface {
    fun getFavorites(): Flow<List<Breed>>
    fun getListOfBreedsInFavorites(): Flow<List<Breed>>
    suspend fun isImageInFavorites(breedImage: String): Boolean
    suspend fun addToFavorites(breed: Breed)
    suspend fun removeFromFavorites(breed: Breed)
}