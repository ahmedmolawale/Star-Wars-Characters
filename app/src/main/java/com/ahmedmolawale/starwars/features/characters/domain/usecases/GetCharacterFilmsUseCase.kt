package com.ahmedmolawale.starwars.features.characters.domain.usecases

import com.ahmedmolawale.starwars.core.baseinteractor.BaseUseCase
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterFilmsUseCase @Inject constructor(private val characterRepository: ICharacterRepository) :
    BaseUseCase<List<String>, List<Film>>() {
    override suspend fun run(params: List<String>): Flow<Either<Failure, List<Film>>> {
        return characterRepository.getFilms(params)
    }
}