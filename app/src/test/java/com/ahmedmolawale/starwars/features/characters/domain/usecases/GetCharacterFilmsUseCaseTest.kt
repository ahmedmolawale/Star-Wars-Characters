package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharacterFilmsUseCaseTest : UnitTest() {
    private lateinit var getCharacterFilmsUseCase: GetCharacterFilmsUseCase

    @MockK
    private lateinit var characterRepository: ICharacterRepository

    @Before
    fun setUp() {
        getCharacterFilmsUseCase = GetCharacterFilmsUseCase(characterRepository)
    }

    @Test
    fun `should call getFilms from repository`() = runBlockingTest {
        val filmsUrl = listOf("http://swapi.dev/api/films/1/")
        coEvery { characterRepository.getFilms(filmsUrl) } returns flow {
            emit(
                Either.Right(emptyList<Film>())
            )
        }
        getCharacterFilmsUseCase.run(filmsUrl)
        coVerify(exactly = 1) { characterRepository.getFilms(filmsUrl) }
    }
}