package com.ahmedmolawale.starwars.features.characters.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SCharacterPresentation(
    val name: String,
    val nameInitials: String,
    val birthYear: String,
    val birthYearDesc: String,
    val heightInCm: String,
    val heightInInches: String,
    val url: String,
    val planetUrl: String,
    val filmsUrls: List<String>,
    val speciesUrls: List<String>
) : Parcelable
