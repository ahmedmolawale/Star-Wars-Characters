package com.ahmedmolawale.starwars.features.characters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.ahmedmolawale.starwars.databinding.CharacterDetailFragmentBinding
import com.ahmedmolawale.starwars.features.characters.presentation.model.SCharacterPresentation
import com.ahmedmolawale.starwars.features.characters.presentation.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()
    private var character: SCharacterPresentation? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        binding.characterDetailViewModel = characterDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        character =
            requireArguments().getParcelable(CharacterSearchFragment.CHARACTER_DETAILS)
        character?.let {
            characterDetailViewModel.setCharacter(it)
            characterDetailViewModel.getPlanet(it.planetUrl)
        }
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.pager.adapter =
            PageAdapter(
                childFragmentManager
            )
        binding.pager.offscreenPageLimit = 2
        binding.tabLayout.setupWithViewPager(binding.pager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "CharacterDetailsFragment"
    }

    @ExperimentalCoroutinesApi
    inner class PageAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    val filmsFragment =
                        CharacterFilmsFragment()
                    filmsFragment.arguments = bundleOf(
                        CharacterFilmsFragment.CHARACTER_DETAILS to character
                    )
                    filmsFragment
                }
                else -> {
                    val characterSpeciesFragment =
                        CharacterSpeciesFragment()
                    characterSpeciesFragment.arguments = bundleOf(
                        CharacterSpeciesFragment.CHARACTER_DETAILS to character?.speciesUrls
                    )
                    characterSpeciesFragment
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Films"
                else -> "Species"
            }
        }
    }
}
