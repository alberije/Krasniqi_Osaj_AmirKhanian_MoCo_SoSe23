package com.example.plantsaver.ui.theme.database

sealed interface NameEvent {

    object SaveName: NameEvent
    data class setName(val name: String): NameEvent



}