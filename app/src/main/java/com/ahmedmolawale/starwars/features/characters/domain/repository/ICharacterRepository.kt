package com.ahmedmolawale.starwars.features.characters.domain.repository

import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {
    suspend fun searchCharacters(characterName: String): Flow<Either<Failure, List<SCharacter>>>
    suspend fun recentCharacters(): Flow<Either<Failure, List<SCharacter>>>
}
