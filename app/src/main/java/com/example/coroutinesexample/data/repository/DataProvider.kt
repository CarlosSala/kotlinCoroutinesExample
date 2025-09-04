package com.example.coroutinesexample.data.repository

object DataProvider {

    fun doHeavyTask(): String {
        Thread.sleep(3000)
        return "Heavy task done"
    }
}