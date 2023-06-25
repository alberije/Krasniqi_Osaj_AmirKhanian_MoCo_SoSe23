package com.example.plantsaver

import androidx.lifecycle.ViewModel
import com.example.plantsaver.database.UserDao
import com.example.plantsaver.database.event.NameEvent

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