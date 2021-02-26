package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterPlanetUseCase @Inject constructor(private val characterRepository: ICharacterDetailsRepository) :
    BaseUseCase<String, Planet>() {
    override suspend fun run(params: String): Flow<Either<Failure, Planet>> {
        return characterRepository.getPlanet(params)
    }
}