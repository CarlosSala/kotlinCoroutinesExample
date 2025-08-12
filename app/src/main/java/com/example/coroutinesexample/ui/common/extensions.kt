package com.example.coroutinesexample.ui.common

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.coroutinesexample.R


// test in an activity
// more generic and can use en more sites like Context: Activity, Service, Application, etc.
fun Context.customToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Activity is a sub class of Context
fun Activity.myToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}


// test in an activity, using Glide library
fun ImageView.load(url: Any, width: Int, height: Int) {

    if (!url.isNull()) {

        Glide.with(this.context)
            .load(url) // image url
            .override(width, height)// resizing
            .placeholder(R.drawable.ic_bluetooth_24) // any placeholder to load at start
            .error(R.drawable.ic_launcher_background)// any image in case of error
            .centerCrop()
            .into(this) // imageview object
    }
}

// test in an activity
fun EditText.onTextChanged(listener: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            listener(s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}

// convert string to Editable
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

// use any anywhere
fun Any?.isNull() = this == null