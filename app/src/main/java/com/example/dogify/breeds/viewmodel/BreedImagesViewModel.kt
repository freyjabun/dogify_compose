package com.example.dogify.breeds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.repo.BreedPictureRepository
import com.example.dogify.breeds.view.BreedPic
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedImagesViewModel(val breedPic: BreedPic, db: FavoritesDatabase) : ViewModel() {

    private val repo = BreedPictureRepository(db.dao)

    private val _breedPics = MutableStateFlow<List<Breed>>(emptyList())
    val breedPics = _breedPics.asStateFlow()



//    val temp = flow<List<BreedPicEntry>> {
//        val response = breedPicRepo.getPicturesByBreed(breedName)
//        val breedPicEntries = response.message.map {
//                imageUrl -> BreedPicEntry(
//            breedName = breedName,
//            breedImage = imageUrl
//            )
//        }
//        println("Entries for breedPics: $breedPicEntries")
//        breedPicEntries
//    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

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