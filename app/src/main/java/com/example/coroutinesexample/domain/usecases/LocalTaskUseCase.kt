package com.example.coroutinesexample.domain.usecases

import com.example.coroutinesexample.data.repository.TaskRepositoryImpl

class LocalTaskUseCase {

    private val taskRepositoryImpl = TaskRepositoryImpl()

    fun localTask(): String {
        return taskRepositoryImpl.localTask()
    }
}