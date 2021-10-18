package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model

import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sqrt

class Distance(coordinate1: Coordinate, coordinate2: Coordinate) {
    constructor() : this(Coordinate(0.0, 0.0), Coordinate(0.0, 0.0))

    val value: Long = calculateDistance(coordinate1, coordinate2).roundToLong()

    private fun calculateDistance(coordinate1: Coordinate, coordinate2: Coordinate): Double {
        val xDiff = (coordinate2.x - coordinate1.x)
        val yDiff = (coordinate2.y - coordinate1.y)
        return sqrt(xDiff.pow(2.0) + yDiff.pow(2.0))
    }
}