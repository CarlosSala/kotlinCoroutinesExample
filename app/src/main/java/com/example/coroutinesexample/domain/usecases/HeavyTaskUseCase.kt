package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepository
import javax.inject.Inject

class HeavyTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    fun heavyTask(): String {
        return taskRepository.heavyTask()
    }
}