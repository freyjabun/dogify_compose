package com.example.dogify.breeds.repo

import com.example.dogify.breeds.model.BreedsResponse
import com.example.dogify.breeds.model.RandomImageResponse

interface BreedRepositoryInterface {
    suspend fun getAllBreeds(): BreedsResponse
    suspend fun getImageOfBreed(breedName: String, subBreedName: String?): RandomImageResponse
}