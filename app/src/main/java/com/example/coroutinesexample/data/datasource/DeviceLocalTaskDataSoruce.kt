package com.example.coroutinesexample.data.datasource

import com.example.coroutinesexample.data.repository.DataProvider

class DeviceLocalTaskDataSource : LocalTaskDataSource {
    override fun performLocalTask(): String {
        return DataProvider.doHeavyTask()
    }
}