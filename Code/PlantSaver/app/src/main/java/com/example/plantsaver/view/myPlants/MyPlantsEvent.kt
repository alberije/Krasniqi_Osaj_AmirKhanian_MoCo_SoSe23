package com.example.plantsaver.view.myPlants

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import com.example.plantsaver.database.model.Plant

sealed class MyPlantsEvent{

    data class ClickPlant(val plant: Plant, val context: Context):MyPlantsEvent()
    data class DeletePlant(val plant: Plant): MyPlantsEvent()
    data class AddPhoto(val imageNumber:Int, val bitmap: ImageBitmap): MyPlantsEvent()

}