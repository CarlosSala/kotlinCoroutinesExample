package com.example.coroutinesexample.ui.mapper

import com.example.coroutinesexample.domain.model.SuperheroImage
import com.example.coroutinesexample.domain.model.SuperheroItem
import com.example.coroutinesexample.domain.model.Superheros
import com.example.coroutinesexample.ui.model.SuperheroImageUi
import com.example.coroutinesexample.ui.model.SuperheroItemUi
import com.example.coroutinesexample.ui.model.SuperherosUi

fun Superheros.toUiModel(): SuperherosUi {
    return SuperherosUi(
        response = response,
        superheroes = superheroes.map { it.toUiModel() }
    )
}

fun SuperheroItem.toUiModel(): SuperheroItemUi {
    return SuperheroItemUi(
        superheroId = superheroId,
        name = name,
        image = image.toUiModel()
    )
}

fun SuperheroImage.toUiModel(): SuperheroImageUi {
    return SuperheroImageUi(
        url = url
    )
}