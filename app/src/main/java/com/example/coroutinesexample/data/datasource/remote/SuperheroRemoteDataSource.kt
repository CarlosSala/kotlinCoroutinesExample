package com.example.coroutinesexample.data.datasource.remote

import com.example.coroutinesexample.domain.model.Superheros

interface SuperheroRemoteDataSource {

    suspend fun getSuperheroByName(name: String): Superheros
}