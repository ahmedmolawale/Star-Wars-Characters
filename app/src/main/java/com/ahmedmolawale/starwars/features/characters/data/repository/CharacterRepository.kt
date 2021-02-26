package com.ahmedmolawale.starwars.features.characters.data.repository

import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.data.remote.model.CharacterResponse
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val apiService: StarWarApi,
    private val charactersDao: CharactersDao
) :
    ICharacterRepository {
    override suspend fun searchCharacters(characterName: String): Flow<Either<Failure, List<SCharacter>>> =
        flow {
            val res = apiService.searchCharacters(characterName)
            emit(when (res.isSuccessful) {
                true -> {
                    res.body()?.let { it ->
                        cacheCharacters(it.results)
                        Either.Right(it.results.map { a -> a.toDomainObject() })
                    } ?: Either.Left(Failure.DataError)
                }
                false -> Either.Left(Failure.ServerError)
            })
        }

    override suspend fun recentCharacters(): Flow<Either<Failure, List<SCharacter>>> = flow {
        val res = charactersDao.getAllCharacters()
        emit(Either.Right(res.map { it.toDomainObject() }))
    }


    private suspend fun cacheCharacters(list: List<CharacterResponse>) {
        //saving only the first ten
        if (list.size >= 10) {
            charactersDao.deleteAllCharacters()
            charactersDao.insertCharacters(list.map {
                it.toCharacterEntity()
            }.subList(0, 10))
        }
    }
}