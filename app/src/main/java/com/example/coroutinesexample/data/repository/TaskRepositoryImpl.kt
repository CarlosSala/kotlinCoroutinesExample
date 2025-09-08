package com.example.coroutinesexample.data.repository

import com.example.coroutinesexample.data.datasource.local.HeavyTaskDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSource
import com.example.coroutinesexample.domain.model.Superheros

class TaskRepositoryImpl(
    private val heavyTaskDataSource: HeavyTaskDataSource,
    private val superheroRemoteDataSourceImpl: SuperheroRemoteDataSource
) : TaskRepository {

    // remote task
    override suspend fun getSuperhero(name: String): Superheros {
        return superheroRemoteDataSourceImpl.getSuperheroByName(name)
    }

    // local task
    override fun heavyTask(): String {
        return heavyTaskDataSource.performHeavyTask()
    }
}