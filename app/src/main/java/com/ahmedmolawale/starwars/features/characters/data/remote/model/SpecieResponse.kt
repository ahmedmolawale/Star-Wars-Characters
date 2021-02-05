package com.ahmedmolawale.starwars.features.characters.data.remote.model

import com.ahmedmolawale.starwars.features.characters.domain.model.Specie

data class SpecieResponse(
    val name: String,
    val url: String,
    val language: String
) {
    fun toDomainObject() = Specie(
        name = name,
        url = url,
        language = language
    )
}