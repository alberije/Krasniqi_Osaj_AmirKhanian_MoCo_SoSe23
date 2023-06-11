package com.example.plantsaver

sealed interface PlantEvent {

    object SavePlant: PlantEvent
    data class setPlantName(val name: String): PlantEvent
    data class setDescription(val description: String): PlantEvent
    data class setLocation(val location: String): PlantEvent
    data class setCare(val care: String): PlantEvent
    data class deletePlant(val plant: Plant): PlantEvent




}