package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceStationEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.util.EntityMapper

class SpaceStationEntityMapper : EntityMapper<SpaceStationEntity, SpaceStation> {

    override fun mapToDomainModel(model: SpaceStationEntity) = SpaceStation(
        model.id,
        model.name,
        model.coordinate,
        model.capacity,
        model.stock,
        model.need,
        model.isFavorite,
        model.isVisited
    )

    override fun mapFromDomainModel(domainModel: SpaceStation) = SpaceStationEntity(
        domainModel.id,
        domainModel.name,
        domainModel.coordinate,
        domainModel.capacity,
        domainModel.stock,
        domainModel.need,
        domainModel.isFavorite,
        domainModel.isVisited
    )

}