package com.example.plantsaver

import androidx.lifecycle.ViewModel
import com.example.plantsaver.database.PlantDao

class PlantViewmodel(
    private val dao: PlantDao
    ):ViewModel() {

}