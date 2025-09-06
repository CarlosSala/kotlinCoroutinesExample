package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl

class RemoteTaskUseCase() {

    private val taskRepositoryImpl = TaskRepositoryImpl()

    suspend operator fun invoke(superhero: String): SuperheroDataResponseDto {
        return taskRepositoryImpl.getSuperhero(superhero)
    }
}