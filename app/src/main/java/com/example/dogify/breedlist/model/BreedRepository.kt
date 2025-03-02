// BreedRepository.kt
package com.example.dogify.breedlist.model

import com.example.dogify.utils.DataFetchService
import com.example.dogify.utils.DogBreedService
import retrofit2.create

class BreedRepository {

    private val dogBreedService = DataFetchService.getInstance().create<DogBreedService>()

    suspend fun getAllBreeds(): BreedsResponse {
        return dogBreedService.getAllBreeds()
    }

    suspend fun getImageOfBreed(breedName: String, subBreedName: String?): BreedListPictureResponse {
        return if (subBreedName.isNullOrEmpty()) {
            dogBreedService.getRandomPictureOfBreed(breedName)
        } else {
            dogBreedService.getRandomPictureOfBreedWithSubBreed(breedName, subBreedName)
        }
    }
}
