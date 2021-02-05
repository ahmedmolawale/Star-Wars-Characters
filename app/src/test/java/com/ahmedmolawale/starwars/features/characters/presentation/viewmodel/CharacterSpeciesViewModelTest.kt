package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterSpeciesUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterSpeciesViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharacterSpeciesUseCase: GetCharacterSpeciesUseCase

    private lateinit var characterSpeciesViewModel: CharacterSpeciesViewModel
    private lateinit var speciesUrls: List<String>
    private lateinit var species: List<Specie>

    @Before
    fun setup() {
        speciesUrls = listOf("http://swapi.dev/api/species/1/", "http://swapi.dev/api/species/2/")
        species = listOf(Specie(name = "Dior", url = "", language = "geet"))
        characterSpeciesViewModel = CharacterSpeciesViewModel(getCharacterSpeciesUseCase)
    }

    @Test
    fun `getSpecies should return species list on success`() {
        every { getCharacterSpeciesUseCase(any(), speciesUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Specie>>) -> Unit>()(Either.Right(species))
        }
        characterSpeciesViewModel.getSpecies(speciesUrls)

        val res = characterSpeciesViewModel.specieView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.species).isEqualTo(species.map { it.toPresentation() })
    }

    @Test
    fun `getSpecies should show empty view when list of species is empty`() {
        every { getCharacterSpeciesUseCase(any(), speciesUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Specie>>) -> Unit>()(Either.Right(emptyList()))
        }
        characterSpeciesViewModel.getSpecies(speciesUrls)

        val res = characterSpeciesViewModel.specieView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isTrue()
        assertThat(res.species).isNull()
    }

    @Test
    fun `getSpecies should show error when an error occur`() {
        every { getCharacterSpeciesUseCase(any(), speciesUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Specie>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        characterSpeciesViewModel.getSpecies(speciesUrls)

        val res = characterSpeciesViewModel.specieView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.loading).isFalse()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.species).isNull()
    }
}