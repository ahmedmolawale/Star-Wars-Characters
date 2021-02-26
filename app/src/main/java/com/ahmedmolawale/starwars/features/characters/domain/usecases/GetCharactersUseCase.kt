package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: ICharacterRepository) :
    BaseUseCase<String, List<SCharacter>>() {
    override suspend fun run(params: String): Flow<Either<Failure, List<SCharacter>>> {
        return characterRepository.searchCharacters(params)
    }
}
