package com.example.coroutinesexample.data.datasource.local

interface HeavyTaskDataSource {
    fun performHeavyTask(): String
}