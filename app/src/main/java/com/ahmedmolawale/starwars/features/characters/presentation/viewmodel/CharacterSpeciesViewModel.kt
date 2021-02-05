package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharacterSpeciesUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.state.SpecieView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CharacterSpeciesViewModel @Inject constructor(
    private val getCharacterSpeciesUseCase: GetCharacterSpeciesUseCase,
) :
    ViewModel() {

    private val job = Job()
    private val _specieView = MutableLiveData<SpecieView>()
    val specieView: LiveData<SpecieView>
        get() = _specieView


    fun getSpecies(urls: List<String>) {
        _specieView.value = SpecieView(loading = true)
        getCharacterSpeciesUseCase(job, urls) {
            it.fold(
                ::handleSpecieFailure,
                ::handleSpecieSuccess
            )
        }
    }

    private fun handleSpecieSuccess(species: List<Specie>) {
        if (species.isEmpty()) {
            _specieView.value = SpecieView(isEmpty = true)
        } else {
            _specieView.value = SpecieView(species = species.map { it.toPresentation() })
        }
    }

    private fun handleSpecieFailure(failure: Failure) {
        _specieView.value =
            SpecieView(errorMessage = "An error has occurred")
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
