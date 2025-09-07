package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.datasource.local.DeviceLocalTaskDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSourceImpl
import com.example.coroutinesexample.data.model.SuperheroDataResponseDto

class TaskRepositoryImpl : TaskRepository {

    private val superheroRemoteDataSourceImpl = SuperheroRemoteDataSourceImpl()
    private val localTaskDataSource = DeviceLocalTaskDataSource()

    // remote task
    override suspend fun getSuperhero(name: String): SuperheroDataResponseDto {
        return superheroRemoteDataSourceImpl.getSuperheroByName(name)
    }

    // local task
    override fun localTask(): String {
        return localTaskDataSource.performLocalTask()
    }
}