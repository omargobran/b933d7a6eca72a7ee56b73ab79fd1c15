package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceStationDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceshipDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceStationEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceshipEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util.SpaceStationEntityMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util.SpaceshipEntityMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship

class LocalRepository(
    private val spaceStationDao: SpaceStationDao,
    private val spaceshipDao: SpaceshipDao,
    private val spaceStationEntityMapper: SpaceStationEntityMapper,
    private val spaceshipEntityMapper: SpaceshipEntityMapper
) {
    suspend fun getSpaceship() = spaceshipDao.getSpaceship()

    suspend fun getFavoriteSpaceStations() = spaceStationDao.getFavoriteSpaceStations()

    suspend fun getSpaceStations() = spaceStationDao.getSpaceStations()

    suspend fun insertSpaceStations(spaceStationEntities: List<SpaceStationEntity>) =
        spaceStationDao.insertSpaceStations(spaceStationEntities)

    fun mapFromSpaceShip(spaceship: Spaceship) =
        spaceshipEntityMapper.mapFromDomainModel(spaceship)

    fun mapToSpaceship(spaceshipEntity: SpaceshipEntity) =
        spaceshipEntityMapper.mapToDomainModel(spaceshipEntity)

    fun mapToSpaceStation(spaceStationEntity: SpaceStationEntity) =
        spaceStationEntityMapper.mapToDomainModel(spaceStationEntity)

    fun mapFromSpaceStation(spaceStation: SpaceStation) =
        spaceStationEntityMapper.mapFromDomainModel(spaceStation)

    fun mapToSpaceStationList(spaceStationEntities: List<SpaceStationEntity>) =
        spaceStationEntities.map {
            mapToSpaceStation(it)
        }

    fun mapFromSpaceStationList(spaceStations: List<SpaceStation>) =
        spaceStations.map {
            mapFromSpaceStation(it)
        }

    suspend fun getSpaceStationById(id: Int) = spaceStationDao.getSpaceStationById(id)

    suspend fun deleteAllSpaceStations() = spaceStationDao.deleteTable()

    suspend fun updateSpaceStation(spaceStationEntity: SpaceStationEntity) =
        spaceStationDao.update(spaceStationEntity)

    fun updateSpaceStationSync(spaceStationEntity: SpaceStationEntity) =
        spaceStationDao.updateSync(spaceStationEntity)

    suspend fun insertSpaceship(spaceshipEntity: SpaceshipEntity) =
        spaceshipDao.insert(spaceshipEntity)

    suspend fun updateSpaceship(spaceshipEntity: SpaceshipEntity) =
        spaceshipDao.update(spaceshipEntity)

    fun updateSpaceshipSync(spaceshipEntity: SpaceshipEntity) =
        spaceshipDao.updateSync(spaceshipEntity)
}