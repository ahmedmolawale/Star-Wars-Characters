package com.ahmedmolawale.starwars.features.characters.data.repository

import com.ahmedmolawale.starwars.core.exception.Failure
import com.ahmedmolawale.starwars.core.ext.enforceHttps
import com.ahmedmolawale.starwars.core.functional.Either
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.domain.model.Film
import com.ahmedmolawale.starwars.features.characters.domain.model.Planet
import com.ahmedmolawale.starwars.features.characters.domain.model.Specie
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDetailsRepository @Inject constructor(
    private val apiService: StarWarApi
) :
    ICharacterDetailsRepository {

    override suspend fun getFilms(filmUrls: List<String>): Flow<Either<Failure, List<Film>>> =
        flow {
            val films = mutableListOf<Film>()
            for (filmUrl in filmUrls) {
                try {
                    val res = apiService.fetchFilm(filmUrl.enforceHttps())
                    if (res.isSuccessful && res.body() != null) {
                        films.add(res.body()!!.toDomainObject())
                    }
                } catch (exception: Throwable) {
                }
            }
            emit(Either.Right(films))
        }

    override suspend fun getPlanet(planetUrl: String): Flow<Either<Failure, Planet>> = flow {
        val res = apiService.fetchPlanet(planetUrl.enforceHttps())
        emit(
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let { it ->
                        Either.Right(it.toDomainObject())
                    } ?: Either.Left(Failure.DataError)
                }
                false -> Either.Left(Failure.ServerError)
            }
        )
    }

    override suspend fun getSpecies(speciesUrls: List<String>): Flow<Either<Failure, List<Specie>>> =
        flow {
            val species = mutableListOf<Specie>()
            for (specieUrl in speciesUrls) {
                try {
                    val res = apiService.fetchSpecie(specieUrl.enforceHttps())
                    if (res.isSuccessful && res.body() != null) {
                        species.add(res.body()!!.toDomainObject())
                    }
                } catch (exception: Throwable) {
                }
            }
            emit(Either.Right(species))
        }
}
