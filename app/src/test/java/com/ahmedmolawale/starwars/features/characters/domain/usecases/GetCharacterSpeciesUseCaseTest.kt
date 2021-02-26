package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharacterSpeciesUseCaseTest : UnitTest() {
    private lateinit var getCharacterSpeciesUseCase: GetCharacterSpeciesUseCase

    @MockK
    private lateinit var characterDetailsRepository: ICharacterDetailsRepository

    @Before
    fun setUp() {
        getCharacterSpeciesUseCase = GetCharacterSpeciesUseCase(characterDetailsRepository)
    }

    @Test
    fun `should call getSpecies from repository`() = runBlockingTest {
        val speciesUrl = listOf("http://swapi.dev/api/species/1/")
        coEvery { characterDetailsRepository.getSpecies(speciesUrl) } returns flow {
            emit(
                Either.Right(emptyList<Specie>())
            )
        }
        getCharacterSpeciesUseCase.run(speciesUrl)
        coVerify(exactly = 1) { characterDetailsRepository.getSpecies(speciesUrl) }
    }
}