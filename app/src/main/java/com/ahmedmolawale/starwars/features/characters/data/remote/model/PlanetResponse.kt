package com.ahmedmolawale.starwars.features.characters.data.remote.model

import com.ahmedmolawale.starwars.features.characters.domain.model.Planet

data class PlanetResponse(
    val name: String,
    val population: String
){
    fun toDomainObject() = Planet(
        name = name,
        population = population
    )
}