package com.ahmedmolawale.starwars.features.characters.presentation.mapper

import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.presentation.convertCMToInches
import com.ahmedmolawale.starwars.features.characters.presentation.extractInitials
import com.ahmedmolawale.starwars.features.characters.presentation.model.FilmPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.PlanetPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SpeciePresentation
import com.ahmedmolawale.starwars.features.characters.presentation.populationValue

fun SCharacter.toPresentation() = SCharacterPresentation(
    name = name,
    nameInitials = extractInitials(name),
    birthYear = birthYear,
    birthYearDesc = "Born in $birthYear",
    planetUrl = homeWorldUrl,
    url = url,
    heightInCm = height,
    heightInInches = convertCMToInches(height),
    filmsUrls = filmsUrls,
    speciesUrls = speciesUrls
)

fun Film.toPresentation() = FilmPresentation(
    title = title,
    url = url,
    description = openingCrawl
)

fun Specie.toPresentation() = SpeciePresentation(
    name = name,
    url = url,
    language = language
)

fun Planet.toPresentation() = PlanetPresentation(
    name = name,
    population = populationValue(population)
)
