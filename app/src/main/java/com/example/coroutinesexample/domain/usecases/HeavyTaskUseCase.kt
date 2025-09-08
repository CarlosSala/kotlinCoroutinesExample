package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepository

class HeavyTaskUseCase(
    private val taskRepository: TaskRepository
) {

    fun heavyTask(): String {
        return taskRepository.heavyTask()
    }
}