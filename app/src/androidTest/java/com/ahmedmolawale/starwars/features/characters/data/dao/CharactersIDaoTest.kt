package com.ahmedmolawale.starwars.features.characters.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.ahmedmolawale.starwars.features.characters.data.local.StarWarDatabase
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterWithFilmsWithSpecies
import com.ahmedmolawale.starwars.features.characters.data.local.model.FilmEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.SpecieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CharactersIDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: StarWarDatabase

    private lateinit var charactersDao: CharactersDao
    private lateinit var characterEntity: CharacterEntity
    private lateinit var specieEntities: List<SpecieEntity>
    private lateinit var filmEntities: List<FilmEntity>
    private lateinit var characterWithFilmsWithSpecies: List<CharacterWithFilmsWithSpecies>

    @Before
    fun setUp() {
        //setup room db
        hiltRule.inject()
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(context, StarWarDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
        //charactersDao = db.charactersDao()
        characterEntity =
            CharacterEntity(
                id = 1,
                name = "Olawale",
                birthYear = "2034",
                height = "35",
                url = "https://trivago.com",
                homeWorldUrl = "https://trivago.com"
            )

        specieEntities = listOf(
            SpecieEntity(
                id = 1,
                url = "https://trivago.com",
                characterId = 1
            )
        )
        filmEntities = listOf(
            FilmEntity(
                id = 1,
                url = "https://trivago.com",
                characterId = 1
            )
        )

        characterWithFilmsWithSpecies = listOf(
            CharacterWithFilmsWithSpecies(
                character = characterEntity,
                films = filmEntities,
                species = specieEntities
            )
        )

    }

    @Test
    fun insert_a_character_should_save_a_character_to_db() = runBlockingTest {
//        charactersDao.insertACharacter(
//            characterEntity
//        )
//        val savedCharacters = charactersDao.getAllCharacters()
//        assertThat(savedCharacters[0].character).isEqualTo(characterEntity)
    }


    @After
    @Throws(IOException::class)
    fun tearDown() {
        runBlockingTest {
            db.clearAllTables()
        }
        db.close()
    }
}