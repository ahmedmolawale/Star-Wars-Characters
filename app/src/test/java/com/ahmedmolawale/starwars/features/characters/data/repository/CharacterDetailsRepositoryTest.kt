package com.ahmedmolawale.starwars.features.characters.data.repository

import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.data.remote.model.FilmResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.PlanetResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.SpecieResponse
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CharacterDetailsRepositoryTest : UnitTest() {

    private lateinit var characterDetailsRepository: CharacterDetailsRepository

    private lateinit var planetUrl: String
    private lateinit var filmUrls: List<String>
    private lateinit var specieUrls: List<String>

    @MockK
    private lateinit var apiService: StarWarApi

    @MockK
    private lateinit var filmResponse: Response<FilmResponse>

    @MockK
    private lateinit var planetResponse: Response<PlanetResponse>

    @MockK
    private lateinit var specieResponse: Response<SpecieResponse>

    @Before
    fun setUp() {
        planetUrl = "https://swapi.dev/api/planets/1/"
        filmUrls = listOf(
            "https://swapi.dev/api/fimls/1/"
        )
        specieUrls = listOf(
            "https://swapi.dev/api/species/1/"
        )
        characterDetailsRepository = CharacterDetailsRepository(apiService)
    }

    @Test
    fun `getPlanet with null responseBody should return data error`() = runBlockingTest {
        val planetUrl = "https://swapi.dev/api/planets/1/"
        every { planetResponse.body() } returns null
        every { planetResponse.isSuccessful } returns true
        coEvery { apiService.fetchPlanet(planetUrl) } returns planetResponse

        val planet = characterDetailsRepository.getPlanet(planetUrl)
        planet.collect { a ->
            assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `getPlanet should return server error when response is not successful`() = runBlockingTest {
        every { planetResponse.isSuccessful } returns false
        coEvery { apiService.fetchPlanet(planetUrl) } returns planetResponse

        val planet = characterDetailsRepository.getPlanet(planetUrl)
        planet.collect { a ->
            assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
        }
    }

    @Test
    fun `getPlanet should get planet successfully`() = runBlockingTest {
        val planetRes = PlanetResponse(
            name = "Tatooine",
            population = "20000"
        )
        every { planetResponse.body() } returns planetRes
        every { planetResponse.isSuccessful } returns true
        coEvery { apiService.fetchPlanet(planetUrl) } returns planetResponse

        val res = characterDetailsRepository.getPlanet(planetUrl)
        res.collect { a ->
            assertThat(a).isEqualTo(Either.Right(planetRes.toDomainObject()))
        }
    }

    @Test
    fun `getFilms should return empty list if exception occurs in all cases`() = runBlockingTest {
        coEvery { apiService.fetchFilm(filmUrls[0]) } returns filmResponse
        val res = characterDetailsRepository.getFilms(filmUrls)
        res.collect { a ->
            assertThat(a).isEqualTo(Either.Right(emptyList<Film>()))
        }
    }

    @Test
    fun `getFilms should return empty list if response not successful in all cases`() =
        runBlockingTest {
            every { filmResponse.isSuccessful } returns false
            coEvery { apiService.fetchFilm(filmUrls[0]) } returns filmResponse

            val res = characterDetailsRepository.getFilms(filmUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Film>()))
            }
        }

    @Test
    fun `getFilms should return empty list if response body is null in all cases`() =
        runBlockingTest {
            every { filmResponse.isSuccessful } returns true
            every { filmResponse.body() } returns null
            coEvery { apiService.fetchFilm(filmUrls[0]) } returns filmResponse

            val res = characterDetailsRepository.getFilms(filmUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Film>()))
            }
        }

    @Test
    fun `getFilms should return list of films`() =
        runBlockingTest {
            val filmRes = FilmResponse(
                title = "A New Hope",
                openingCrawl = "It is a period of civil war.\\r\\nRebel spaceships, string",
                url = "https://swapi.dev/api/films/1/"
            )
            every { filmResponse.isSuccessful } returns true
            every { filmResponse.body() } returns filmRes
            coEvery { apiService.fetchFilm(filmUrls[0]) } returns filmResponse

            val res = characterDetailsRepository.getFilms(filmUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(listOf(filmRes.toDomainObject())))
            }
        }

    @Test
    fun `getSpecies should return empty list if exception occurs in all cases`() = runBlockingTest {
        coEvery { apiService.fetchSpecie(specieUrls[0]) } returns specieResponse
        val res = characterDetailsRepository.getSpecies(specieUrls)
        res.collect { a ->
            assertThat(a).isEqualTo(Either.Right(emptyList<Specie>()))
        }
    }

    @Test
    fun `getSpecies should return empty list if response not successful in all cases`() =
        runBlockingTest {
            every { specieResponse.isSuccessful } returns false
            coEvery { apiService.fetchSpecie(specieUrls[0]) } returns specieResponse

            val res = characterDetailsRepository.getSpecies(specieUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Specie>()))
            }
        }

    @Test
    fun `getSpecies should return empty list if response body is null in all cases`() =
        runBlockingTest {
            every { specieResponse.isSuccessful } returns true
            every { specieResponse.body() } returns null
            coEvery { apiService.fetchSpecie(specieUrls[0]) } returns specieResponse

            val res = characterDetailsRepository.getSpecies(specieUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Specie>()))
            }
        }

    @Test
    fun `getSpecies should return list of films`() =
        runBlockingTest {
            val specieRes = SpecieResponse(
                name = "Dinor",
                language = "Language",
                url = "http://swapi.dev/api/species/1/"
            )
            every { specieResponse.isSuccessful } returns true
            every { specieResponse.body() } returns specieRes
            coEvery { apiService.fetchSpecie(specieUrls[0]) } returns specieResponse

            val res = characterDetailsRepository.getSpecies(specieUrls)
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(listOf(specieRes.toDomainObject())))
            }
        }
}
