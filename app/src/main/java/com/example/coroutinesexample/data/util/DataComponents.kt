package com.example.coroutinesexample.data.util

import com.example.coroutinesexample.data.datasource.local.HeavyTaskDataSourceImpl
import com.example.coroutinesexample.data.datasource.local.HeavyTaskDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSourceImpl
import com.example.coroutinesexample.data.network.RetrofitHelper
import com.example.coroutinesexample.data.network.SuperheroApiService
import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl

// this object is a singleton that will be used to access the data layer
object DataComponents {

    private val superheroApiService: SuperheroApiService by lazy {
        RetrofitHelper.getInstanceRetrofit()
    }

    val superheroRemoteDataSource: SuperheroRemoteDataSource by lazy {
        SuperheroRemoteDataSourceImpl(superheroApiService)
    }

    val heavyTaskDataSource: HeavyTaskDataSource by lazy {
        HeavyTaskDataSourceImpl()
    }

    val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(
            heavyTaskDataSource = heavyTaskDataSource,
            superheroRemoteDataSourceImpl = superheroRemoteDataSource
        )
    }
}