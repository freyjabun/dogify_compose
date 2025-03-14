package com.example.dogify.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogify.breeds.repo.BreedImageRepositoryInterface
import com.example.dogify.breeds.view.BreedPic
import com.example.dogify.breeds.viewmodel.BreedImagesViewModel

class BreedImagesViewModelFactory(
    private val breedPic: BreedPic,
    private val repo: BreedImageRepositoryInterface
) : ViewModelProvider.Factory { // Ensure you're extending ViewModelProvider.Factory

    // Correctly override the create() method here
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedImagesViewModel::class.java)) {
            return BreedImagesViewModel(breedPic, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

