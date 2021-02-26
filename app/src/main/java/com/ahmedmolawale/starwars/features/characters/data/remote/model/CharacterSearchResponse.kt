package com.ahmedmolawale.starwars.features.characters.data.remote.model

import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterWithFilmsWithSpecies
import com.ahmedmolawale.starwars.features.characters.data.local.model.FilmEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.SpecieEntity
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.squareup.moshi.Json

data class CharacterResponse(
    private val name: String,
    @field:Json(name = "birth_year") private val birthYear: String,
    private val height: String,
    private val url: String,
    private val homeworld: String,
    private val films: List<String>,
    private val species: List<String>
) {
    fun toDomainObject() = SCharacter(
        name = name,
        birthYear = birthYear,
        height = height,
        url = url,
        homeWorldUrl = homeworld,
        filmsUrls = films,
        speciesUrls = species
    )

    fun toCharacterEntity() =
        CharacterWithFilmsWithSpecies(
            character = CharacterEntity(
                name = name,
                birthYear = birthYear,
                height = height,
                url = url,
                homeWorldUrl = homeworld
            ),
            films = films.map { FilmEntity(url = it) },
            species = species.map { SpecieEntity(url = it) }

        )
}

data class CharacterSearchResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<CharacterResponse>
)
