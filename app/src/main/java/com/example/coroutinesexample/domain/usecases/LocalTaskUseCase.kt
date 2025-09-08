package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl

class LocalTaskUseCase(
    private val taskRepository: TaskRepository
) {

    // private val taskRepositoryImpl = TaskRepositoryImpl()

    fun localTask(): String {
        return taskRepository.localTask()
    }
}