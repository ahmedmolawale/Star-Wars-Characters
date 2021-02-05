package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedmolawale.starwars.UnitTest
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterFilmsUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.getOrAwaitValueTest
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterFilmViewModelTest : UnitTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCharacterFilmsUseCase: GetCharacterFilmsUseCase

    private lateinit var characterFilmViewModel: CharacterFilmViewModel
    private lateinit var filmsUrls: List<String>
    private lateinit var films: List<Film>

    @Before
    fun setup() {
        filmsUrls = listOf("http://swapi.dev/api/films/1/")
        films = listOf(
            Film(
                title = "Dior",
                url = "http://swapi.dev/api/films/1/",
                openingCrawl = "geet"
            )
        )
        characterFilmViewModel = CharacterFilmViewModel(getCharacterFilmsUseCase)
    }

    @Test
    fun `getFilms should return films list on success`() {
        every { getCharacterFilmsUseCase(any(), filmsUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Film>>) -> Unit>()(Either.Right(films))
        }
        characterFilmViewModel.getFilms(filmsUrls)

        val res = characterFilmViewModel.filmView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.films).isEqualTo(films.map { it.toPresentation() })
    }

    @Test
    fun `getFilms should show empty view when list of films is empty`() {
        every { getCharacterFilmsUseCase(any(), filmsUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Film>>) -> Unit>()(Either.Right(emptyList()))
        }
        characterFilmViewModel.getFilms(filmsUrls)

        val res = characterFilmViewModel.filmView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isTrue()
        Truth.assertThat(res.films).isNull()
    }

    @Test
    fun `getFilms should show error when an error occur`() {
        every { getCharacterFilmsUseCase(any(), filmsUrls, any()) }.answers {
            thirdArg<(Either<Failure, List<Specie>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        characterFilmViewModel.getFilms(filmsUrls)

        val res = characterFilmViewModel.filmView.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.films).isNull()
    }
}