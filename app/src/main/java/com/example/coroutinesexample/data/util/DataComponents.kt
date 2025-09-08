package com.example.coroutinesexample.data.util

import com.example.coroutinesexample.data.datasource.local.DeviceLocalTaskDataSource
import com.example.coroutinesexample.data.datasource.local.LocalTaskDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSourceImpl
import com.example.coroutinesexample.data.network.RetrofitHelper
import com.example.coroutinesexample.data.network.SuperheroApiService
import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl

object DataComponents {

    private val superheroApiService: SuperheroApiService by lazy {
        RetrofitHelper.getInstanceRetrofit()
    }

    val superheroRemoteDataSource: SuperheroRemoteDataSource by lazy {
        SuperheroRemoteDataSourceImpl(superheroApiService)
    }

    val localTaskDataSource: LocalTaskDataSource by lazy {
        DeviceLocalTaskDataSource()
    }

    val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(
            localTaskDataSource = localTaskDataSource,
            superheroRemoteDataSourceImpl = superheroRemoteDataSource
        )
    }
}