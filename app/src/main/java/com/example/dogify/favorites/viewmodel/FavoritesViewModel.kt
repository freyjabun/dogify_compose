package com.example.dogify.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.favorites.model.FavoritesRepository
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(db: FavoritesDatabase) : ViewModel() {

    private val repo = FavoritesRepository(db.dao)

    private val _selectedBreed = MutableStateFlow<Breed?>(null)
    private val selectedBreed: StateFlow<Breed?> = _selectedBreed

    val favoriteFlow: StateFlow<List<Breed>> = repo.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val allBreedsFlow: StateFlow<List<Breed>> = repo.getListOfBreedsInFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val filteredFavoritesFlow: StateFlow<List<Breed>> = combine(favoriteFlow, selectedBreed) { favorites, selectedBreed ->
        if (selectedBreed != null) {
            favorites.filter { breed -> breed.breedName == selectedBreed.breedName }
        } else {
            favorites
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

//    val favoriteByBreedFlow: StateFlow<List<Breed>> = repo.getFavoritesByBreed(
//
//    ).stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = emptyList()
//    )

    fun toggleFavorite(breed: Breed){
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breed.breedImageUrl)
            if (!exists){
                repo.addToFavorites(Breed(
                    breedImageUrl = breed.breedImageUrl,
                    subBreedName = breed.subBreedName,
                    breedName = breed.breedName
                ))
                println("Added ${breed.breedImageUrl} to Favorites")
            } else {
                repo.removeFromFavorites(Breed(
                    breedImageUrl = breed. breedImageUrl,
                    breedName = breed.breedName,
                    subBreedName = breed.subBreedName
                ))
                println("Removed ${breed.breedImageUrl} from Favorites")
            }
        }
    }

    fun selectBreed(breed: Breed) {
        _selectedBreed.value = breed
    }

    fun clearBreedFilter() {
        _selectedBreed.value = null // Clear the breed filter, show full list
    }

}
