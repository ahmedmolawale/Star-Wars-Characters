package com.ahmedmolawale.starwars.features.characters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.local.model.CharacterEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.FilmEntity
import com.ahmedmolawale.starwars.features.characters.data.local.model.SpecieEntity

@Database(
    entities = [CharacterEntity::class, SpecieEntity::class, FilmEntity::class],
    version = 1, exportSchema = false
)
abstract class StarWarDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "star_war_database"
    }

    abstract fun charactersDao(): CharactersDao
}
