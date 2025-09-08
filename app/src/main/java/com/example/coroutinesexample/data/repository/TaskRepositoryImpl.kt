package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.datasource.local.DeviceLocalTaskDataSource
import com.example.coroutinesexample.data.datasource.local.LocalTaskDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSourceImpl
import com.example.coroutinesexample.domain.model.Superheros

class TaskRepositoryImpl(
    private val localTaskDataSource: LocalTaskDataSource,
    private val superheroRemoteDataSourceImpl: SuperheroRemoteDataSource
) : TaskRepository {

    // remote task
    override suspend fun getSuperhero(name: String): Superheros {
        return superheroRemoteDataSourceImpl.getSuperheroByName(name)
    }

    // local task
    override fun localTask(): String {
        return localTaskDataSource.performLocalTask()
    }
}