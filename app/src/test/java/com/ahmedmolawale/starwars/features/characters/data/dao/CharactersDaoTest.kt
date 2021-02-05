package com.ahmedmolawale.starwars.features.characters.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahmedmolawale.starwars.features.characters.data.local.StarWarDatabase
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterWithFilmsWithSpecies
import com.ahmedmolawale.starwars.features.characters.data.local.model.FilmEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.SpecieEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.jvm.Throws


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class CharactersDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: StarWarDatabase
    private lateinit var charactersDao: CharactersDao
    private lateinit var characterEntity: CharacterEntity
    private lateinit var specieEntities: List<SpecieEntity>
    private lateinit var filmEntities: List<FilmEntity>
    private lateinit var characterWithFilmsWithSpecies: List<CharacterWithFilmsWithSpecies>

    @Before
    fun setUp() {
        //setup room db
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, StarWarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        charactersDao = db.charactersDao()
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
    fun `insert a character should save a character to db`() = runBlockingTest {
        charactersDao.insertACharacter(
            characterEntity
        )
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters[0].character).isEqualTo(characterEntity)
    }

    @Test
    fun `insert species should save species to db`() = runBlockingTest {
        charactersDao.insertACharacter(
            characterEntity
        )
        charactersDao.insertSpecies(
            specieEntities
        )
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters[0].species).isEqualTo(specieEntities)
    }

    @Test
    fun `delete species should remove species from db`() = runBlockingTest {
        //need to insert a character before inserting films because films depends on character
        charactersDao.insertACharacter(
            characterEntity
        )
        charactersDao.insertSpecies(
            specieEntities
        )
        charactersDao.deleteSpecies()
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters[0].species).isEmpty()
    }

    @Test
    fun `insert films should save films to db`() = runBlockingTest {
        //need to insert a character before inserting films because films depends on character
        charactersDao.insertACharacter(
            characterEntity
        )
        charactersDao.insertFilms(
            filmEntities
        )
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters[0].films).isEqualTo(filmEntities)
    }

    @Test
    fun `delete films should remove films from db`() = runBlockingTest {
        //need to insert a character before inserting films because films depends on character
        charactersDao.insertACharacter(
            characterEntity
        )
        charactersDao.insertFilms(
            filmEntities
        )
        charactersDao.deleteFilms()
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters[0].films).isEmpty()
    }

    @Test
    fun `delete a character should remove a character from db`() = runBlockingTest {
        charactersDao.insertACharacter(
            characterEntity
        )
        charactersDao.deleteCharacters()
        val savedCharacters = charactersDao.getAllCharacters()
        assertThat(savedCharacters).isEmpty()
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