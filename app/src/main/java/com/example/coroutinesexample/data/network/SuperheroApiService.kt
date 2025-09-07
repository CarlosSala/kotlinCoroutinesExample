package com.example.coroutinesexample.data.network


import com.example.coroutinesexample.data.model.SuperheroDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroApiService {

    @GET("api/10229233666327556/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superheroName: String): Response<SuperheroDto>
}