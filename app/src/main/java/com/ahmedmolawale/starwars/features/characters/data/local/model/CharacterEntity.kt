package com.ahmedmolawale.starwars.features.characters.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "birth_year") val birthYear: String,
    val height: String,
    val url: String,
    val homeWorldUrl: String
)