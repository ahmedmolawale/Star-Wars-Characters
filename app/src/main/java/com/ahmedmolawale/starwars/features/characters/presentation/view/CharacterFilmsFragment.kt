package com.ahmedmolawale.starwars.features.characters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmedmolawale.starwars.databinding.CharacterFilmsFragmentBinding
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterFilmViewModel
import com.ahmedmolawale.starwars.features.characters.presentation.adapter.CharacterFilmsAdapter
import com.ahmedmolawale.starwars.features.characters.presentation.initRecyclerViewWithLineDecoration
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFilmsFragment : Fragment() {

    private var _binding: CharacterFilmsFragmentBinding? = null
    private val binding get() = _binding!!
    private val characterFilmViewModel: CharacterFilmViewModel by viewModels()
    private lateinit var characterFilmsAdapter: CharacterFilmsAdapter
    private var character: SCharacterPresentation? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterFilmsFragmentBinding.inflate(inflater, container, false)
        binding.characterFilmViewModel = characterFilmViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        character =
            requireArguments().getParcelable(CHARACTER_DETAILS)
        character?.let {
            characterFilmViewModel.getFilms(it.filmsUrls)
        }
        setupListAdapter()
    }


    private fun setupListAdapter() {
        val viewModel = binding.characterFilmViewModel
        if (viewModel != null) {
            characterFilmsAdapter =
                CharacterFilmsAdapter(
                    viewModel
                )
            context?.let {
                binding.filmList.initRecyclerViewWithLineDecoration(it)
            }
            binding.filmList.adapter = characterFilmsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "CharacterFilmFragment"
        val CHARACTER_DETAILS = "character_details"
    }

}