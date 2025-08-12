package com.example.coroutinesexample.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coroutinesexample.R
import com.example.coroutinesexample.data.model.SuperheroDataResponse
import com.example.coroutinesexample.data.network.ApiService
import com.example.coroutinesexample.data.network.RetrofitHelper
import com.example.coroutinesexample.data.repository.DataProvider
import com.example.coroutinesexample.databinding.ActivityMainBinding
import com.example.coroutinesexample.ui.common.load
import com.example.coroutinesexample.ui.common.myToast
import com.example.coroutinesexample.ui.common.onTextChanged
import com.example.coroutinesexample.ui.common.toEditable
import com.example.coroutinesexample.ui.common.customToast
import com.example.coroutinesexample.ui.detail.ResultActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FirstAppViewModel
    private lateinit var binding: ActivityMainBinding
    private var etUser: String = "et user"
    private var etPass: String = "et pass"
    private lateinit var retrofit: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FirstAppViewModel::class.java]

        retrofit = RetrofitHelper.getInstanceRetrofit()

        // 3 utilities form extensions functions
        myToast("Custom toast for extensions functions")
        binding.iv.load(R.drawable.male_symbol, 200, 200)
        binding.etUsername.onTextChanged { "The text contains $it".also(::println) }

        // subscribe liveData for login three
        viewModel.loginResult.observe(this) { result ->

            customToast(if (result == true) "Success" else "Failure")
            if (result == true) {
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra("EXTRA_NAME", etUser)
                startActivity(intent)
            }
        }

        firstCoroutine()
        initListener()
        retrofitCoroutine()
    }

    private fun firstCoroutine() {

        // GlobalScope.launch(Dispatchers.Main) {
        lifecycleScope.launch {

            // withContext is a suspend function
            val result = withContext(Dispatchers.IO) {
                DataProvider.doHeavyTask()
            }
            // println(result)
            // myToast(result)
            binding.etPassword.hint = result

        }
    }

    private fun initListener() {

        // login one coroutine
        binding.btnLogin.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()

            lifecycleScope.launch {

                Log.i("current thread", Thread.currentThread().name.toEditable().toString())

                val result = withContext(context = Dispatchers.IO) {
                    Log.i("current thread", Thread.currentThread().name.toEditable().toString())
                    validateLogin(user = etUser, password = etPass)
                }

                customToast(if (result) "Success login" else "Failure login")

                if (result) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("EXTRA_NAME", etUser)
                    // startActivity(intent)
                }
            }
        }

        // login two coroutines, parallels coroutine
        binding.btnLogin1.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()

            lifecycleScope.launch {

                val resultOne = async(context = Dispatchers.IO) {
                    validateLogin(etUser, etPass)
                }

                val resultTwo = async(context = Dispatchers.IO) {
                    validateLogin(etUser, etPass)
                }

                customToast(if (resultOne.await() && resultTwo.await()) "Success login" else "Failure login")

                if (resultOne.await() && resultTwo.await()) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("EXTRA_NAME", etUser)
                    startActivity(intent)
                }
            }
        }

        // login three, coroutine in viewModel
        binding.btnLogin2.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()

            // val successThree = vm.onSubmitClicked(etUser, etPass)
            // toast(if (successThree) "Success" else "Failure")

            viewModel.onSubmitClicked(etUser, etPass)
        }
    }

    private fun validateLogin(user: String, password: String): Boolean {

        // emulate delay of server
        Thread.sleep(2000)
        return user.isNotEmpty() && password.isNotEmpty()
    }

    private fun retrofitCoroutine() {

        lifecycleScope.launch(Dispatchers.IO) {

            val response: Response<SuperheroDataResponse> =
                // getSuperHeroes is a suspend function
                retrofit.getSuperHeroes("amor")

            if (response.isSuccessful) {

                // return main thread
                withContext(Dispatchers.Main) {
                    val message = "Response from sever is: ${response.isSuccessful}"
                    binding.etUsername.hint = message
                    // toast(message)
                    myToast(message)
                }
            }
        }
    }

    fun waitForCoroutines() {
        lifecycleScope.launch(Dispatchers.IO) {

            /*
                    val deferred1 = async { retrofit.getSuperHeroes("a") }
                    val deferred2 = async { retrofit.getSuperHeroes("b") }
                    val deferred3 = async { retrofit.getSuperHeroes("c") }
                    val deferred4 = async { retrofit.getSuperHeroes("d") }

                    val response1 = deferred1.await()
                    val response2 = deferred2.await()
                    val response3 = deferred3.await()
                    val response4 = deferred4.await()
        */

            val deferreds: List<Deferred<Response<SuperheroDataResponse>>> = listOf(
                async { retrofit.getSuperHeroes("batman") },
                async { retrofit.getSuperHeroes("super") },
                async { retrofit.getSuperHeroes("a") },
                async { retrofit.getSuperHeroes("man") })

            // wait for all request
            val response = deferreds.awaitAll()
        }
    }

}

