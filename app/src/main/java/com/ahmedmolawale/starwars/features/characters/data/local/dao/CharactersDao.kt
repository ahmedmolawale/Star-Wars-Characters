package com.ahmedmolawale.starwars.features.characters.data.local.dao

import androidx.room.*
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterWithFilmsWithSpecies
import com.ahmedmolawale.starwars.features.characters.data.local.model.FilmEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.SpecieEntity

@Dao
interface CharactersDao {

    @Query("DELETE FROM characters")
    suspend fun deleteCharacters(): Int

    @Query("DELETE FROM films")
    suspend fun deleteFilms(): Int

    @Query("DELETE FROM species")
    suspend fun deleteSpecies(): Int

    /**
     * Needs to be a transaction as multiple queries will be run and it has to happen atomically
     */
    @Transaction
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterWithFilmsWithSpecies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertACharacter(character: CharacterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(film: List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: List<SpecieEntity>)

    @Transaction
    suspend fun insertCharacters(characterWithFilmsWithSpecies: List<CharacterWithFilmsWithSpecies>) {
        for (character in characterWithFilmsWithSpecies) {
            val characterId = insertACharacter(character = character.character)
            insertFilms(character.films.map {
                it.characterId = characterId
                it
            })
            insertSpecies(character.species.map {
                it.characterId = characterId
                it
            })
        }
    }

    @Transaction
    suspend fun deleteAllCharacters() {
        deleteFilms()
        deleteSpecies()
        deleteCharacters()
    }
}