package com.example.coroutinesexample.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coroutinesexample.data.util.DataComponents
import com.example.coroutinesexample.domain.usecases.HeavyTaskUseCase
import com.example.coroutinesexample.domain.usecases.GetSuperheroUseCase
import com.example.coroutinesexample.ui.mapper.toUiModel
import com.example.coroutinesexample.ui.model.SuperherosUi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "MainViewModel"

class MainViewModel(
    private val getSuperheroUseCase: GetSuperheroUseCase,
    private val heavyTaskUseCase: HeavyTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    // sharedFlow is a hot flow that can be shared among multiple consumers and is oriented to events
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent: SharedFlow<Boolean?> get() = _loginEvent
    private val _loginAsyncEvent = MutableSharedFlow<Boolean>()
    val loginAsyncEvent: SharedFlow<Boolean?> get() = _loginAsyncEvent
    private val _getSeveralSuperheroes = MutableSharedFlow<List<SuperherosUi>?>()
    val getSeveralSuperheroes: SharedFlow<List<SuperherosUi>?> get() = _getSeveralSuperheroes

    fun performHeavyTask() {
        viewModelScope.launch {
            val resultHeavyTask = try {
                withContext(Dispatchers.IO) {
                    heavyTaskUseCase.heavyTask()
                }
            } catch (e: Exception) {
                // Handle error, maybe update another StateFlow for error messages
                null
                Log.e(TAG, "Error in doSomething: ", e)
            }
            _uiState.update { it.copy(heavyTask = resultHeavyTask.toString()) }
        }
    }

    fun getSuperhero(name: String) {
        viewModelScope.launch {
            val response = try {
                withContext(Dispatchers.IO) {
                    getSuperheroUseCase(name)
                }
            } catch (e: Exception) {
                null
            }
            _uiState.update {
                it.copy(superherosUi = response?.toUiModel())
            }
        }
    }

    fun validateLogin(user: String, pass: String) {
        viewModelScope.launch {
            Log.i(TAG, "Current Thread: ${Thread.currentThread().name}")
            val resultLogin = withContext(context = Dispatchers.IO) {
                Log.i(TAG, "Current Thread: ${Thread.currentThread().name}")
                delayLogin(user = user, pass = pass)
            }
            _loginEvent.emit(value = resultLogin)
        }
    }

    private fun delayLogin(user: String, pass: String): Boolean {
        Thread.sleep(2000)
        return user.isNotEmpty() && pass.isNotEmpty()
    }

    // To use parallelism with async and await, it's important throw all async functions before await
    fun validateAsyncLogin(user: String, pass: String) {
        viewModelScope.launch {
            val deferredResultOne = async(context = Dispatchers.IO) { delayLogin(user, pass) }
            val deferredResultTwo = async(context = Dispatchers.IO) { delayLogin(user, pass) }
            val resultOne = deferredResultOne.await()
            val resultTwo = deferredResultTwo.await()
            _loginAsyncEvent.emit(value = resultOne && resultTwo)
        }
    }

    fun getSeveralSuperheroes() {
        viewModelScope.launch {
            val response = try {
                withContext(Dispatchers.IO) {
                    val deferreds: List<Deferred<SuperherosUi>> = listOf(
                        async { getSuperheroUseCase("batman").toUiModel() },
                        async { getSuperheroUseCase("green").toUiModel() },
                        async { getSuperheroUseCase("flash").toUiModel() }
                    )
                    // wait for all request
                    deferreds.awaitAll()
                }
            } catch (e: Exception) {
                null
            }
            _getSeveralSuperheroes.emit(response)
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

            val taskRepository = DataComponents.taskRepository
            val getSuperheroUseCase = GetSuperheroUseCase(taskRepository)
            val heavyTaskUseCase = HeavyTaskUseCase(taskRepository)
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getSuperheroUseCase, heavyTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}