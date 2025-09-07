package com.example.coroutinesexample.data.datasource.remote

import com.example.coroutinesexample.data.mapper.toDomain
import com.example.coroutinesexample.data.network.RetrofitHelper
import com.example.coroutinesexample.domain.model.Superheros

class SuperheroRemoteDataSourceImpl : SuperheroRemoteDataSource {

    private val superheroApiService = RetrofitHelper.getInstanceRetrofit()

    override suspend fun getSuperheroByName(name: String): Superheros {
        val response = superheroApiService.getSuperHeroes(name)
        return response.body()!!.toDomain()
    }
}