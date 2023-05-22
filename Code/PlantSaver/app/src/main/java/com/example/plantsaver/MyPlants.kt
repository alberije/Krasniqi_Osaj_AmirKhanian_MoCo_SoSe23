package com.example.plantsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.plantsaver.ui.theme.PlantSaverTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*


class MyPlants : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantSaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}


@Composable
fun MyPlantsScreen(navController: NavHostController, plantList: List<Plant>) {
    val text: String? = navController.previousBackStackEntry
        ?.arguments?.getString("enteredText")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                //verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("homescreen") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }

Row() {
    Text(
        text = "My plants",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2d681c),
        modifier = Modifier.padding(vertical = 16.dp)
    )
}


            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        text = "hello",
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.Black
                    )
                    IconAdd(navController = navController)
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                plantList.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowItems.forEach { plant ->
                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(150.dp)
                                    .shadow(4.dp, RoundedCornerShape(9.dp))
                                    .background(Color.White, RoundedCornerShape(9.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    //verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = plant.name)
                                    Text(text = plant.description)
                                    Text(text = plant.location)
                                    Text(text = plant.care)
                                }
                            }
                        }
                    }
                }
            }



        }

    }
}





    @Composable
    fun IconAdd(navController: NavHostController) {
        FloatingActionButton(
            onClick = { navController.navigate("addPlantsfragment") },
            contentColor = Color.White,
            shape = CircleShape,
            containerColor = Color(0xFF55B663)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add "
            )
        }
    }

    @Composable
    fun IconEdit() {
        FloatingActionButton(
            onClick = {},
            contentColor = Color.White,
            shape = CircleShape,
            containerColor = Color(0xFF55B663)
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Edit button"
            )
        }
    }

    @Composable
    fun IconName() {
        FloatingActionButton(
            onClick = {},
            contentColor = Color.Black,
            shape = CircleShape,
            containerColor = Color(0xFFD9D9D9)
        ) {
            Text(
                text = "MA",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

