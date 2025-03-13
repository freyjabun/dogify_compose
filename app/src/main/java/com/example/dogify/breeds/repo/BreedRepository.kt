package com.example.dogify.breeds.repo

import com.example.dogify.breeds.model.BreedsResponse
import com.example.dogify.breeds.model.RandomImageResponse
import com.example.dogify.utils.DataFetchService
import com.example.dogify.utils.DogBreedService
import retrofit2.create

class BreedRepository (private val api : DogBreedService) : BreedRepositoryInterface{


    override suspend fun getAllBreeds(): BreedsResponse {
        return api.getAllBreeds()
    }

    override suspend fun getImageOfBreed(breedName: String, subBreedName: String?): RandomImageResponse {
        return if (subBreedName.isNullOrEmpty()) {
            api.getRandomPictureOfBreed(breedName)
        } else {
            api.getRandomPictureOfBreedWithSubBreed(breedName, subBreedName)
        }
    }
}
