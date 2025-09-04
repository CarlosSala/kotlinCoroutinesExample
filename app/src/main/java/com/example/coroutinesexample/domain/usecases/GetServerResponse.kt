package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.repository.Repository

class GetServerResponse() {

    private val repository = Repository()

    suspend operator fun invoke(superhero: String): SuperheroDataResponseDto {
        return repository.getSuperhero(superhero)
    }
}