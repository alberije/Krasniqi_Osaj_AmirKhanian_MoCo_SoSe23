package com.example.plantsaver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsaver.database.model.Plant


@Dao
interface PlantDao {

    @Query("SELECT * FROM plant WHERE userName LIKE :userName")
    suspend fun getPlantsForUserName(username:String): List<Plant>

    @Insert
    suspend fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)
}
