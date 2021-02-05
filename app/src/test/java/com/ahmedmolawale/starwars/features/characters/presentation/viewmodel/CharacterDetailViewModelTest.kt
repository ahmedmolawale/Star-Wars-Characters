package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterPlanetUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterDetailViewModelTest : UnitTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharacterPlanetUseCase: GetCharacterPlanetUseCase

    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private lateinit var planetUrl: String
    private lateinit var planet: Planet
    private lateinit var character: SCharacterPresentation

    @Before
    fun setup() {
        planetUrl = "http://swapi.dev/api/films/1/"
        planet =
            Planet(
                name = "Dior",
                population = "20000"
            )
        character = SCharacterPresentation(
            name = "ola",
            url = "",
            planetUrl = "",
            birthYearDesc = "",
            nameInitials = "",
            speciesUrls = emptyList(),
            filmsUrls = emptyList(),
            heightInInches = "",
            heightInCm = "",
            birthYear = ""
        )

        characterDetailViewModel = CharacterDetailViewModel(getCharacterPlanetUseCase)
    }

    @Test
    fun `getPlanet should return planet on success`() {
        every { getCharacterPlanetUseCase(any(), planetUrl, any()) }.answers {
            thirdArg<(Either<Failure, Planet>) -> Unit>()(Either.Right(planet))
        }
        characterDetailViewModel.getPlanet(planetUrl)

        val res = characterDetailViewModel.planetView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.planet).isEqualTo(planet.toPresentation())
    }

    @Test
    fun `getFilms should show error when an error occur`() {
        every { getCharacterPlanetUseCase(any(), planetUrl, any()) }.answers {
            thirdArg<(Either<Failure, Planet>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        characterDetailViewModel.getPlanet(planetUrl)

        val res = characterDetailViewModel.planetView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.loading).isFalse()
        assertThat(res.planet).isNull()
    }

    @Test
    fun `setCharacter should set character value`() {
        characterDetailViewModel.setCharacter(character)

        val res = characterDetailViewModel.character.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res).isEqualTo(character)
    }

    @Test
    fun `retryPlanetAgain should call getPlanet when character has a value`() {
        every { getCharacterPlanetUseCase(any(), character.planetUrl, any()) }.answers {
            thirdArg<(Either<Failure, Planet>) -> Unit>()(Either.Right(planet))
        }
        characterDetailViewModel.setCharacter(character)
        characterDetailViewModel.retryPlanetAgain()
        val res = characterDetailViewModel.planetView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.planet).isEqualTo(planet.toPresentation())
    }

    @Test
    fun `retryPlanetAgain should not call getPlanet when character has no value`() {
        characterDetailViewModel.retryPlanetAgain()
        verify(exactly = 0) { characterDetailViewModel.getPlanet(character.planetUrl) }
    }
}
