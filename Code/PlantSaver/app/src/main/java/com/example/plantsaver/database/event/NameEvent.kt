package com.example.plantsaver.database.event


sealed interface NameEvent {

    object SaveName: NameEvent
    data class setName(val name: String): NameEvent



}