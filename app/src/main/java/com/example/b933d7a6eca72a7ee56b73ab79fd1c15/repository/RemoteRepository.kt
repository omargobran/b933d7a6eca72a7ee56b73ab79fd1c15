package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.SpaceStationService
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.model.SpaceStationDto
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.util.SpaceStationDtoMapper

class RemoteRepository(
    private val spaceStationService: SpaceStationService,
    private val spaceStationDtoMapper: SpaceStationDtoMapper
) {
    suspend fun getSpaceStations() = spaceStationService.getSpaceStations()

    private fun mapToSpaceStation(spaceStationDto: SpaceStationDto) =
        spaceStationDtoMapper.mapToDomainModel(spaceStationDto)

    fun mapToSpaceStationList(spaceStationDtoList: List<SpaceStationDto>) =
        spaceStationDtoList.map {
            mapToSpaceStation(it)
        }
}