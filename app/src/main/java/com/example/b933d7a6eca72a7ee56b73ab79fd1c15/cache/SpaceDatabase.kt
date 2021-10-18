package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceStationDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceshipDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceStationEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceshipEntity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter.CoordinateConverter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter.SpaceStationConverter

@Database(entities = [SpaceStationEntity::class, SpaceshipEntity::class], version = 1)
@TypeConverters(CoordinateConverter::class, SpaceStationConverter::class)
abstract class SpaceDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "space_db"
    }

    abstract fun spaceStationDao(): SpaceStationDao
    abstract fun spaceshipDao(): SpaceshipDao
}