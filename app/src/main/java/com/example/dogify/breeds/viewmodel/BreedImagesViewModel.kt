package com.example.dogify.breeds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.repo.BreedImageRepository
import com.example.dogify.breeds.view.BreedPic
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedImagesViewModel(val breedPic: BreedPic, db: FavoritesDatabase) : ViewModel() {

    private val repo = BreedImageRepository(db.dao)

    private val _breedPics = MutableStateFlow<List<Breed>>(emptyList())
    val breedPics = _breedPics.asStateFlow()

    private val _isAdded = MutableStateFlow<Boolean>(false)
    val isAdded = _isAdded.asStateFlow()

    fun getBreedPics(){
        viewModelScope.launch {
        val response = repo.getPicturesByBreed(breedPic.breedName, breedPic.subBreedName)
        val breedPicEntries = response.message.map {
                imageUrl -> Breed(
            breedName = breedPic.breedName,
            breedImageUrl = imageUrl,
            subBreedName = breedPic.subBreedName
            )
        }
        println("Entries for breedPics: $breedPicEntries")
            _breedPics.value = breedPicEntries
        }
    }

    fun toggleFavorite(breedPic: Breed){
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breedPic.breedImageUrl)
            if (!exists){
                repo.addToFavorites(Breed(
                    breedImageUrl = breedPic.breedImageUrl,
                    subBreedName = breedPic.subBreedName,
                    breedName = breedPic.breedName
                ))
                _isAdded.value = true
                println("Added ${breedPic.breedImageUrl} to Favorites")
            } else {
                repo.removeFromFavorites(Breed(
                    breedImageUrl = breedPic. breedImageUrl,
                    breedName = breedPic.breedName,
                    subBreedName = breedPic.subBreedName
                ))
                _isAdded.value = false
                println("Removed ${breedPic.breedImageUrl} from Favorites")
            }
        }
    }
}