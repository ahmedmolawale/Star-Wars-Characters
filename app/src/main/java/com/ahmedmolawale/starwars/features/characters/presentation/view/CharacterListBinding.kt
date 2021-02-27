package com.ahmedmolawale.starwars.features.characters.presentation.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharacterFilmsAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharacterSpeciesAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharactersAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.model.FilmPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.model.SpeciePresentation

/**
 * [BindingAdapter]s for the [SCharacterPresentation]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, characters: List<SCharacterPresentation>?) {
    characters?.let {
        (listView.adapter as CharactersAdapter).submitList(characters)
    }
}

/**
 * [BindingAdapter]s for the [FilmPresentation]s list.
 */
@BindingAdapter("filmItems")
fun setFilmItems(listView: RecyclerView, films: List<FilmPresentation>?) {
    films?.let {
        (listView.adapter as CharacterFilmsAdapter).submitList(films)
    }
}

/**
 * [BindingAdapter]s for the [SpeciePresentation]s list.
 */
@BindingAdapter("specieItems")
fun setSpecieItems(listView: RecyclerView, species: List<SpeciePresentation>?) {
    species?.let {
        (listView.adapter as CharacterSpeciesAdapter).submitList(species)
    }
}
