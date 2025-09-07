package com.example.coroutinesexample.data.datasource.local

class DeviceLocalTaskDataSource : LocalTaskDataSource {
    override fun performLocalTask(): String {
        Thread.sleep(3000)
        return "Heavy task done"
    }
}