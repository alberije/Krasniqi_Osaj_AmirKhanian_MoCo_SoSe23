package com.example.plantsaver

import CreatePlantFamScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantsaver.view.addPlant.AddplantsScreen
import com.example.plantsaver.view.addPlant.CarePlanPage
import com.example.plantsaver.view.home.Homescreen
import com.example.plantsaver.view.myPlants.MyPlantsScreen
import com.example.plantsaver.ui.theme.PlantSaverTheme
import com.example.plantsaver.view.OpenerScreen
import com.example.plantsaver.view.addPlant.AddPlantViewModel
import com.example.plantsaver.view.home.HomeScreenViewModel
import com.example.plantsaver.view.myPlants.MyPlantsViewModel

class MainActivity : ComponentActivity() {

    // viewModels
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val myPlantsViewModel: MyPlantsViewModel by viewModels()
    private val addPlantViewModel: AddPlantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantSaverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE3E9E5)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        val navController = rememberNavController()


                        NavHost(navController = navController, startDestination = "openerscreen") {

                            composable("openerscreen") {
                                OpenerScreen(homeScreenViewModel, navController)
                            }
                            composable("homescreen") {
                                Homescreen(homeScreenViewModel, navController)
                            }

                            composable("myPlantsFragment") {
                                MyPlantsScreen(myPlantsViewModel, navController)
                            }
                            composable("AddPlantsFragment") {
                                AddplantsScreen(addPlantViewModel, navController)
                            }

                            composable("selectPlantFragment"){
                                PlantSelectionScreen(addPlantViewModel, navController)
                            }

                            composable("addplancareFragment") {
                                CarePlanPage(addPlantViewModel, navController)
                            }

                            composable("createyourownFragment") {
                                CreatePlantFamScreen(addPlantViewModel,navController)
                            }


                            composable("test"){

                            }
                        }
                    }
                }
            }
        }
    }
}


