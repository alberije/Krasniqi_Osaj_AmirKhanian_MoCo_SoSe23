package com.example.plantsaver

import androidx.lifecycle.ViewModel

class NameViewModel(
    private val dao: UserDao
): ViewModel() {

fun onEvent(event: NameEvent) {

when(event) {
    NameEvent.SaveName -> TODO()
    is NameEvent.setName -> TODO()
}


}

}