package com.example.coroutinesexample.ui.home

import com.example.coroutinesexample.ui.model.SuperherosUi

data class UiState(
    val loading: Boolean = false,
    val heavyTask: String? = null,
    val superherosUi: SuperherosUi? = null
)