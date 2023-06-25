package com.example.plantsaver.view.addPlant

import com.example.plantsaver.database.model.PlantFamily

sealed class AddPlantEvent{

    // add plant
    data class PlantNameFieldChanged(val value: String): AddPlantEvent()
    data class PlantFamilyFieldChanged(val value: String): AddPlantEvent()
    data class SelectedPlantFamilyChanged(val value: PlantFamily): AddPlantEvent()
    object AddPlantButton: AddPlantEvent()
    // create plant fam
    object AddPlantFamilyButton: AddPlantEvent()
    data class PlantFamNameChanged(val value: String): AddPlantEvent()
    data class PlantFamDescriptionChanged(val value: String): AddPlantEvent()
    data class PlantFamLocationChanged(val value: String): AddPlantEvent()
    data class PlantFamCareChanged(val value: String): AddPlantEvent()
    // add care plan event
    data class DayChecked(val value: Boolean, val day: Int): AddPlantEvent()
    object SaveCarePlan: AddPlantEvent()
    object ResetCarePlanScreen: AddPlantEvent()

}