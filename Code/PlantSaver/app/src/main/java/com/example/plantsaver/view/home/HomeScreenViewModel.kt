package com.example.plantsaver.view.home

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsaver.database.AppDatabase
import com.example.plantsaver.database.Repository
import com.example.plantsaver.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getRepository(AppDatabase.getDatabase(application))

    // ui state
    val name = mutableStateOf("")

    // ui event state
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // geben informationen vom viewmodel zur view
    sealed class UiEvent {
        data class ShowToast(val massage: String) : UiEvent()
        object NavigateMyPlants : UiEvent()
        object NavigateHomescreen : UiEvent()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.initDbIfEmpty()
        }

    }

    // opener
    suspend fun checkIfUserExists(){
        val users = repository.getUsers()

        if(users.isEmpty()){
            // es gibt noch keinen user -> zum homescreen
            _eventFlow.emit(UiEvent.NavigateHomescreen)

        } else {
            // es gibt schon einen user -> laden und zum myplants screen
            val user = users.first()
            repository.setCurrentUser(user)
            _eventFlow.emit(UiEvent.NavigateMyPlants)
        }
    }


    // homescreen
    fun getStarted() {
        // check name input
        if (name.value == "") {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast("Please enter your name first"))
            }
            return
        }

        // es wurde ein name eingegeben
        // identify user or create new user
        viewModelScope.launch(Dispatchers.IO) {

            // create new user
            val newUser = User(name.value)
            // insert into db
            repository.insertUser(newUser)
            // set current user in repo
            repository.setCurrentUser(newUser)
            //_eventFlow.emit(UiEvent.ShowToast("Welcome ${newUser.username}"))

//            }
            // navigate to my plants screen
            _eventFlow.emit(UiEvent.NavigateMyPlants)
        }
    }

    fun nameFieldChanged(value: String) {
        // update text field state
        name.value = value
    }


    // diese funktion wird aufgerufen, wenn der user iwas in der view macht. zB einen button klicken
    fun onEvent(event: HomeScreenEvent) {
        when (event) {

            HomeScreenEvent.GetStartedButton -> getStarted()
            is HomeScreenEvent.NameFieldChanged -> nameFieldChanged(event.value)

        }
    }
}
