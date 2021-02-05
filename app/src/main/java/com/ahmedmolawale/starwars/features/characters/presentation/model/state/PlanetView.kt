package com.ahmedmolawale.starwars.features.characters.presentation.model.state

import com.ahmedmolawale.starwars.features.characters.presentation.model.PlanetPresentation

data class PlanetView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val planet: PlanetPresentation? = null
)