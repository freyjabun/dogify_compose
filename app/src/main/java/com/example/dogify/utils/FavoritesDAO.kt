package com.example.dogify.utils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dogify.favorites.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {

    @Query("SELECT EXISTS(SELECT breedImage FROM favorite WHERE breedImage = :url)")
    suspend fun isImageInFavorites(url:String): Boolean

    @Insert suspend fun addBreedPicToFavorites(item: Favorite)

    @Delete suspend fun removeBreedPicFromFavorites(item: Favorite)

    @Query("SELECT * FROM favorite ORDER BY fullBreedName")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE fullBreedName = :breedName")
    fun getFavoritesByBreedName(breedName: String): Flow<List<Favorite>>

    @Query("SELECT DISTINCT fullBreedName from favorite ORDER BY fullBreedName ASC")
    fun getAllBreedNamesInFavorites(): Flow<List<String>>
}
