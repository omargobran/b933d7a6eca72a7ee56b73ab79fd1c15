package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model

import androidx.room.*
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter.CoordinateConverter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Coordinate

@Entity(tableName = "space_station")
data class SpaceStationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "coordinate")
    var coordinate: Coordinate,

    @ColumnInfo(name = "capacity")
    var capacity: Long,

    @ColumnInfo(name = "stock")
    var stock: Long,

    @ColumnInfo(name = "need")
    var need: Long,

    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean,

    @ColumnInfo(name = "visited")
    val isVisited: Boolean
)