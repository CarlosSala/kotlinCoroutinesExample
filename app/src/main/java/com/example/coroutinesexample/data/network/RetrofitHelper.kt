package com.example.coroutinesexample.data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL: String = "https://superheroapi.com/"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstanceRetrofit(): SuperHeroApiClient {
        return retrofit.create(SuperHeroApiClient::class.java)
    }
}