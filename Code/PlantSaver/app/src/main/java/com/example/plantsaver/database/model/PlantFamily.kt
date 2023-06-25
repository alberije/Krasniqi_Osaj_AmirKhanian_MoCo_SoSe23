package com.example.plantsaver.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_family")
data class PlantFamily(
    val name: String,
    val description: String,
    val location: String,
    val care: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) // Model-Klasse, die die Daten für die Zimmerpflanzen enthält