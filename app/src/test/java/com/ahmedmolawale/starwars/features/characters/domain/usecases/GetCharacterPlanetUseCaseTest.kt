package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
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
class GetCharacterPlanetUseCaseTest : UnitTest(){
    private lateinit var getCharacterPlanetUseCase: GetCharacterPlanetUseCase

    @MockK
    private lateinit var characterRepository: ICharacterRepository

    @Before
    fun setUp() {
        getCharacterPlanetUseCase = GetCharacterPlanetUseCase(characterRepository)
    }

    @Test
    fun `should call getPlanet from repository`() = runBlockingTest {
        val planetUrl = "http://swapi.dev/api/planets/1/"
        coEvery { characterRepository.getPlanet(planetUrl) } returns flow {
            emit(
                Either.Right(Planet("Door", population = "23456"))
            )
        }
        getCharacterPlanetUseCase.run(planetUrl)
        coVerify(exactly = 1) { characterRepository.getPlanet(planetUrl) }
    }
}