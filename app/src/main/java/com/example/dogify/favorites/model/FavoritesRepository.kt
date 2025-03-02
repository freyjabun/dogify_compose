package com.example.dogify.favorites.model

import com.example.dogify.utils.FavoritesDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class FavoritesRepository(private val dao: FavoritesDAO)  {


    private fun mergeBreedNames(entry: FavoritesModel): String {
        return entry.breedName + "-" +
                if (entry.subBreedName.isNullOrEmpty()) {
                    ""
                } else {
                    entry.subBreedName
                }
    }

//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val favorites: Flow<List<FavoritesModel>> = dao.getFavorites().mapLatest { it ->
//        it.map { it.toModel() }
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFavorites() : Flow<List<FavoritesModel>>{
        val favorites = dao.getFavorites().mapLatest { it ->
            it.map { it.toModel() }
        }
        return favorites
    }



    suspend fun getListOfBreedsInFavorites(){
        dao.getAllBreedNamesInFavorites()
    }

    suspend fun getFavoritesByBreed(entry: FavoritesModel){
        dao.getFavoritesByBreed(
            breedName = mergeBreedNames(entry)
        )
    }
}