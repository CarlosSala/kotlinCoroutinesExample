package com.example.coroutinesexample.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutinesexample.R
import com.example.coroutinesexample.databinding.ActivityMainBinding
import com.example.coroutinesexample.ui.common.customToast
import com.example.coroutinesexample.ui.detail.ResultActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var etUser: String = "et user"
    private var etPass: String = "et pass"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // 3 utilities form extensions functions
        // myToast("Custom toast for extensions functions")
        //  binding.iv.load(R.drawable.male_symbol, 200, 200)
        //  binding.etUsername.onTextChanged { "The text contains $it".also(::println) }

        // those two flows are collected since the app is started
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.heavyTask.collect { result ->
                    if (result != null) {
                        binding.tvTask.text = getString(R.string.heavy_task, result)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.superhero.collect { result ->
                    if (result != null) {
                        binding.tvServer.text = getString(R.string.server_response, result)

                    }
                }
            }
        }

        // this flow is collected only when login is successful
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginEvent.collect { result ->
                    if (result != null) {
                        customToast(if (result) "Success" else "Failure")
                        binding.etUsername.setText("")
                        binding.etPassword.setText("")
                    }
                    if (result == true) {
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("EXTRA_NAME", etUser)
                        startActivity(intent)
                    }
                }
            }
        }

        // init ui listener
        initListener()

        viewModel.heavyTask()
        viewModel.getSuperhero("superman")
    }

    private fun initListener() {

        // login one coroutine
        binding.btnLogin.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()

            // Log.i("current thread", Thread.currentThread().name.toEditable().toString())
            viewModel.validateLogin(user = etUser, pass = etPass)
        }

        /*// login two coroutines, parallels coroutine
        binding.btnLogin1.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()

            lifecycleScope.launch {

                val resultOne = async(context = Dispatchers.IO) {
                    viewModel.validateLogin(etUser, etPass)
                }

                val resultTwo = async(context = Dispatchers.IO) {
                    viewModel.validateLogin(etUser, etPass)
                }

                customToast(if (resultOne.await() && resultTwo.await()) "Success login" else "Failure login")

                if (resultOne.await() && resultTwo.await()) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("EXTRA_NAME", etUser)
                    startActivity(intent)
                }
            }
        }*/
    }


    /*    fun waitForCoroutines() {
            lifecycleScope.launch(Dispatchers.IO) {

                *//*
                    val deferred1 = async { retrofit.getSuperHeroes("a") }
                    val deferred2 = async { retrofit.getSuperHeroes("b") }
                    val deferred3 = async { retrofit.getSuperHeroes("c") }
                    val deferred4 = async { retrofit.getSuperHeroes("d") }

                    val response1 = deferred1.await()
                    val response2 = deferred2.await()
                    val response3 = deferred3.await()
                    val response4 = deferred4.await()
        *//*

            val deferreds: List<Deferred<Response<SuperheroDataResponseDto>>> = listOf(
                async { retrofit.getSuperHeroes("batman") },
                async { retrofit.getSuperHeroes("super") },
                async { retrofit.getSuperHeroes("a") },
                async { retrofit.getSuperHeroes("man") })

            // wait for all request
            val response = deferreds.awaitAll()
        }
    }*/

}

