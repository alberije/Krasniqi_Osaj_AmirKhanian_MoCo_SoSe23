package com.example.plantsaver

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>

    @Insert
    fun insertPlant(plant: Plant)

    @Update
    fun updatePlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)
}

