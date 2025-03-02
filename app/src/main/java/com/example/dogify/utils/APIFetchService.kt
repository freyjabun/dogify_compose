package com.example.dogify.utils
import com.example.dogify.breedlist.model.BreedListPictureResponse
import com.example.dogify.breedlist.model.BreedsResponse
import com.example.dogify.breedpics.model.BreedPicResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface DogBreedService{
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsResponse

    @GET("breed/{breedName}/images")
    suspend fun getPicturesByBreed(
        @Path("breedName") breedName: String
    ): BreedPicResponse

    @GET("breed/{breedName}/{subBreedName}/images")
    suspend fun getPicturesBySubBreed(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String?
    ): BreedPicResponse

    @GET("breed/{breedName}/images/random")
    suspend fun getRandomPictureOfBreed(
        @Path("breedName") breedName: String,
    ): BreedListPictureResponse

    @GET("breed/{breedName}/{subBreedName}/images/random")
    suspend fun getRandomPictureOfBreedWithSubBreed(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String?
    ): BreedListPictureResponse
}

object DataFetchService{
    private val BASE_URL = "https://dog.ceo/api/"
    fun getInstance():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
