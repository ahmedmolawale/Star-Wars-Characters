package com.ahmedmolawale.starwars.features.characters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.starwars.databinding.CharacterSpecieItemBinding
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterFilmViewModel
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterSpeciesViewModel
import com.ahmedmolawale.starwars.features.characters.presentation.model.SpeciePresentation

/**
 * Adapter for the films list. Has a reference to the [CharacterFilmViewModel] to send actions back to it.
 */
class CharacterSpeciesAdapter(private val viewModel: CharacterSpeciesViewModel) :
    ListAdapter<SpeciePresentation, CharacterSpeciesAdapter.ViewHolder>(
        SpeciesDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(private val binding: CharacterSpecieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CharacterSpeciesViewModel, item: SpeciePresentation) {
            binding.characterSpeciesViewModel = viewModel
            binding.specie = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterSpecieItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SpeciesDiffCallback : DiffUtil.ItemCallback<SpeciePresentation>() {
    override fun areItemsTheSame(
        oldItem: SpeciePresentation,
        newItem: SpeciePresentation
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: SpeciePresentation,
        newItem: SpeciePresentation
    ): Boolean {
        return oldItem == newItem
    }
}