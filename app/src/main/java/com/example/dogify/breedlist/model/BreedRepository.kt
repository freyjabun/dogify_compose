// BreedRepository.kt
package com.example.dogify.breedlist.model

import com.example.dogify.provider.DataFetchService
import com.example.dogify.provider.DogBreedService
import retrofit2.create

class BreedRepository {

    private val dogBreedService = DataFetchService.getInstance().create<DogBreedService>()

    suspend fun getAllBreeds(): BreedsResponse {
        return dogBreedService.getAllBreeds()
    }
}
