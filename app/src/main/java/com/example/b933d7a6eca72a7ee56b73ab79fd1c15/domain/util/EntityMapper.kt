package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.util

interface EntityMapper<T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

}