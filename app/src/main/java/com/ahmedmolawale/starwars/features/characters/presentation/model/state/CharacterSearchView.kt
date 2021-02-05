package com.ahmedmolawale.starwars.features.characters.presentation.model.state

import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation

data class CharacterSearchView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val isRecentList: Boolean = false,
    val characters: List<SCharacterPresentation>? = null
)