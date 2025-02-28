package com.example.dogify.breedpics.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.model.BreedPictureRepository
import com.example.dogify.breedpics.view.BreedPic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BreedPicsVM(val breedPic: BreedPic) : ViewModel() {

    private val breedPicRepo = BreedPictureRepository()

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
        val response = breedPicRepo.getPicturesByBreed(breedPic.breedName, breedPic.subBreedName)
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
}