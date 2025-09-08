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
import com.example.coroutinesexample.ui.common.load
import com.example.coroutinesexample.ui.detail.ResultActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var etUser: String = ""
    private var etPass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        // extension function to load image with glide
        binding.iv.load(R.drawable.male_symbol, 200, 200)

        // repeatOnLifecycle launches the block in a new coroutine every time the
        // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.tvTask.text =
                        getString(R.string.heavy_task, uiState.heavyTask)
                    binding.tvServer.text =
                        getString(
                            R.string.server_response,
                            uiState.superherosUi?.response.toString()
                        )
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginEvent.collect { result ->
                    if (result != null) {
                        customToast(if (result) "Success" else "Failure")
                    }
                    if (result == true) {
                        binding.etUsername.setText("")
                        binding.etPassword.setText("")
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("EXTRA_NAME", etUser)
                        startActivity(intent)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginAsyncEvent.collect { result ->
                    if (result != null) {
                        customToast(if (result) "Success" else "Failure")
                    }
                    if (result == true) {
                        binding.etUsername.setText("")
                        binding.etPassword.setText("")
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("EXTRA_NAME", etUser)
                        startActivity(intent)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSeveralSuperheroes.collect { listSuperheroes ->
                    if (listSuperheroes != null) {
                        var text = ""
                        listSuperheroes.forEachIndexed { index, superherosUi ->
                            text += "${index + 1}. ${superherosUi.superheroes[index].name} "
                        }
                        customToast(text)
                    }
                }
            }
        }

        // init ui listener
        initListener()

        viewModel.performHeavyTask()
        viewModel.getSuperhero("superman")
    }

    private fun initListener() {

        binding.btnLogin.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()
            viewModel.validateLogin(user = etUser, pass = etPass)
        }

        // login two coroutines, parallels coroutine
        binding.btnLogin1.setOnClickListener {

            etUser = binding.etUsername.text.toString()
            etPass = binding.etPassword.text.toString()
            viewModel.validateAsyncLogin(user = etUser, pass = etPass)
        }

        binding.btnLogin2.setOnClickListener {
            viewModel.getSeveralSuperheroes()
        }
    }
}

