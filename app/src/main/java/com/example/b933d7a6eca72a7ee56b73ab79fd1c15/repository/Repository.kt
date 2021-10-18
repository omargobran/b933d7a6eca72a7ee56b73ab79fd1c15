package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository

import android.util.Log
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceStationEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants.TAG
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    suspend fun updateSpaceship(spaceship: Spaceship): Flow<State<Spaceship>> = flow {
        try {
            val updatedRowsCount =
                localRepository.updateSpaceship(localRepository.mapFromSpaceShip(spaceship))
            if (updatedRowsCount > 0) {
                emit(State.Success(spaceship))
            } else {
                emit(State.Error(Exception("Güncelleme yapılamadı!")))
            }
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    fun updateSpaceshipSync(spaceship: Spaceship): Boolean {
        try {
            val updatedRowsCount =
                localRepository.updateSpaceshipSync(localRepository.mapFromSpaceShip(spaceship))
            return updatedRowsCount > 0
        } catch (exception: Exception) {
            Log.e(TAG, "updateSpaceshipSync: Exception.", exception)
        }

        return false
    }

    suspend fun addSpaceship(spaceship: Spaceship): Flow<State<Boolean>> = flow {
        try {
            val id = localRepository.insertSpaceship(localRepository.mapFromSpaceShip(spaceship))
            Log.d(TAG, "addSpaceship: inserted id = $id")
            emit(State.Success(id > 0))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    suspend fun getSpaceship(): Flow<State<Spaceship>> = flow {
        try {
            emit(State.Loading)
            val cachedSpaceship = localRepository.getSpaceship()
            emit(State.Success(localRepository.mapToSpaceship(cachedSpaceship)))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    suspend fun getFavoriteSpaceStations(): Flow<State<List<SpaceStation>>> = flow {
        try {
            emit(State.Loading)
            val cachedFavorites = localRepository.getFavoriteSpaceStations()
            emit(State.Success(localRepository.mapToSpaceStationList(cachedFavorites)))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    suspend fun getSpaceStationsFromCache(): Flow<State<List<SpaceStation>>> = flow {
        emit(State.Loading)
        try {
            val cachedSpaceStations = localRepository.getSpaceStations()
            emit(State.Success(localRepository.mapToSpaceStationList(cachedSpaceStations)))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    suspend fun getSpaceStations(isReset: Boolean): Flow<State<List<SpaceStation>>> = flow {
        emit(State.Loading)
        try {
            val oldCachedSpaceStations = localRepository.getSpaceStations()
            val networkSpaceStations = remoteRepository.getSpaceStations()
            val spaceStations = remoteRepository.mapToSpaceStationList(networkSpaceStations)

            spaceStations.map { newSpaceStation ->
                oldCachedSpaceStations.map { oldSpaceStation ->
                    setOldValues(oldSpaceStation, newSpaceStation, isReset)
                }
            }

            localRepository.insertSpaceStations(
                localRepository.mapFromSpaceStationList(
                    spaceStations
                )
            )
            val cachedSpaceStations = localRepository.getSpaceStations()

            emit(State.Success(localRepository.mapToSpaceStationList(cachedSpaceStations)))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    private fun setOldValues(
        oldSpaceStation: SpaceStationEntity,
        newSpaceStation: SpaceStation,
        reset: Boolean
    ) {
        if (newSpaceStation.name == oldSpaceStation.name) {
            newSpaceStation.id = oldSpaceStation.id
            newSpaceStation.isFavorite = oldSpaceStation.isFavorite

            if (!reset) {
                newSpaceStation.isVisited = oldSpaceStation.isVisited
                newSpaceStation.need = oldSpaceStation.need
                newSpaceStation.stock = oldSpaceStation.stock
            } else {
                newSpaceStation.isVisited = false
            }
        }
    }

    suspend fun favoriteSpaceStation(id: Int, favorite: Boolean): Flow<State<Boolean>> = flow {
        try {
            val selectedCachedSpaceStation = localRepository.getSpaceStationById(id)

            val selectedSpaceStation = localRepository.mapToSpaceStation(selectedCachedSpaceStation)
            selectedSpaceStation.isFavorite = favorite

            val updatedRowsCount =
                localRepository.updateSpaceStation(
                    localRepository.mapFromSpaceStation(
                        selectedSpaceStation
                    )
                )
            emit(State.Success(updatedRowsCount > 0))
        } catch (exception: Exception) {
            emit(State.Error(exception))
        }
    }

    fun favoriteSpaceStationSync(selectedSpaceStation: SpaceStation, favorite: Boolean): Boolean {
        try {
            selectedSpaceStation.isFavorite = favorite

            val updatedRowsCount =
                localRepository.updateSpaceStationSync(
                    localRepository.mapFromSpaceStation(
                        selectedSpaceStation
                    )
                )
            return updatedRowsCount > 0
        } catch (exception: Exception) {
            Log.e(TAG, "favoriteSpaceStationSync: Exception.", exception)
        }
        return false
    }

    fun updateSpaceStationSync(selectedSpaceStation: SpaceStation): Boolean {
        try {
            val updatedRowsCount =
                localRepository.updateSpaceStationSync(
                    localRepository.mapFromSpaceStation(
                        selectedSpaceStation
                    )
                )
            return updatedRowsCount > 0
        } catch (exception: Exception) {
            Log.e(TAG, "favoriteSpaceStationSync: Exception.", exception)
        }
        return false
    }
}