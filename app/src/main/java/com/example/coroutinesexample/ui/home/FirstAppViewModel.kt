package com.example.coroutinesexample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesexample.data.repository.DataProvider
import com.example.coroutinesexample.ui.common.toEditable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// the viewModel only must be informed about what passed
class FirstAppViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean?>()
    val loginResult: LiveData<Boolean?> get() = _loginResult

    fun onSubmitClicked(user: String, pass: String) {

        viewModelScope.launch {

            Log.i("current thread", Thread.currentThread().name.toEditable().toString())

            _loginResult.value = withContext(Dispatchers.IO) {

                Log.i("current thread", Thread.currentThread().name.toEditable().toString())

                validateLogin(user, pass)
            }
        }
    }

    private fun validateLogin(user: String, pass: String): Boolean {

        Thread.sleep(2000)
        return user.isNotEmpty() && pass.isNotEmpty()
    }

    fun doSomething() {

        viewModelScope.launch {

            val result = withContext(Dispatchers.IO) {
                DataProvider.doHeavyTask()
            }
            println(result)
        }
    }
}