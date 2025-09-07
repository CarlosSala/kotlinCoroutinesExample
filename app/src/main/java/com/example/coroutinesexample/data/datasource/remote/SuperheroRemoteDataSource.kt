package com.example.coroutinesexample.data.datasource.remote

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

interface SuperheroRemoteDataSource {

    suspend fun getSuperheroByName(name: String): SuperheroDataResponseDto
}