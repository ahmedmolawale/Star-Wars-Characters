package com.ahmedmolawale.starwars.features.characters.data.remote.api

import com.ahmedmolawale.starwars.features.characters.data.remote.model.CharacterSearchResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.FilmResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.PlanetResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.SpecieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarApi {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): Response<CharacterSearchResponse>

    @GET
    suspend fun fetchSpecie(@Url specieUrl: String): Response<SpecieResponse>

    @GET
    suspend fun fetchFilm(@Url filmUrl: String): Response<FilmResponse>

    @GET
    suspend fun fetchPlanet(@Url planetUrl: String): Response<PlanetResponse>
}