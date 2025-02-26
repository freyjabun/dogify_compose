package com.example.dogify.breedpics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.model.BreedPicResponse
import com.example.dogify.breedpics.model.BreedPictureRepository
import com.example.dogify.provider.DogBreedService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedPicsVM : ViewModel() {
    private val breedPicRepo = BreedPictureRepository()

    private val _breedPics = MutableStateFlow<List<BreedPicEntry>>(emptyList())
    val breedPics = _breedPics.asStateFlow()

    fun getBreedPics(breedName: String){
        viewModelScope.launch {
            val response = breedPicRepo.getPicturesByBreed(breedName)
            val breedPicEntries = response.message.map {
                imageUrl -> BreedPicEntry(
                    breedName = breedName,
                    breedImage = imageUrl
                )
            }
            println(breedPicEntries)
            _breedPics.value = breedPicEntries
        }
    }
}