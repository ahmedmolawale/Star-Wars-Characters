package com.ahmedmolawale.starwars.features.characters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.starwars.databinding.CharacterItemBinding
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation

/**
 * Adapter for the characters list.
 */
class CharactersAdapter(private val onClickCharacter: (SCharacterPresentation) -> Unit) :
    ListAdapter<SCharacterPresentation, CharactersAdapter.ViewHolder>(
        CharacterDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(onClickCharacter, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onClickCharacter: (SCharacterPresentation) -> Unit, item: SCharacterPresentation) {
            binding.root.setOnClickListener {
                onClickCharacter(item)
            }
            binding.character = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterItemBinding.inflate(layoutInflater, parent, false)
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
class CharacterDiffCallback : DiffUtil.ItemCallback<SCharacterPresentation>() {
    override fun areItemsTheSame(
        oldItem: SCharacterPresentation,
        newItem: SCharacterPresentation
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: SCharacterPresentation,
        newItem: SCharacterPresentation
    ): Boolean {
        return oldItem == newItem
    }
}
