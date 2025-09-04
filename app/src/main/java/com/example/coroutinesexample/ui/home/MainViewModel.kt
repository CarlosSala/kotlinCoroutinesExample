package com.example.coroutinesexample.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesexample.data.repository.DataProvider
import com.example.coroutinesexample.domain.usecases.GetServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "MainViewModel"

// the viewModel only must be informed about what passed
class MainViewModel : ViewModel() {

    private val getServerResponse = GetServerResponse()

    // sharedFlow is a hot flow that can be shared among multiple consumers
    // and is oriented to events
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent: SharedFlow<Boolean?> get() = _loginEvent

    private val _heavyTask = MutableStateFlow<String?>(null)
    val heavyTask: StateFlow<String?> get() = _heavyTask

    private val _superhero = MutableStateFlow<String?>(null)
    val superhero: StateFlow<String?> get() = _superhero

    fun validateLogin(user: String, pass: String) {
        viewModelScope.launch {
            // Log.i(TAG, "Current Thread: ${Thread.currentThread().name}")
            // withContext is a suspend function
            val resultLogin = withContext(context = Dispatchers.IO) {
                // Log.i(TAG, "Current Thread: ${Thread.currentThread().name.toEditable()}")
                delayLogin(user = user, pass = pass)
            }
            _loginEvent.emit(resultLogin)
        }
    }

    private fun delayLogin(user: String, pass: String): Boolean {

        Thread.sleep(2000)
        return user.isNotEmpty() && pass.isNotEmpty()
    }

    fun heavyTask() {
        viewModelScope.launch {
            try {
                val resultHeavyTask = withContext(Dispatchers.IO) {
                    DataProvider.doHeavyTask()
                }
                _heavyTask.value = resultHeavyTask
            } catch (e: Exception) {
                // Handle error, maybe update another StateFlow for error messages
                _heavyTask.value = null // Or some error indicator
                Log.e(TAG, "Error in doSomething: ", e)
            }
        }
    }

    fun getSuperhero(name: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _superhero.value = getServerResponse(name).response
            }
        }
    }

}