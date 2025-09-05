package com.example.coroutinesexample.data

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.network.RetrofitHelper

class SuperheroService {

    suspend fun getSuperhero(superheroName: String): SuperheroDataResponseDto {
        val response = RetrofitHelper.getInstanceRetrofit().getSuperHeroes(superheroName)
        return response.body()!!
    }
}