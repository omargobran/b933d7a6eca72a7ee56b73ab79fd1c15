package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation

@Entity(tableName = "spaceship")
data class SpaceshipEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "durability")
    var durability: Long,

    @ColumnInfo(name = "speed")
    var speed: Long,

    @ColumnInfo(name = "capacity")
    var capacity: Long,

    @ColumnInfo(name = "ds")
    var ds: Long,

    @ColumnInfo(name = "eus")
    var eus: Long,

    @ColumnInfo(name = "ugs")
    var ugs: Long,

    @ColumnInfo(name = "hp")
    var hp: Int,

    @ColumnInfo(name = "timer")
    var timer: Long,

    @ColumnInfo(name = "current_station")
    var currentStation: SpaceStation?
)