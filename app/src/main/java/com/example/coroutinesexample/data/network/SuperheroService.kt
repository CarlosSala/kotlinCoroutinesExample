package com.example.coroutinesexample.data.network

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

class SuperheroService {

    suspend fun getSuperhero(superheroName: String): SuperheroDataResponseDto {
        val response = RetrofitHelper.getInstanceRetrofit().getSuperHeroes(superheroName)
        return response.body()!!
    }
}