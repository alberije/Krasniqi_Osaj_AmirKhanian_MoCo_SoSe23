package com.example.plantsaver

sealed interface NameEvent {

    object SaveName: NameEvent
    data class setName(val name: String): NameEvent



}