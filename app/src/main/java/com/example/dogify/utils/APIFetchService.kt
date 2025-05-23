package com.example.dogify.utils
import com.example.dogify.breeds.model.BreedImageResponse
import com.example.dogify.breeds.model.BreedsResponse
import com.example.dogify.breeds.model.RandomImageResponse
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
    ): BreedImageResponse

    @GET("breed/{breedName}/{subBreedName}/images")
    suspend fun getPicturesBySubBreed(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String?
    ): BreedImageResponse

    @GET("breed/{breedName}/images/random")
    suspend fun getRandomPictureOfBreed(
        @Path("breedName") breedName: String,
    ): RandomImageResponse

    @GET("breed/{breedName}/{subBreedName}/images/random")
    suspend fun getRandomPictureOfBreedWithSubBreed(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String?
    ): RandomImageResponse
}

object DataFetchService{
    private val BASE_URL = "https://dog.ceo/api/"
    fun getInstance():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
