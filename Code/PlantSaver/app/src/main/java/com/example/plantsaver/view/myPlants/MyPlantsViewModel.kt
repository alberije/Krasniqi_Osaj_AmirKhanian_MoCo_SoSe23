package com.example.plantsaver.view.myPlants

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsaver.database.AppDatabase
import com.example.plantsaver.database.Repository
import com.example.plantsaver.database.model.Plant
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyPlantsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getRepository(AppDatabase.getDatabase(application))

    // ui state
    val name = mutableStateOf("")
    val plantList = mutableStateOf(listOf<Plant>())

    private var getCurrentUserJob: Job? = null
    private var getPlantListJob: Job? = null
//
//    // ui event state
//    private val _eventFlow = MutableSharedFlow<UiEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()
//
//    sealed class UiEvent {
//        data class ShowToast(val massage: String) : UiEvent()
//        object NavigateAddPlant : UiEvent()
//    }

    init {
        observeCurrentUser()
        observePlantList()
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
}