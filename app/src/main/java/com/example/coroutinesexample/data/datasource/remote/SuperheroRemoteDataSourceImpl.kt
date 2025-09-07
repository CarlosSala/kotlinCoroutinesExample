package com.example.coroutinesexample.data.datasource.remote

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.network.RetrofitHelper

class SuperheroRemoteDataSourceImpl : SuperheroRemoteDataSource {

    private val superheroApiService = RetrofitHelper.getInstanceRetrofit()

    override suspend fun getSuperheroByName(name: String): SuperheroDataResponseDto {
        val response = superheroApiService.getSuperHeroes(name)
        return response.body()!!
    }
}