package com.example.plantsaver.ui.theme.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>

    @Insert
    fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)
}

