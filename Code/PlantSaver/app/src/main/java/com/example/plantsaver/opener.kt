package com.example.plantsaver

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantsaver.R
import com.example.plantsaver.ui.theme.PlantSaverTheme
import com.example.plantsaver.view.home.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class opener : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantSaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE3E9E5)
                ) {

            }
        }
    }
}
}

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