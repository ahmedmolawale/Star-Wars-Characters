package com.ahmedmolawale.starwars.features.characters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.starwars.databinding.CharacterFilmItemBinding
import com.ahmedmolawale.starwars.features.characters.presentation.model.FilmPresentation

/**
 * Adapter for the films list.
 */
class CharacterFilmsAdapter :
    ListAdapter<FilmPresentation, CharacterFilmsAdapter.ViewHolder>(
        FilmsDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(private val binding: CharacterFilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmPresentation) {
            binding.film = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterFilmItemBinding.inflate(layoutInflater, parent, false)
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
class FilmsDiffCallback : DiffUtil.ItemCallback<FilmPresentation>() {
    override fun areItemsTheSame(
        oldItem: FilmPresentation,
        newItem: FilmPresentation
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: FilmPresentation,
        newItem: FilmPresentation
    ): Boolean {
        return oldItem == newItem
    }
}
