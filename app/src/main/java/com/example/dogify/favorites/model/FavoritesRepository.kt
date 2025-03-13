package com.example.dogify.favorites.model

import com.example.dogify.breeds.model.Breed
import com.example.dogify.utils.FavoritesDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class FavoritesRepository(private val dao: FavoritesDAO) :FavoritesRepositoryInterface {


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorites(): Flow<List<Breed>> {
        val favorites = dao.getFavorites().mapLatest { it ->
            it.map { it.toModel() }
        }
        return favorites
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getListOfBreedsInFavorites(): Flow<List<Breed>> {
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

    override suspend fun isImageInFavorites(breedImage: String): Boolean {
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

    override suspend fun addToFavorites(breed: Breed) {
        dao.addBreedPicToFavorites(
            Favorite(
                breedImage = breed.breedImageUrl,
                fullBreedName = mergeBreedNames(breed)
            )

        )
    }

    override suspend fun removeFromFavorites(breed: Breed) {
        dao.removeBreedPicFromFavorites(
            Favorite(
                breedImage = breed.breedImageUrl,
                fullBreedName = mergeBreedNames(breed)
            )
        )
    }
}