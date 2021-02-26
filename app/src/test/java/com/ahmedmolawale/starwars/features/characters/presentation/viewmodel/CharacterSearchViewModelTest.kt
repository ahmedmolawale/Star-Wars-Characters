package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharactersUseCase
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetRecentCharactersUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterSearchViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @MockK
    private lateinit var getRecentCharactersUseCase: GetRecentCharactersUseCase

    private lateinit var characterSearchViewModel: CharacterSearchViewModel
    private lateinit var characters: List<SCharacter>

    @Before
    fun setup() {
        characters = listOf(
            SCharacter(
                name = "Dior", url = "http://swapi.dev/api/people/1/",
                birthYear = "288BB", filmsUrls = emptyList(), speciesUrls = emptyList(),
                homeWorldUrl = "http://swapi.dev/api/planets/1/", height = "3445"
            )
        )
        characterSearchViewModel =
            CharacterSearchViewModel(getCharactersUseCase, getRecentCharactersUseCase)
    }

    @Test
    fun `searchCharacter should return actual list when search string is not empty`() {
        val searchString = "aa"
        every { getCharactersUseCase(any(), searchString, any()) }.answers {
            thirdArg<(Either<Failure, List<SCharacter>>) -> Unit>()(Either.Right(characters))
        }
        characterSearchViewModel.searchCharacters(searchString)

        val res = characterSearchViewModel.charactersSearchView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.isRecentList).isFalse()
        assertThat(res.characters).isEqualTo(characters.map { it.toPresentation() })
    }

    @Test
    fun `searchCharacter should show empty view when no character is found`() {
        val searchString = "aa"
        every { getCharactersUseCase(any(), searchString, any()) }.answers {
            thirdArg<(Either<Failure, List<SCharacter>>) -> Unit>()(Either.Right(emptyList()))
        }
        characterSearchViewModel.searchCharacters(searchString)

        val res = characterSearchViewModel.charactersSearchView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isTrue()
        assertThat(res.isRecentList).isFalse()
        assertThat(res.characters).isNull()
    }

    @Test
    fun `searchCharacter should show error view when error occurs`() {
        val searchString = "aa"
        every { getCharactersUseCase(any(), searchString, any()) }.answers {
            thirdArg<(Either<Failure, List<SCharacter>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        characterSearchViewModel.searchCharacters(searchString)

        val res = characterSearchViewModel.charactersSearchView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.isRecentList).isFalse()
        assertThat(res.characters).isNull()
    }

    @Test
    fun `searchCharacter should show recent when search string is empty`() {
        val searchString = ""
        every { getRecentCharactersUseCase(any(), any()) }.answers {
            secondArg<(Either<Failure, List<SCharacter>>) -> Unit>()(Either.Right(characters))
        }
        characterSearchViewModel.searchCharacters(searchString)
        val res = characterSearchViewModel.charactersSearchView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.isRecentList).isTrue()
    }
}
