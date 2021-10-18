package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.util

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Coordinate
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.util.EntityMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.model.SpaceStationDto

class SpaceStationDtoMapper : EntityMapper<SpaceStationDto, SpaceStation> {

    override fun mapToDomainModel(model: SpaceStationDto): SpaceStation {
        return SpaceStation(
            0,
            model.name,
            Coordinate(model.coordinateX, model.coordinateY),
            model.capacity,
            model.stock,
            model.need,
            isFavorite = false,
            isVisited = false
        )
    }

    override fun mapFromDomainModel(domainModel: SpaceStation) = SpaceStationDto(
        domainModel.name,
        domainModel.coordinate.x,
        domainModel.coordinate.y,
        domainModel.capacity,
        domainModel.stock,
        domainModel.need
    )

}
