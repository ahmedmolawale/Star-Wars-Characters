package com.ahmedmolawale.starwars.features.characters.data.repository

import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.features.characters.data.local.dao.CharactersDao
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.data.remote.model.CharacterResponse
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.model.SCharacter
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
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

    override suspend fun getFilms(filmUrls: List<String>): Flow<Either<Failure, List<Film>>> =
        flow {
            val films = mutableListOf<Film>()
            for (filmUrl in filmUrls) {
                try {
                    val res = apiService.fetchFilm(filmUrl)
                    if (res.isSuccessful && res.body() != null) {
                        films.add(res.body()!!.toDomainObject())
                    }
                } catch (exception: Throwable) {
                }
            }
            emit(Either.Right(films))
        }

    override suspend fun getPlanet(planetUrl: String): Flow<Either<Failure, Planet>> = flow {
        val res = apiService.fetchPlanet(planetUrl)
        emit(when (res.isSuccessful) {
            true -> {
                res.body()?.let { it ->
                    Either.Right(it.toDomainObject())
                } ?: Either.Left(Failure.DataError)
            }
            false -> Either.Left(Failure.ServerError)
        })
    }

    override suspend fun getSpecies(speciesUrls: List<String>): Flow<Either<Failure, List<Specie>>> =
        flow {
            val species = mutableListOf<Specie>()
            for (specieUrl in speciesUrls) {
                try {
                    val res = apiService.fetchSpecie(specieUrl)
                    if (res.isSuccessful && res.body() != null) {
                        species.add(res.body()!!.toDomainObject())
                    }
                } catch (exception: Throwable) {
                }
            }
            emit(Either.Right(species))
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