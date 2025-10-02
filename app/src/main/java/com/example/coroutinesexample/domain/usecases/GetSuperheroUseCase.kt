package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.domain.model.Superheros
import javax.inject.Inject

class GetSuperheroUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(superhero: String): Superheros {
        return taskRepository.getSuperhero(superhero)
    }
}