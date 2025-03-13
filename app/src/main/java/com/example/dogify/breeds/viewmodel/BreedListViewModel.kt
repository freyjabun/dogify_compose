package com.example.dogify.breeds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.repo.BreedRepositoryInterface
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedListViewModel(private val breedRepo: BreedRepositoryInterface) : ViewModel() {

    private val _breedList = MutableStateFlow<List<Breed>>(emptyList())
    val breedList = _breedList.asStateFlow()

    fun loadAllBreeds() {
        viewModelScope.launch {
            try {
                val response = breedRepo.getAllBreeds()

                val allBreeds = response.message.flatMap { entry ->
                    if (entry.value.isEmpty()) listOf(
                        Breed(
                            breedName = entry.key
                        )
                    )
                    else entry.value.map {
                        Breed(
                            breedName = entry.key,
                            subBreedName = it
                        )
                    }
                }
                addImagesAsync(allBreeds)

            } catch (e: Exception) {
                e.printStackTrace()
                _breedList.value = emptyList()
            }
        }
    }

    private fun addImagesAsync(breeds: List<Breed>) {
        viewModelScope.launch {
            breeds.forEach { breedEntry ->
                val imageUrl = async {
                    breedRepo.getImageOfBreed(breedEntry.breedName, breedEntry.subBreedName).message
                }
                val updatedBreed = breedEntry.copy(breedImageUrl = imageUrl.await())

                _breedList.value = _breedList.value.toMutableList().apply {
                    add(updatedBreed)
                }
            }
        }
    }

}
