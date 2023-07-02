package com.example.plantsaver


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantsaver.view.home.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OpenerScreen(viewModel: HomeScreenViewModel, navController: NavHostController)   {

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier.size(200.dp)
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }
                is HomeScreenViewModel.UiEvent.NavigateMyPlants -> {
                    navController.navigate("myPlantsFragment")
                }

                is HomeScreenViewModel.UiEvent.NavigateHomescreen -> {
                    navController.navigate("homescreen")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(2000) //2 skunden warten
        // schauen ob es einen user gibt, wenn ja direkt zu my plants, sonst zu homescreen
        viewModel.checkIfUserExists()
    }
}