package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.datasource.DeviceLocalTaskDataSource
import com.example.coroutinesexample.data.network.SuperheroService
import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

class TaskRepositoryImpl : TaskRepository {

    private val api = SuperheroService()
    private val localTaskDataSource = DeviceLocalTaskDataSource()

    // remote task
    override suspend fun getSuperhero(name: String): SuperheroDataResponseDto {
        return api.getSuperhero(name)
    }

    // local task
    override fun localTask(): String {
        return localTaskDataSource.performLocalTask()
    }
}