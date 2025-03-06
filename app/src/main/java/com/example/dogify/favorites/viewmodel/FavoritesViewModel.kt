package com.example.dogify.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.favorites.model.FavoritesRepository
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(db: FavoritesDatabase) : ViewModel() {

    private val repo = FavoritesRepository(db.dao)

    private val _selectedBreed = MutableStateFlow<Breed?>(null)
    private val selectedBreed = _selectedBreed.asStateFlow()


    val allBreedsFlow: StateFlow<List<Breed>> = repo.getListOfBreedsInFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private val favoriteFlow: StateFlow<List<Breed>> = repo.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val favoritesByBreedFlow: StateFlow<List<Breed>> = selectedBreed.flatMapLatest {
        selectedBreed -> repo.getFavoritesByBreed(selectedBreed)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val showFavoritesFlow: StateFlow<List<Breed>> =
        selectedBreed.flatMapLatest {
            if (selectedBreed.value != null) {
                favoritesByBreedFlow
            } else {
                favoriteFlow
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavorite(breed: Breed) {
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breed.breedImageUrl)
            if (!exists) {
                repo.addToFavorites(
                    Breed(
                        breedImageUrl = breed.breedImageUrl,
                        subBreedName = breed.subBreedName,
                        breedName = breed.breedName
                    )
                )
                println("Added ${breed.breedImageUrl} to Favorites")
            } else {
                repo.removeFromFavorites(
                    Breed(
                        breedImageUrl = breed.breedImageUrl,
                        breedName = breed.breedName,
                        subBreedName = breed.subBreedName
                    )
                )
                println("Removed ${breed.breedImageUrl} from Favorites")
            }
        }
    }

    fun selectBreed(breed: Breed) {
        _selectedBreed.value = breed
    }

    fun clearBreedFilter() {
        _selectedBreed.value = null
    }

}
