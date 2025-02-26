package com.example.dogify.breedpics.model

import com.example.dogify.provider.DataFetchService
import com.example.dogify.provider.DogBreedService
import retrofit2.create

class BreedPictureRepository{

    private val dogBreedImageService = DataFetchService.getInstance().create<DogBreedService>()

    suspend fun getPicturesByBreed(breedName: String): BreedPicResponse{
        return dogBreedImageService.getPicturesByBreed(
            breedName
        )
    }
}