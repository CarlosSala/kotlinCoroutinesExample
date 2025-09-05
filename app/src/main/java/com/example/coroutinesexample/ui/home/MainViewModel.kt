package com.example.coroutinesexample.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesexample.data.model.SuperheroDataResponseDto
import com.example.coroutinesexample.data.repository.DataProvider
import com.example.coroutinesexample.domain.usecases.GetServerResponse
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

data class UiState(
    val loading: Boolean = false,
    val heavyTask: String? = null,
    val responseServer: String? = null
)

// the viewModel only must be informed about what passed
class MainViewModel : ViewModel() {

    private val getServerResponse = GetServerResponse()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    // sharedFlow is a hot flow that can be shared among multiple consumers
    // and is oriented to events
    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent: SharedFlow<Boolean?> get() = _loginEvent
    private val _loginAsyncEvent = MutableSharedFlow<Boolean>()
    val loginAsyncEvent: SharedFlow<Boolean?> get() = _loginAsyncEvent
    private val _getSeveralSuperheroes = MutableSharedFlow<String?>()
    val getSeveralSuperheroes: SharedFlow<String?> get() = _getSeveralSuperheroes

    fun heavyTask() {
        viewModelScope.launch {
            val resultHeavyTask = try {
                withContext(Dispatchers.IO) {
                    DataProvider.doHeavyTask()
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
                    getServerResponse(name).response
                }
            } catch (e: Exception) {
                null
            }
            _uiState.update {
                it.copy(responseServer = response.toString())
            }
        }
    }

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

    fun validateAsyncLogin(user: String, pass: String) {
        viewModelScope.launch {
            val deferredResultOne = async(context = Dispatchers.IO) { delayLogin(user, pass) }
            val deferredResultTwo = async(context = Dispatchers.IO) { delayLogin(user, pass) }
            val resultOne = deferredResultOne.await()
            val resultTwo = deferredResultTwo.await()
            _loginAsyncEvent.emit(resultOne && resultTwo)
        }
    }

    fun getSeveralSuperheroes() {
        viewModelScope.launch {

            val response = try {
                withContext(Dispatchers.IO) {

                    /*  val deferred1 = async { getServerResponse("batman") }
                        val deferred2 = async { getServerResponse("superman") }
                        val response1 = deferred1.await()
                        val response2 = deferred2.await() */

                    val deferreds: List<Deferred<SuperheroDataResponseDto>> = listOf(
                        async { getServerResponse("batman") },
                        async { getServerResponse("green") },
                        async { getServerResponse("flash") }
                    )

                    // wait for all request
                    deferreds.awaitAll()
                }
            } catch (e: Exception) {
                null
            }

            var text = ""
            response?.forEachIndexed { index, superheroDataResponseDto ->
                text += "${index + 1}. ${superheroDataResponseDto.superheroes[index].name} "
            }
            _getSeveralSuperheroes.emit(text)
        }
    }
}