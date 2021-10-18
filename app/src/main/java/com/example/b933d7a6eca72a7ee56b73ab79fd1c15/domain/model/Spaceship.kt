package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model

data class Spaceship(
    val id: Int,
    var name: String,
    var durability: Long,
    var speed: Long,
    var capacity: Long,
    var ds: Long,
    var eus: Long,
    var ugs: Long,
    var hp: Int,
    var timer: Long,
    var currentStation: SpaceStation?
) {
    constructor():this(
        -1,
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        null
    )

    constructor(spaceship: Spaceship) : this(
        spaceship.id,
        spaceship.name,
        spaceship.durability,
        spaceship.speed,
        spaceship.capacity,
        spaceship.ds,
        spaceship.eus,
        spaceship.ugs,
        spaceship.hp,
        spaceship.timer,
        spaceship.currentStation
    )
}