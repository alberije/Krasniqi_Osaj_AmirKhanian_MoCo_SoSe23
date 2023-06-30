package com.example.plantsaver.view.addPlant

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
import com.example.plantsaver.view.addPlant.AddPlantEvent.AddPlantButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddPlantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getRepository(AppDatabase.getDatabase(application),application)

    val newPlant = mutableStateOf(Plant("", 0, ""))

    // ui state add plant screen
    val plantName = mutableStateOf("")
    val selectedPlantFamily = mutableStateOf<PlantFamily?>(null)

    // zum anzeigen der drei bilder
    val image1 = mutableStateOf<ImageBitmap?>(null)
    val image2 = mutableStateOf<ImageBitmap?>(null)
    val image3 = mutableStateOf<ImageBitmap?>(null)

    // ui state add plant family screen
    val createPlantFam = mutableStateOf(PlantFamily("","","",""))
    // ui state add care plan screen
    var mondayChecked = mutableStateOf(false)
    var tuesdayChecked = mutableStateOf(false)
    var wednesdayChecked = mutableStateOf(false)
    var thursdayChecked = mutableStateOf(false)
    var fridayChecked = mutableStateOf(false)
    var saturdayChecked = mutableStateOf(false)
    var sundayChecked = mutableStateOf(false)


    // data state
    val userName = mutableStateOf("")
    val plantFamilyList = mutableStateOf(listOf<PlantFamily>())

    private var getCurrentUserJob: Job? = null
    private var getPlantFamilyListJob: Job? = null


    // ui event state
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val massage: String) : UiEvent()
        object NavigateUp: UiEvent()
    }

    init {
        observeCurrentUser()
        observePlantFamilyList()
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadAllPlantFamilies()
        }
    }



    private fun observeCurrentUser() {
        getCurrentUserJob?.cancel()
        getCurrentUserJob = repository.currentUserFlow
            .onEach {
                if (it != null)
                    userName.value = it.username
            }
            .launchIn(viewModelScope)
    }

    private fun observePlantFamilyList() {
        getPlantFamilyListJob?.cancel()
        getPlantFamilyListJob = repository.plantFamilyListFlow
            .onEach {
                plantFamilyList.value = it
            }
            .launchIn(viewModelScope)
    }


    // add plant screen
    fun plantNameFieldChanged(value: String) {
        // update text field state
        plantName.value = value
    }

    fun addPlantButton(context: Context) {

        if (plantName.value.isEmpty()) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast("Please enter the name of your plant first"))
            }
            return
        }
        if (selectedPlantFamily.value == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast("Please select or add a plant family first"))
            }
            return
        }
        if (repository.currentUserFlow.value == null) {

            return
        }


        // pflanze zu db hinzufügen
        viewModelScope.launch(Dispatchers.IO) {
            // hier noch name, family id und username für die neue pflanze setzen.
            // der care plan wurde schon in newplant gespeichert
            val newPlantId = repository.insertPlant(
                newPlant.value.copy(
                    name = plantName.value,
                    plantFamilyId = selectedPlantFamily.value!!.id,
                    userName = repository.currentUserFlow.value!!.username
                )
            )
            // fotos speichern
            if(image1.value != null)
                repository.saveImage(newPlantId, "1", image1.value!!)
            if(image2.value != null)
                repository.saveImage(newPlantId, "2", image2.value!!)
            if(image3.value != null)
                repository.saveImage(newPlantId, "3", image3.value!!)

            // plants neu laden inkl thumbnails
            repository.loadPlantsForUserName(userName.value)

            // alle eingaben om add plants screen zurücksetzen
            resetAddPlantScreen()
            // das objekt für die neue pflanze zurücksetzen
            newPlant.value = Plant("", 0, "")
            // den ausgewählten care plan zurücksetzen
            resetCarePlanScreen()

            _eventFlow.emit(UiEvent.NavigateUp)
        }

    }

    private fun resetAddPlantScreen(){
        plantName.value = ""
        selectedPlantFamily.value = null
        image1.value = null
        image2.value = null
        image3.value = null
    }

    private fun selectedPlantFamilyChanged(plantFamily: PlantFamily){
        selectedPlantFamily.value = plantFamily
    }

    private fun plantFamilyFieldChanged(value: String){
    }

    private fun addPhoto(value: Int, bitmap: ImageBitmap){
        if(value == 0)
            return
        when(value){
            1 -> {
                image1.value = bitmap
            }
            2 -> {
                image2.value = bitmap
            }
            else -> {
                image3.value = bitmap
            }
        }

    }

    // CreatePlantFam screen
    private fun addPlantFamily(){
        if(createPlantFam.value.name.isEmpty() || createPlantFam.value.description.isEmpty() ||
            createPlantFam.value.location.isEmpty() || createPlantFam.value.care.isEmpty()){
            // es wurden nicht alle felder ausgefüllt!
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast("Please fill in every Field!"))
            }
            // weil nicht alle felder ausgefüllt wurden können wir keine family hinzufügen und gehen aus der funktion raus
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            // neue plant family in die db speichern
            val newId = repository.insertPlantFamily(createPlantFam.value)
            // die plant families neu laden damit die neue auch im dropdown angezeigt wird
            repository.loadAllPlantFamilies()
            // die neu erstellte plant family direkt auswählen im add plant screen
            // wichtig die neu gesetzte id von der db muss hier rein!
            selectedPlantFamilyChanged(createPlantFam.value.copy(id = newId))
            // die eingetippten werte zurück setzen damit die nciht mehr da stehen wenn man nochmal plant family hinzufügen will
            resetCreatePlantFamScreen()
            // zu add plant screen zurück navigieren
            _eventFlow.emit(UiEvent.NavigateUp)
        }
    }

    private fun resetCreatePlantFamScreen(){
        createPlantFam.value = createPlantFam.value.copy(name = "", description = "", location = "", care = "")
    }

    private fun plantFamNameChanged(value: String){
        createPlantFam.value = createPlantFam.value.copy(name= value)
    }

    private fun plantFamDescriptionChanged(value: String){
        createPlantFam.value = createPlantFam.value.copy(description = value)
    }

    private fun plantFamLocationChanged(value: String){
        createPlantFam.value = createPlantFam.value.copy(location = value)
    }

    private fun plantFamCareChanged(value: String){
        createPlantFam.value = createPlantFam.value.copy(care= value)
    }


    // add care plan
    fun dayChecked(value: Boolean, day: Int){
        // montag = 1 ... sonntag = 7
        when(day){
            1 -> {
                mondayChecked.value = value
            }
            2 -> {
                tuesdayChecked.value = value
            }
            3 -> {
                wednesdayChecked.value = value
            }
            4 -> {
                thursdayChecked.value = value
            }
            5 -> {
                fridayChecked.value = value
            }
            6 -> {
                saturdayChecked.value = value
            }
            else -> {
                sundayChecked.value = value
            }
        }
    }

    private fun saveCarePlan(){


        // die ausgewählten tage in newPlant reinspeichern, dann zurück navigieren
        newPlant.value = newPlant.value.copy(
            mon = mondayChecked.value,
            tue = tuesdayChecked.value,
            wed = wednesdayChecked.value,
            thu = thursdayChecked.value,
            fri = fridayChecked.value,
            sat = saturdayChecked.value,
            sun = sundayChecked.value
        )
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavigateUp)
        }
    }

    private fun resetCarePlanScreen(){
        mondayChecked.value = newPlant.value.mon
        tuesdayChecked.value = newPlant.value.tue
        wednesdayChecked.value = newPlant.value.wed
        thursdayChecked.value = newPlant.value.thu
        fridayChecked.value = newPlant.value.fri
        saturdayChecked.value = newPlant.value.sat
        sundayChecked.value = newPlant.value.sun
    }

    fun onEvent(event: AddPlantEvent) {
        when (event) {
            // add plant
            is AddPlantEvent.PlantNameFieldChanged -> plantNameFieldChanged(event.value)
            is AddPlantEvent.AddPlantButton -> addPlantButton(event.context)
            is AddPlantEvent.SelectedPlantFamilyChanged -> selectedPlantFamilyChanged(event.value)
            is AddPlantEvent.PlantFamilyFieldChanged -> plantFamilyFieldChanged(event.value)
            // create plant fam
            AddPlantEvent.AddPlantFamilyButton -> addPlantFamily()
            is AddPlantEvent.PlantFamNameChanged -> plantFamNameChanged(event.value)
            is AddPlantEvent.PlantFamDescriptionChanged -> plantFamDescriptionChanged(event.value)
            is AddPlantEvent.PlantFamLocationChanged -> plantFamLocationChanged(event.value)
            is AddPlantEvent.PlantFamCareChanged -> plantFamCareChanged(event.value)
            // add care plan events
            is AddPlantEvent.DayChecked -> dayChecked(event.value, event.day)
            AddPlantEvent.SaveCarePlan -> saveCarePlan()
            AddPlantEvent.ResetCarePlanScreen -> resetCarePlanScreen()
        }
    }
}