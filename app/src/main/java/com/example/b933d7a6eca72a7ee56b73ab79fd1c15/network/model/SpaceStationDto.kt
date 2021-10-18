package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpaceStationDto(
    @SerializedName("name")
    var name: String,

    @SerializedName("coordinateX")
    var coordinateX: Double,

    @SerializedName("coordinateY")
    var coordinateY: Double,

    @SerializedName("capacity")
    var capacity: Long,

    @SerializedName("stock")
    var stock: Long,

    @SerializedName("need")
    var need: Long
) : Serializable