package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
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
class GetCharactersUseCaseTest : UnitTest() {

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @MockK
    private lateinit var characterRepository: ICharacterRepository

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCase(characterRepository)
    }

    @Test
    fun `should call searchCharacters from repository`() = runBlockingTest {
        val searchString = "search"
        coEvery { characterRepository.searchCharacters(searchString) } returns flow {
            emit(
                Either.Right(
                    emptyList<SCharacter>()
                )
            )
        }
        getCharactersUseCase.run(searchString)
        coVerify(exactly = 1) { characterRepository.searchCharacters(searchString) }
    }
}
