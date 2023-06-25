package com.example.plantsaver.view.home

// geben informationen von der view zum viewmodel

sealed class HomeScreenEvent{

    data class NameFieldChanged(val value: String): HomeScreenEvent()
    object GetStartedButton: HomeScreenEvent()

}