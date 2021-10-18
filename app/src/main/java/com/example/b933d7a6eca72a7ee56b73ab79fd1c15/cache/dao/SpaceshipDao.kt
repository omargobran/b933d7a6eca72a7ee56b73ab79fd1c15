package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao

import androidx.room.*
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.model.SpaceshipEntity

@Dao
interface SpaceshipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spaceshipEntity: SpaceshipEntity): Long

    @Update
    suspend fun update(spaceshipEntity: SpaceshipEntity): Int

    @Update
    fun updateSync(spaceshipEntity: SpaceshipEntity): Int

    @Delete
    suspend fun delete(spaceshipEntity: SpaceshipEntity): Int

    @Query("SELECT * FROM spaceship")
    suspend fun getSpaceship(): SpaceshipEntity

}