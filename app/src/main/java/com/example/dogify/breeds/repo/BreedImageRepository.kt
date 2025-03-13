package com.example.dogify.breeds.repo

import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.model.BreedImageResponse
import com.example.dogify.favorites.model.Favorite
import com.example.dogify.utils.DataFetchService
import com.example.dogify.utils.DogBreedService
import com.example.dogify.utils.FavoritesDAO
import retrofit2.create

class BreedImageRepository(private val dao: FavoritesDAO, private val api: DogBreedService) :
    BreedImageRepositoryInterface {


    override suspend fun getPicturesByBreed(
        breedName: String,
        subBreedName: String?
    ): BreedImageResponse {
        return if (subBreedName.isNullOrEmpty()) {
            api.getPicturesByBreed(
                breedName
            )
        } else {
            api.getPicturesBySubBreed(
                breedName, subBreedName
            )
        }
    }

    override suspend fun isImageInFavorites(breedImage: String): Boolean {
        return dao.isImageInFavorites(
            url = breedImage
        )
    }

    private fun mergeBreedNames(entry: Breed): String {
        return entry.breedName + "-" +
                if (entry.subBreedName.isNullOrEmpty()) {
                    ""
                } else {
                    entry.subBreedName
                }
    }

    override suspend fun addToFavorites(entry: Breed) {
        dao.addBreedPicToFavorites(
            Favorite(
                breedImage = entry.breedImageUrl,
                fullBreedName = mergeBreedNames(entry)
            )

        )
    }

    override suspend fun removeFromFavorites(entry: Breed) {
        dao.removeBreedPicFromFavorites(
            Favorite(
                breedImage = entry.breedImageUrl,
                fullBreedName = mergeBreedNames(entry)
            )
        )
    }
}