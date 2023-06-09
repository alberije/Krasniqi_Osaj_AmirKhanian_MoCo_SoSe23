package com.example.plantsaver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant)

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>


}