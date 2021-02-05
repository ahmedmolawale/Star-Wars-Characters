package com.ahmedmolawale.starwars.features.characters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ahmedmolawale.starwars.R
import com.ahmedmolawale.starwars.databinding.CharacterSearchFragmentBinding
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterSearchViewModel
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharactersAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.initRecyclerViewWithLineDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterSearchFragment : Fragment() {

    private var _binding: CharacterSearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val characterSearchViewModel: CharacterSearchViewModel by viewModels()
    private lateinit var characterAdapter: CharactersAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterSearchFragmentBinding.inflate(inflater, container, false)
        binding.characterSearchViewModel = characterSearchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        characterSearchViewModel.getRecentCharacters()
        setupListAdapter()
        setupView()
    }

    private fun setupView() {
        binding.search.addTextChangedListener {
            if (it != null && it.isNotBlank()) {
                characterSearchViewModel.searchCharacters(it.toString())
            }
        }
        characterSearchViewModel.openCharacterDetailsEvent.observe(viewLifecycleOwner, Observer {
            val fm = activity?.supportFragmentManager
            val characterDetailsFragment =
                CharacterDetailsFragment()
            characterDetailsFragment.arguments = bundleOf(
                CHARACTER_DETAILS to it
            )
            fm?.beginTransaction()
                ?.add(R.id.main_host, characterDetailsFragment, CharacterDetailsFragment.TAG)
                ?.addToBackStack(CharacterDetailsFragment.TAG)?.commit()
        })
    }

    private fun setupListAdapter() {
        val viewModel = binding.characterSearchViewModel
        if (viewModel != null) {
            characterAdapter =
                CharactersAdapter(
                    viewModel
                )
            context?.let {
                binding.charactersRecyclerView.initRecyclerViewWithLineDecoration(it)
            }
            binding.charactersRecyclerView.adapter = characterAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "CharacterSearchFragment"
        val CHARACTER_DETAILS = "character_details"
    }

}