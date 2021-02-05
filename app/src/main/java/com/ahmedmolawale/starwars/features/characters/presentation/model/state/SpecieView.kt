package com.ahmedmolawale.starwars.features.characters.presentation.model.state

import com.ahmedmolawale.starwars.features.characters.presentation.model.SpeciePresentation

data class SpecieView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val species: List<SpeciePresentation>? = null
)