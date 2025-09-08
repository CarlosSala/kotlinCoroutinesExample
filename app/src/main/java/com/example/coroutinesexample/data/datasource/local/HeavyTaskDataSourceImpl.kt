package com.example.coroutinesexample.data.datasource.local

class HeavyTaskDataSourceImpl : HeavyTaskDataSource {
    override fun performHeavyTask(): String {
        Thread.sleep(3000)
        return "Done"
    }
}