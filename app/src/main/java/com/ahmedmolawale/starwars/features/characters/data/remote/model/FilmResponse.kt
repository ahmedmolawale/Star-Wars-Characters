package com.ahmedmolawale.starwars.features.characters.data.remote.model

import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.squareup.moshi.Json

data class FilmResponse(
    val title: String,
    val url: String,
    @field:Json(name = "opening_crawl") val openingCrawl: String
) {
    fun toDomainObject() = Film(
        title = title,
        url = url,
        openingCrawl = openingCrawl
    )
}
