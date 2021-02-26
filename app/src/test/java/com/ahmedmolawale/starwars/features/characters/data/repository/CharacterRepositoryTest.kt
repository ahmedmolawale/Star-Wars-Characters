package com.ahmedmolawale.starwars.features.characters.data.repository

import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.data.remote.model.CharacterResponse
import com.ahmedmolawale.starwars.features.characters.data.remote.model.CharacterSearchResponse
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
class CharacterRepositoryTest : UnitTest() {

    private lateinit var characterRepository: CharacterRepository

    private lateinit var characterSearchResponse: CharacterSearchResponse

    private lateinit var searchString: String

    @MockK
    private lateinit var apiService: StarWarApi

    @MockK
    private lateinit var charactersResponse: Response<CharacterSearchResponse>

    @MockK
    private lateinit var characterDao: CharactersDao

    @Before
    fun setUp() {
        searchString = "fire"
        characterRepository = CharacterRepository(apiService, characterDao)
    }

    @Test
    fun `searchCharacters with null responseBody should return data error`() = runBlockingTest {
        every { charactersResponse.body() } returns null
        every { charactersResponse.isSuccessful } returns true
        coEvery { apiService.searchCharacters(searchString) } returns charactersResponse

        val characters = characterRepository.searchCharacters(searchString)
        characters.collect { a ->
            assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `searchCharacters should return server error when response is not successful`() =
        runBlockingTest {
            every { charactersResponse.isSuccessful } returns false
            coEvery { apiService.searchCharacters(searchString) } returns charactersResponse

            val characters = characterRepository.searchCharacters(searchString)
            characters.collect { a ->
                assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @Test
    fun `searchCharacters should get characters list`() = runBlockingTest {
        characterSearchResponse = CharacterSearchResponse(
            1, "", "",
            results = listOf(
                CharacterResponse(
                    name = "Olawale",
                    birthYear = "2000",
                    height = "1.7",
                    url = "https://starwars.com",
                    homeworld = "http://swapi.dev/api/planets/1/",
                    species = emptyList(),
                    films = emptyList()
                )
            )
        )
        every { charactersResponse.body() } returns characterSearchResponse
        every { charactersResponse.isSuccessful } returns true
        coEvery { apiService.searchCharacters(searchString) } returns charactersResponse

        val characters = characterRepository.searchCharacters(searchString)
        characters.collect { a ->
            assertThat(a).isEqualTo(Either.Right(characterSearchResponse.results.map { it.toDomainObject() }))
        }
    }
}
