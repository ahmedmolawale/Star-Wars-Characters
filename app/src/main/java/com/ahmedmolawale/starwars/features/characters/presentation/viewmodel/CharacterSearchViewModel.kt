package com.ahmedmolawale.starwars.features.characters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetCharactersUseCase
import com.ahmedmolawale.starwars.features.characters.domain.usecases.GetRecentCharactersUseCase
import com.ahmedmolawale.starwars.features.characters.presentation.mapper.toPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.state.CharacterSearchView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
    private val getRecentCharactersUseCase: GetRecentCharactersUseCase
) :
    ViewModel() {

    private val job = Job()
    private val _openCharacterDetailsEvent = MutableLiveData<SCharacterPresentation>()
    val openCharacterDetailsEvent: LiveData<SCharacterPresentation>
        get() = _openCharacterDetailsEvent

    private val _charactersSearchView = MutableLiveData<CharacterSearchView>()
    val charactersSearchView: LiveData<CharacterSearchView>
        get() = _charactersSearchView

    fun searchCharacters(searchString: String) {
        //job.cancel()
        _charactersSearchView.value = CharacterSearchView(loading = true)
        if (searchString.isNotBlank()) {
            getCharacters(job, searchString) {
                it.fold(
                    ::handleFailure,
                    ::handleSuccess
                )
            }
        } else {
            getRecentCharacters()
        }
    }

    private fun handleSuccess(characters: List<SCharacter>) {
        if (characters.isNotEmpty()) {
            _charactersSearchView.value =
                CharacterSearchView(
                    characters = characters.map { it.toPresentation() },
                    isRecentList = _charactersSearchView.value?.isRecentList ?: false
                )
        } else
            _charactersSearchView.value = CharacterSearchView(isEmpty = true)
    }

    private fun handleFailure(failure: Failure) {
        //Todo error message should be based on failure type
        _charactersSearchView.value = CharacterSearchView(errorMessage = "An error occurred.")
    }

    fun getRecentCharacters() {
        _charactersSearchView.value = CharacterSearchView(loading = true, isRecentList = true)
        getRecentCharactersUseCase(job, GetRecentCharactersUseCase.None()) {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openCharacterDetails(character: SCharacterPresentation) {
        _openCharacterDetailsEvent.value = character
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}