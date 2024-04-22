package com.example.coroutinesexample.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.coroutinesexample.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult = findViewById<TextView>(R.id.tv_result)
        // is name is a null string, orEmpty() returns a empty string
        val name: String = intent.extras?.getString("EXTRA_NAME").orEmpty()
        tvResult.text = getString(R.string.hello_world, name)
    }
}