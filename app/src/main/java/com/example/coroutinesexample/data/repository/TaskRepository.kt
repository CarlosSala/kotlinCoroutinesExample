package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.domain.model.Superheros

interface TaskRepository {
    suspend fun getSuperhero(name: String): Superheros
    fun localTask(): String
}