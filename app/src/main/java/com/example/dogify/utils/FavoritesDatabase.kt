package com.example.dogify.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogify.favorites.model.Favorite
import kotlin.concurrent.Volatile

@Database(
    entities = [Favorite::class],
    version = 1
)
abstract class FavoritesDatabase: RoomDatabase() {
    abstract val dao: FavoritesDAO

    companion object{
        @Volatile
        private var Instance: FavoritesDatabase? = null

        fun getDatabase(context: Context): FavoritesDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, FavoritesDatabase::class.java, "favorites_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}