package com.example.coroutinesexample.data

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.model.SuperheroItemResponse
import com.example.coroutinesexample.data.network.SuperHeroApiClient
import retrofit2.Retrofit

class SuperheroService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSuperhero(superheroName: String): SuperheroDataResponseDto {
        val response = retrofit.create(SuperHeroApiClient::class.java).getSuperHeroes(superheroName)
        return response.body()!!
    }
}