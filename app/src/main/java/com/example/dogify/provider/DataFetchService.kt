package com.example.dogify.provider
import com.example.dogify.breedlist.model.BreedsResponse
import com.example.dogify.breedpics.model.BreedPicResponse
import com.example.dogify.breedpics.view.BreedPics
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface DogBreedService{
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsResponse

    @GET("breed/{breedName}/images/all")
    suspend fun getPicturesByBreed(@Path("breedName") breedName: String): BreedPicResponse
}

object DataFetchService{
    private val BASE_URL = "https://dog.ceo/api/"
    fun getInstance():Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
