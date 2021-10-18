package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model

data class SpaceStation(
    var id: Int,
    var name: String,
    var coordinate: Coordinate,
    var capacity: Long,
    var stock: Long,
    var need: Long,
    var isFavorite: Boolean,
    var isVisited: Boolean
)
