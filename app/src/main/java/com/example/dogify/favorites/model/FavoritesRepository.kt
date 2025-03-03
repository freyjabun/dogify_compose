package com.example.dogify.favorites.model

import com.example.dogify.breeds.model.Breed
import com.example.dogify.utils.FavoritesDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class FavoritesRepository(private val dao: FavoritesDAO) {


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavorites(): Flow<List<Breed>> {
        val favorites = dao.getFavorites().mapLatest { it ->
            it.map { it.toModel() }
        }
        return favorites
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getListOfBreedsInFavorites(): Flow<List<Breed>> {
        val breeds = dao.getAllBreedNamesInFavorites().mapLatest { it ->
            it.map {
                val parts = it.split("-")
                val breedName = parts[0]
                val subBreedName = parts[1]
                Breed(breedName = breedName, subBreedName = subBreedName)
            }
        }
        return breeds
    }

    suspend fun isImageInFavorites(breedImage: String): Boolean {
        return dao.isImageInFavorites(
            url = breedImage
        )
    }

    private fun mergeBreedNames(breed: Breed): String {
        return breed.breedName + "-" +
                if (breed.subBreedName.isNullOrEmpty()) {
                    ""
                } else {
                    breed.subBreedName
                }
    }

    suspend fun addToFavorites(breed: Breed) {
        dao.addBreedPicToFavorites(
            Favorite(
                breedImage = breed.breedImageUrl,
                fullBreedName = mergeBreedNames(breed)
            )

        )
    }

    suspend fun removeFromFavorites(breed: Breed) {
        dao.removeBreedPicFromFavorites(
            Favorite(
                breedImage = breed.breedImageUrl,
                fullBreedName = mergeBreedNames(breed)
            )
        )
    }
}