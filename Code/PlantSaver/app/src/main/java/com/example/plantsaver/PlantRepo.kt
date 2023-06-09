package com.example.plantsaver

class PlantRepo(private val plantDao: PlantDao){
suspend fun getAllplants(): List<Plant> {
    return plantDao.getAllPlants()

}

    suspend fun insertPlan(plant: Plant) {
        plantDao.insertPlant(plant)
    }

}

