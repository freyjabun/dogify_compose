package com.example.dogify.breedlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.breedlist.model.BreedEntry
import com.example.dogify.breedlist.model.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedListVM : ViewModel() {
    private val breedRepo = BreedRepository()

    private val _breedList = MutableStateFlow<List<BreedEntry>>(emptyList())
    val breedList = _breedList.asStateFlow()

    fun loadAllBreeds() {
        viewModelScope.launch {
            try {
                val response = breedRepo.getAllBreeds()

                val allBreeds = response.message.flatMap { entry ->
                    if (entry.value.isEmpty()) listOf(
                        BreedEntry(
                            breedName = entry.key
                        )
                    )
                    else entry.value.map {
                        BreedEntry(
                            breedName = entry.key,
                            subBreedName = it
                        )
                    }
                }
                _breedList.value = allBreeds
            } catch (e: Exception) {
                e.printStackTrace()
                _breedList.value = emptyList()
            }

        }
    }
}