package com.example.dogify.breedpics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.model.BreedPictureRepository
import com.example.dogify.breedpics.view.BreedPic
import com.example.dogify.favorites.model.FavoritesModel
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedPicsVM(val breedPic: BreedPic, db: FavoritesDatabase) : ViewModel() {

    private val repo = BreedPictureRepository(db.dao)

    private val _breedPics = MutableStateFlow<List<BreedPicEntry>>(emptyList())
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
                imageUrl -> BreedPicEntry(
            breedName = breedPic.breedName,
            breedImage = imageUrl,
            subBreedName = breedPic.subBreedName
            )
        }
        println("Entries for breedPics: $breedPicEntries")
            _breedPics.value = breedPicEntries
        }
    }

    fun toggleFavorite(breedPic: BreedPicEntry){
        viewModelScope.launch {
            val exists = repo.isImageInFavorites(breedPic.breedImage)
            if (!exists){
                repo.addToFavorites(FavoritesModel(
                    breedImage = breedPic.breedImage,
                    subBreedName = breedPic.subBreedName,
                    breedName = breedPic.breedName
                ))
                println("Added ${breedPic.breedImage} to Favorites")
            } else {
                repo.removeFromFavorites(FavoritesModel(
                    breedImage = breedPic.breedImage,
                    breedName = breedPic.breedName,
                    subBreedName = breedPic.subBreedName
                ))
                println("Removed ${breedPic.breedImage} from Favorites")
            }
        }
    }
}