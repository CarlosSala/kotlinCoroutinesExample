package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.SuperheroService
import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

class Repository {

    private val api = SuperheroService()

    suspend fun getSuperhero(name: String): SuperheroDataResponseDto {
        return api.getSuperhero(name)
    }
}