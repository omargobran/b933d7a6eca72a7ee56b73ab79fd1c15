package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceshipEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.util.EntityMapper

class SpaceshipEntityMapper : EntityMapper<SpaceshipEntity, Spaceship> {

    override fun mapToDomainModel(model: SpaceshipEntity) = Spaceship(
        model.id,
        model.name,
        model.durability,
        model.speed,
        model.capacity,
        model.ds,
        model.eus,
        model.ugs,
        model.hp,
        model.timer,
        model.currentStation
    )

    override fun mapFromDomainModel(domainModel: Spaceship) = SpaceshipEntity(
        domainModel.id,
        domainModel.name,
        domainModel.durability,
        domainModel.speed,
        domainModel.capacity,
        domainModel.ds,
        domainModel.eus,
        domainModel.ugs,
        domainModel.hp,
        domainModel.timer,
        domainModel.currentStation
    )

}