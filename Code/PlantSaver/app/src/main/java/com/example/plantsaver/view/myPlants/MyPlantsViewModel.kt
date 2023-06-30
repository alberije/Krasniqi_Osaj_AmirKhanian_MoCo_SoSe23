package com.example.plantsaver.view.myPlants

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsaver.database.AppDatabase
import com.example.plantsaver.database.Repository
import com.example.plantsaver.database.model.Plant
import com.example.plantsaver.database.model.PlantFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyPlantsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getRepository(AppDatabase.getDatabase(application),application)

    // ui state
    val name = mutableStateOf("")
    val plantList = mutableStateOf(listOf<Plant>())
    val plantThumbnailList = mutableStateOf(listOf<ImageBitmap?>())

    // ui state my plants out
    val currentPlant = mutableStateOf<Plant?>(null)
    val currentPlantFamily = mutableStateOf<PlantFamily?>(null)
    val currentPlantPhotos = mutableStateOf<List<ImageBitmap>>(listOf())



    private var getCurrentUserJob: Job? = null
    private var getPlantListJob: Job? = null
    private var getPlantThumbnailsJob: Job? = null

   //ui event state
 private val _eventFlow = MutableSharedFlow<UiEvent>()
   val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val massage: String) : UiEvent()
        object NavigatePlant : UiEvent()
        object NavigateUp : UiEvent()
    }

    init {
        observeCurrentUser()
        observePlantList()
        observePlantThumbnails()
        viewModelScope.launch {

            repository.loadAllPlantFamilies()
        }
    }

    // immer wenn ein neuer user im repository gespeichert wird dann reagiert diese funktion automatisch darauf
    private fun observeCurrentUser() {
        getCurrentUserJob?.cancel()
        getCurrentUserJob = repository.currentUserFlow
            .onEach {
                if(it != null) {
                    name.value = it.username
                    // pflanzen von diesem user laden
                    repository.loadPlantsForUserName(it.username)
                }
            }
            .launchIn(viewModelScope)
    }

    // wenn liste der pflanzen sich im repository verändert weil zB eine pflanze hinzugefügt wurde dann wird das hier
    // automatisch observed und die neuen pflanzen angezeigt
    private fun observePlantList() {
        getPlantListJob?.cancel()
        getPlantListJob = repository.plantListFlow
            .onEach {
                plantList.value = it
            }
            .launchIn(viewModelScope)
    }
//
//    fun addPlantButton(){
//
//    }
//
//    fun onEvent(event: MyPlantsEvent) {
//        when (event) {
//            MyPlantsEvent.AddPlantButton -> addPlantButton()
//        }
//    }


    private fun observePlantThumbnails()  {
        getPlantThumbnailsJob?.cancel()
        getPlantThumbnailsJob = repository.plantThumbnailsFlow
            .onEach {
                plantThumbnailList.value = it

            }
            .launchIn(viewModelScope)
    }

    private fun clickPlant(plant: Plant, context: Context) {
        currentPlant.value = plant
        currentPlantFamily.value = repository.plantFamilyListFlow.value.find { it.id == plant.plantFamilyId}
        viewModelScope.launch(Dispatchers.IO) {
            currentPlantPhotos.value = repository.getImages(plant.id, context)
        }
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigatePlant)
        }

    }


    private fun deletePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            // pflanze löschen
            repository.deletePlant(plant)
            //pflanze neu laden (inkl bilder)
            repository.loadPlantsForUserName(name.value)
            // navigate back
            _eventFlow.emit(UiEvent.NavigateUp)

        }
    }

    private fun addImageToCurrentPlant(imageNumber: Int, bitmap: ImageBitmap) {
        viewModelScope.launch {
            currentPlant.value?.let{repository.saveImage(it.id, imageNumber.toString(), bitmap)}
            currentPlantPhotos.value = currentPlantPhotos.value + bitmap

            // thumbnails neu laden
            repository.loadPlantThumbnails()

        }

    }

    fun onEvent(event: MyPlantsEvent) {
        when (event) {
            is MyPlantsEvent.ClickPlant -> clickPlant(event.plant, event.context)
            is MyPlantsEvent.DeletePlant -> deletePlant(event.plant)
            is MyPlantsEvent.AddPhoto -> addImageToCurrentPlant(event.imageNumber, event.bitmap)


        }


    }


}