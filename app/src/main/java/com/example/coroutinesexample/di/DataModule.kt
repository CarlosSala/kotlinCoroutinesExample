package com.example.coroutinesexample.di

import com.example.coroutinesexample.data.datasource.local.HeavyTaskDataSource
import com.example.coroutinesexample.data.datasource.local.HeavyTaskDataSourceImpl
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSource
import com.example.coroutinesexample.data.datasource.remote.SuperheroRemoteDataSourceImpl
import com.example.coroutinesexample.data.network.SuperheroApiService
import com.example.coroutinesexample.data.repository.TaskRepository
import com.example.coroutinesexample.data.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSuperheroApiService(retrofit: Retrofit): SuperheroApiService {
        return retrofit.create(SuperheroApiService::class.java)
    }

    @Provides
    fun provideSuperheroRemoteDataSource(superheroApiService: SuperheroApiService): SuperheroRemoteDataSource {
        return SuperheroRemoteDataSourceImpl(superheroApiService)
    }

    @Provides
    fun provideHeavyTaskDataSource(): HeavyTaskDataSource {
        return HeavyTaskDataSourceImpl()
    }

    @Provides
    fun provideTaskRepository(
        heavyTaskDataSource: HeavyTaskDataSource,
        superheroRemoteDataSource: SuperheroRemoteDataSource
    ): TaskRepository {
        return TaskRepositoryImpl(
            heavyTaskDataSource = heavyTaskDataSource,
            superheroRemoteDataSource = superheroRemoteDataSource
        )
    }
}