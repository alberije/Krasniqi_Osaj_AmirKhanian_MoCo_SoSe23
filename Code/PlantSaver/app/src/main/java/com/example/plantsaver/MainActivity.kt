package com.example.plantsaver

import CreatePlantFamScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantsaver.ui.theme.PlantSaverTheme

class MainActivity : ComponentActivity() {
    private val plantList = mutableStateListOf<Plant>()
    private val nameList = mutableStateListOf<Name>()
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
                        val plantViewModel = PlantViewModel()

                        NavHost(navController = navController, startDestination = "openerscreen") {

                            composable("openerscreen") {
                                OpenerScreen(navController)
                            }
                            composable("homescreen") {
                                Homescreen(navController)
                            }

                            composable("myPlantsFragment") {
                                MyPlantsScreen(navController, plantList)
                            }
                            composable("AddPlantsFragment") {
                                AddplantsScreennew(navController, plantList,plantViewModel)
                            }

                            composable("selectPlantFragment"){
                                PlantSelectionScreen(navController,plantViewModel)
                            }

                            composable("addplancareFragment") {
                                CarePlanPage(navController)
                            }

                            composable("createyourownFragment") {
                                CreatePlantFamScreen(navController, plantList)
                            }


                            composable("test"){
                                CarePlanPage(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


