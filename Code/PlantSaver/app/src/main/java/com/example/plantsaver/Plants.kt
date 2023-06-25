package com.example.plantsaver

import androidx.room.Entity

@Entity(tableName = "plants")
data class Plant(val name: String,
                 val description: String,
                 val location: String,
                 val care: String) // Model-Klasse, die die Daten für die Zimmerpflanzen enthält

