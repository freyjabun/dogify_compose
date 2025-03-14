package com.example.dogify.breeds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.repo.BreedImageRepositoryInterface
import com.example.dogify.breeds.view.BreedPic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedImagesViewModel(
    private val breedPic: BreedPic,
    private val repo: BreedImageRepositoryInterface
) : ViewModel() {

    private val _breedPics = MutableStateFlow<List<Breed>>(emptyList())
    val breedPics = _breedPics.asStateFlow()

    private val _isAdded = MutableStateFlow(false)
    val isAdded = _isAdded.asStateFlow()

    fun getBreedPics() {
        viewModelScope.launch {
            val response = repo.getPicturesByBreed(breedPic.breedName, breedPic.subBreedName)
            println("Getting pictures of: ${breedPic.breedName}")
            val breedPicEntries = response.message.map { imageUrl ->
                Breed(
                    breedName = breedPic.breedName,
                    breedImageUrl = imageUrl,
                    subBreedName = breedPic.subBreedName
                )
            }
            println("Entries for breedPics: $breedPicEntries")
            _breedPics.value = breedPicEntries
        }
    }

    fun toggleFavorite(breedPic: Breed) {
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breedPic.breedImageUrl)
            if (!exists) {
                repo.addToFavorites(
                    Breed(
                        breedImageUrl = breedPic.breedImageUrl,
                        subBreedName = breedPic.subBreedName,
                        breedName = breedPic.breedName
                    )
                )
                _isAdded.value = true
                println("Added ${breedPic.breedImageUrl} to Favorites")
            } else {
                repo.removeFromFavorites(
                    Breed(
                        breedImageUrl = breedPic.breedImageUrl,
                        breedName = breedPic.breedName,
                        subBreedName = breedPic.subBreedName
                    )
                )
                _isAdded.value = false
                println("Removed ${breedPic.breedImageUrl} from Favorites")
            }
        }
    }

}