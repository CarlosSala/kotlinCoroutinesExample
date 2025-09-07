package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepositoryImpl
import com.example.coroutinesexample.domain.model.Superheros

class RemoteTaskUseCase() {

    private val taskRepositoryImpl = TaskRepositoryImpl()

    suspend operator fun invoke(superhero: String): Superheros {
        return taskRepositoryImpl.getSuperhero(superhero)
    }
}