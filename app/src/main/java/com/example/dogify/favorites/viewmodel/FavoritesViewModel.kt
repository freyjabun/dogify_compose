package com.example.dogify.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.favorites.model.FavoritesRepository
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(db: FavoritesDatabase) : ViewModel() {

    private val repo = FavoritesRepository(db.dao)

    val favFlow: StateFlow<List<Breed>> = repo.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun toggleFavorite(breedPic: Breed){
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breedPic.breedImageUrl)
            if (!exists){
                repo.addToFavorites(Breed(
                    breedImageUrl = breedPic.breedImageUrl,
                    subBreedName = breedPic.subBreedName,
                    breedName = breedPic.breedName
                ))
                println("Added ${breedPic.breedImageUrl} to Favorites")
            } else {
                repo.removeFromFavorites(Breed(
                    breedImageUrl = breedPic. breedImageUrl,
                    breedName = breedPic.breedName,
                    subBreedName = breedPic.subBreedName
                ))
                println("Removed ${breedPic.breedImageUrl} from Favorites")
            }
        }
    }

}
