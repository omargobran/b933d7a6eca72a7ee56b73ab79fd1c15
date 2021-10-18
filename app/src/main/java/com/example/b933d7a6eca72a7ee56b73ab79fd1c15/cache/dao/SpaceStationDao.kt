package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao

import androidx.room.*
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceStationEntity

@Dao
interface SpaceStationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpaceStation(spaceStationEntity: SpaceStationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpaceStations(spaceStationEntities: List<SpaceStationEntity>): List<Long>

    @Update
    suspend fun update(spaceStationEntity: SpaceStationEntity): Int

    @Update
    fun updateSync(spaceStationEntity: SpaceStationEntity): Int

    @Delete
    suspend fun delete(spaceStationEntity: SpaceStationEntity): Int

    @Query("SELECT * FROM space_station ORDER BY id ASC")
    suspend fun getSpaceStations(): List<SpaceStationEntity>

    @Query("SELECT * FROM space_station WHERE favorite LIKE 1 ORDER BY id ASC")
    suspend fun getFavoriteSpaceStations(): List<SpaceStationEntity>

    @Query("SELECT * FROM space_station WHERE name LIKE :name")
    suspend fun getSpaceStationByName(name: String): SpaceStationEntity

    @Query("SELECT * FROM space_station WHERE id LIKE :id")
    suspend fun getSpaceStationById(id: Int): SpaceStationEntity

    @Query("DELETE FROM space_station")
    suspend fun deleteTable()

}