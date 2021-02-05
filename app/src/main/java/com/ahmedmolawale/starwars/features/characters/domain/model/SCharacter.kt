package com.ahmedmolawale.starwars.features.characters.domain.model


/**
 * A data class to hold information about a star war character
 * Note that I have named it [SCharacter] to avoid conflict with the standard [Character] class
 */
data class SCharacter(
    val name: String,
    val birthYear: String,
    val height: String,
    val url: String,
    val homeWorldUrl: String,
    val filmsUrls: List<String>,
    val speciesUrls: List<String>
)
