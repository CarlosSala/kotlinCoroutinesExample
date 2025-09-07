package com.example.coroutinesexample.data.mapper

import com.example.coroutinesexample.data.model.SuperheroDto
import com.example.coroutinesexample.data.model.SuperheroImageResponse
import com.example.coroutinesexample.data.model.SuperheroItemResponse
import com.example.coroutinesexample.domain.model.SuperheroImage
import com.example.coroutinesexample.domain.model.SuperheroItem
import com.example.coroutinesexample.domain.model.Superheros

fun SuperheroDto.toDomain(): Superheros {
    return Superheros(
        response = this.response,
        superheroes = this.superheroes.map { it.toDomain() }
    )
}

fun SuperheroItemResponse.toDomain(): SuperheroItem {
    return SuperheroItem(
        superheroId = this.superheroId,
        name = this.name,
        image = this.image.toDomain()
    )
}

fun SuperheroImageResponse.toDomain(): SuperheroImage {
    return SuperheroImage(
        url = this.url
    )
}