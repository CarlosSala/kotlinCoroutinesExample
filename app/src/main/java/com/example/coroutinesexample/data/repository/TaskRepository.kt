package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

interface TaskRepository {
    suspend fun getSuperhero(name: String): SuperheroDataResponseDto
    fun localTask(): String
}