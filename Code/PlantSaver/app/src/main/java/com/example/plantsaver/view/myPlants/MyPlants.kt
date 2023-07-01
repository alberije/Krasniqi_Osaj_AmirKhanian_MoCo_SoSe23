package com.example.plantsaver.view.myPlants


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.database.model.Plant
import com.example.plantsaver.ui.theme.PlantSaverTheme
import com.example.plantsaver.view.ImageBox
import kotlinx.coroutines.flow.collectLatest


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
fun MyPlantsScreen(viewModel: MyPlantsViewModel, navController: NavHostController) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MyPlantsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }

                is MyPlantsViewModel.UiEvent.NavigatePlant -> {
                    navController.navigate("myplantsout")
                }

                MyPlantsViewModel.UiEvent.NavigateUp -> {
                    // hier ist nichts zu tun
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    //verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                }
            }
            item {
                Row() {
                    val firstName = viewModel.name.value
                    val myPlantsText = if (firstName.isNotBlank()) {
                        "$firstName's Plants"
                    } else {
                        "My plants"
                    }


                    Text(
                        text = myPlantsText,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2d681c),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconAdd(navController = navController)
                    }
                }
            }

            item {
                PlantGrid(
                    viewModel.plantList.value,
                    viewModel.plantThumbnailList.value,
                    viewModel::onEvent
                )
            }
        }
    }
}

@Composable
fun PlantGrid(plants: List<Plant>, thumbnails: List<ImageBitmap?>, onEvent: (MyPlantsEvent) -> Unit){
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        plants.chunked(2).forEachIndexed { rowIndex, rowItems ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEachIndexed() { index, plant ->
                    ImageBox(
                        modifier = Modifier
                            .size(150.dp)
                            .clickable { onEvent(MyPlantsEvent.ClickPlant(plant, context)) },
                        thumbnails.elementAtOrNull(index + 2 * rowIndex),
                        plant.name,
                        showText = true
                    )
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




