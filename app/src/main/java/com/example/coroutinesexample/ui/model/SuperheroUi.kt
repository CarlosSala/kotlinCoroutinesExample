package com.example.coroutinesexample.ui.model

data class SuperherosUi(
    val response: String,
    val superheroes: List<SuperheroItemUi>
)

data class SuperheroItemUi(
    val superheroId: String,
    val name: String,
    val image: SuperheroImageUi,

    )

data class SuperheroImageUi(
    val url: String,
)