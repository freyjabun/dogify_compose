package com.example.dogify.breedpics.model

import com.example.dogify.favorites.model.Favorite
import com.example.dogify.favorites.model.FavoritesModel
import com.example.dogify.utils.DataFetchService
import com.example.dogify.utils.DogBreedService
import com.example.dogify.utils.FavoritesDAO
import retrofit2.create

class BreedPictureRepository(private val dao: FavoritesDAO) {

    private val dogBreedImageService = DataFetchService.getInstance().create<DogBreedService>()

    suspend fun getPicturesByBreed(breedName: String, subBreedName: String?): BreedPicResponse {
        return if (subBreedName.isNullOrEmpty()) {
            dogBreedImageService.getPicturesByBreed(
                breedName
            )
        } else {
            dogBreedImageService.getPicturesBySubBreed(
                breedName, subBreedName
            )
        }
    }

    suspend fun isImageInFavorites(breedImage: String): Boolean {
        return dao.isImageInFavorites(
            url = breedImage
        )
    }

    private fun mergeBreedNames(entry: FavoritesModel): String {
        return entry.breedName + "-" +
                if (entry.subBreedName.isNullOrEmpty()) {
                    ""
                } else {
                    entry.subBreedName
                }
    }

    suspend fun addToFavorites(entry: FavoritesModel) {
        dao.addBreedPicToFavorites(
            Favorite(
                breedImage = entry.breedImage,
                fullBreedName = mergeBreedNames(entry)
            )

        )
    }

    suspend fun removeFromFavorites(entry: FavoritesModel) {
        dao.removeBreedPicFromFavorites(
            Favorite(
                breedImage = entry.breedImage,
                fullBreedName = mergeBreedNames(entry)
            )
        )
    }
}