package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterFilmsUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.state.FilmView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CharacterFilmViewModel @Inject constructor(
    private val getCharacterFilmsUseCase: GetCharacterFilmsUseCase,
) :
    ViewModel() {

    private val job = Job()
    private val _filmView = MutableLiveData<FilmView>()
    val filmView: LiveData<FilmView>
        get() = _filmView

    fun getFilms(urls: List<String>) {
        _filmView.value = FilmView(loading = true)
        getCharacterFilmsUseCase(job, urls) {
            it.fold(
                ::handlePlanetFailure,
                ::handlePlanetSuccess
            )
        }
    }

    private fun handlePlanetSuccess(films: List<Film>) {
        if (films.isEmpty()) {
            _filmView.value = FilmView(isEmpty = true)
        } else {
            _filmView.value = FilmView(films = films.map { it.toPresentation() })
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handlePlanetFailure(failure: Failure) {
        _filmView.value =
            FilmView(errorMessage = "An error has occurred")
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
