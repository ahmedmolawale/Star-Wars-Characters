package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterDetailsRepository
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
    private lateinit var characterDetailsRepository: ICharacterDetailsRepository

    @Before
    fun setUp() {
        getCharacterFilmsUseCase = GetCharacterFilmsUseCase(characterDetailsRepository)
    }

    @Test
    fun `should call getFilms from repository`() = runBlockingTest {
        val filmsUrl = listOf("http://swapi.dev/api/films/1/")
        coEvery { characterDetailsRepository.getFilms(filmsUrl) } returns flow {
            emit(
                Either.Right(emptyList<Film>())
            )
        }
        getCharacterFilmsUseCase.run(filmsUrl)
        coVerify(exactly = 1) { characterDetailsRepository.getFilms(filmsUrl) }
    }
}