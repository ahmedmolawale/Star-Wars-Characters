package com.ahmedmolawale.starwars.features.characters.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedmolawale.starwars.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val characterSearch =
            CharacterSearchFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_host, characterSearch, CharacterSearchFragment.TAG)
            .commit()
    }
}
