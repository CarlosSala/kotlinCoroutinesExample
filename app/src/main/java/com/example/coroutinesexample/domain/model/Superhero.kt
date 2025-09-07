package com.example.coroutinesexample.domain.model

data class Superheros(
    val response: String,
    val superheroes: List<SuperheroItem>
)

data class SuperheroItem(
    val superheroId: String,
    val name: String,
    val image: SuperheroImage,
)

data class SuperheroImage(
    val url: String,
)