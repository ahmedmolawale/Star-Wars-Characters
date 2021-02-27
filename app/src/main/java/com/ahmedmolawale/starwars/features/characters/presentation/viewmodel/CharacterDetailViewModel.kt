package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterPlanetUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.state.PlanetView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterPlanetUseCase: GetCharacterPlanetUseCase,
) :
    ViewModel() {

    private val job = Job()
    private val _planetView = MutableLiveData<PlanetView>()
    val planetView: LiveData<PlanetView>
        get() = _planetView

    private val _character = MutableLiveData<SCharacterPresentation>()
    val character: LiveData<SCharacterPresentation>
        get() = _character

    fun getPlanet(url: String) {
        _planetView.value = PlanetView(loading = true)
        getCharacterPlanetUseCase(job, url) {
            it.fold(
                ::handlePlanetFailure,
                ::handlePlanetSuccess
            )
        }
    }

    private fun handlePlanetSuccess(planet: Planet) {
        _planetView.value = PlanetView(planet = planet.toPresentation())
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handlePlanetFailure(failure: Failure) {
        // Todo error message should be based on failure type
        _planetView.value =
            PlanetView(errorMessage = "Unable to get planet. Tap to try again")
    }

    // Used through data binding
    fun retryPlanetAgain() {
        character.value?.let {
            getPlanet(it.planetUrl)
        }
    }

    fun setCharacter(character: SCharacterPresentation) {
        _character.value = character
    }
    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
