package com.ahmedmolawale.starwars.features.characters.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter

data class CharacterWithFilmsWithSpecies(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val films: List<FilmEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val species: List<SpecieEntity>
) {
    fun toDomainObject() = SCharacter(
        name = character.name,
        birthYear = character.birthYear, height = character.height,
        url = character.url, homeWorldUrl = character.homeWorldUrl,
        filmsUrls = films.map { it.url }, speciesUrls = species.map { it.url }
    )
}