package com.example.coroutinesexample.ui.common

import android.content.Context
import android.text.Editable
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.coroutinesexample.R


// test in an activity
// more generic and can use en more sites like Context: Activity, Service, Application, etc.
fun Context.customToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

// use any anywhere
fun Any?.isNull() = this == null
