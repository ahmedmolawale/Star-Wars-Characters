package com.ahmedmolawale.starwars.features.characters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmedmolawale.starwars.databinding.CharacterSpeciesFragmentBinding
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterSpeciesViewModel
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharacterSpeciesAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.initRecyclerViewWithLineDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterSpeciesFragment : Fragment() {

    private var _binding: CharacterSpeciesFragmentBinding? = null
    private val binding get() = _binding!!
    private val characterSpeciesViewModel: CharacterSpeciesViewModel by viewModels()
    private lateinit var characterSpeciesAdapter: CharacterSpeciesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterSpeciesFragmentBinding.inflate(inflater, container, false)
        binding.characterSpeciesViewModel = characterSpeciesViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val speciesUrls = requireArguments().getStringArrayList(CHARACTER_DETAILS)
        print("Data $speciesUrls")
        speciesUrls?.let {
            characterSpeciesViewModel.getSpecies(it)
        }
        setupListAdapter()
    }


    private fun setupListAdapter() {
        val viewModel = binding.characterSpeciesViewModel
        if (viewModel != null) {
            characterSpeciesAdapter =
                CharacterSpeciesAdapter(
                    viewModel
                )
            context?.let {
                binding.specieList.initRecyclerViewWithLineDecoration(it)
            }
            binding.specieList.adapter = characterSpeciesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "CharacterSpecieFragment"
        val CHARACTER_DETAILS = "character_details"
    }

}