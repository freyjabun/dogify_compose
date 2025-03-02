package com.example.dogify.breeds.repo

import com.example.dogify.breeds.model.BreedsResponse
import com.example.dogify.breeds.model.RandomImageResponse
import com.example.dogify.utils.DataFetchService
import com.example.dogify.utils.DogBreedService
import retrofit2.create

class BreedRepository {

    private val dogBreedService = DataFetchService.getInstance().create<DogBreedService>()

    suspend fun getAllBreeds(): BreedsResponse {
        return dogBreedService.getAllBreeds()
    }

    suspend fun getImageOfBreed(breedName: String, subBreedName: String?): RandomImageResponse {
        return if (subBreedName.isNullOrEmpty()) {
            dogBreedService.getRandomPictureOfBreed(breedName)
        } else {
            dogBreedService.getRandomPictureOfBreedWithSubBreed(breedName, subBreedName)
        }
    }
}
