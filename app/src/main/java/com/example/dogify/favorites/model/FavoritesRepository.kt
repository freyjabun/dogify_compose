package com.example.dogify.favorites.model

import com.example.dogify.breeds.model.Breed
import com.example.dogify.utils.FavoritesDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class FavoritesRepository(private val dao: FavoritesDAO)  {


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavorites() : Flow<List<Breed>>{
        val favorites = dao.getFavorites().mapLatest { it ->
            it.map { it.toModel() }
        }
        return favorites
    }

    suspend fun getListOfBreedsInFavorites(){
        //TODO
        dao.getAllBreedNamesInFavorites()
    }

    suspend fun getFavoritesByBreed(entry: Breed){
        //TODO
        dao.getFavoritesByBreed(
            breedName = mergeBreedNames(entry)
        )
    }
    suspend fun isImageInFavorites(breedImage: String): Boolean {
        return dao.isImageInFavorites(
            url = breedImage
        )
    }

    private fun mergeBreedNames(entry: Breed): String {
        return entry.breedName + "-" +
                if (entry.subBreedName.isNullOrEmpty()) {
                    ""
                } else {
                    entry.subBreedName
                }
    }

    suspend fun addToFavorites(entry: Breed) {
        dao.addBreedPicToFavorites(
            Favorite(
                breedImage = entry.breedImageUrl,
                fullBreedName = mergeBreedNames(entry)
            )

        )
    }

    suspend fun removeFromFavorites(entry: Breed) {
        dao.removeBreedPicFromFavorites(
            Favorite(
                breedImage = entry.breedImageUrl,
                fullBreedName = mergeBreedNames(entry)
            )
        )
    }
}