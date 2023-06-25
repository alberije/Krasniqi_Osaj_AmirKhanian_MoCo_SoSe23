package com.example.plantsaver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsaver.database.model.PlantFamily

@Dao
interface PlantFamilyDao {
    @Query("SELECT * FROM plant_family")
    suspend fun getAllPlantFamilies(): List<PlantFamily>

    @Insert
    fun insertPlantFamily(plantFamily: PlantFamily): Long

    @Insert
    fun insertAll(plantFamilies: List<PlantFamily>)

    @Delete
    fun deletePlantFamily(plantFamily: PlantFamily)

    // gibt zurück ob schon plant families in der db stehen
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM plant_family) THEN 0 ELSE 1 END")
    suspend fun isEmpty(): Boolean
}