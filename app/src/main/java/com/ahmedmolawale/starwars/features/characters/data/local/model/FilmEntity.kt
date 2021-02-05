package com.ahmedmolawale.starwars.features.characters.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    var characterId: Long = 0
)