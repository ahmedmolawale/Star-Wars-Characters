package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterSpeciesUseCase @Inject constructor(private val characterRepository: ICharacterDetailsRepository) :
    BaseUseCase<List<String>, List<Specie>>() {
    override suspend fun run(params: List<String>): Flow<Either<Failure, List<Specie>>> {
        return characterRepository.getSpecies(params)
    }
}