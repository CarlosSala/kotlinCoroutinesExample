package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl
import com.example.coroutinesexample.domain.model.Superheros

class RemoteTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(superhero: String): Superheros {
        return taskRepository.getSuperhero(superhero)
    }
}