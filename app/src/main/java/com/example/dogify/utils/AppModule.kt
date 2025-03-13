package com.example.dogify.utils

import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.example.dogify.breeds.repo.BreedImageRepository
import com.example.dogify.breeds.repo.BreedImageRepositoryInterface
import com.example.dogify.breeds.repo.BreedRepository
import com.example.dogify.breeds.repo.BreedRepositoryInterface
import com.example.dogify.breeds.view.BreedPic
import com.example.dogify.breeds.viewmodel.BreedImagesViewModel
import com.example.dogify.breeds.viewmodel.BreedListViewModel
import com.example.dogify.favorites.model.FavoritesRepository
import com.example.dogify.favorites.model.FavoritesRepositoryInterface
import com.example.dogify.favorites.viewmodel.FavoritesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder().baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogBreedService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            FavoritesDatabase::class.java,
            "favorites_database"
        ).build()
    }

    single<FavoritesDAO> {
        get<FavoritesDatabase>().dao
    }

    //BreedList
    single<BreedRepositoryInterface> {
        BreedRepository(get())
    }

    viewModel {
        BreedListViewModel(get())
    }

    //BreedImages
    single<BreedImageRepositoryInterface> {
        BreedImageRepository(
            dao = get(),
            api = get()
        )
    }

    viewModel { breedPic ->
        BreedImagesViewModel(
            breedPic = breedPic.get(),
            repo = get()
        )
    }

    //Favorites
    single<FavoritesRepositoryInterface> {
        FavoritesRepository(get())
    }

    viewModel{
        FavoritesViewModel(get())
    }
}