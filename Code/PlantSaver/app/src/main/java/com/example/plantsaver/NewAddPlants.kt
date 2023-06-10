package com.example.plantsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.plantsaver.ui.theme.PlantSaverTheme

class NewAddPlants : ComponentActivity() {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddplantsScreennew(navController: NavHostController, plantList: MutableList<Plant>, plantViewModel: PlantViewModel) {
    val plant = remember{ mutableStateOf(Plant("","","","")) }

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigate("myPlantsFragment")}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }


            Text(
                text = "Add plants",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2d681c),
                modifier = Modifier
                    .padding(vertical = 30.dp)

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Plant Name",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    TextField(
                                        value = plant.value.name,
                                        onValueChange = { plant.value = plant.value.copy(name= it) },
                                        label = { Text("Enter your plant Name") },
                                        modifier = Modifier.width(230.dp)

                                    )

                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Plant Family",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    dropDownMenu(plantViewModel)
                                }


                                Column(modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    Row() {
                                        Text(
                                            text = "can't find?",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }


                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row() {

                                        Button(
                                            onClick = { navController.navigate("createyourownFragment")},
                                            colors = ButtonDefaults.buttonColors(Color(0xFF55B663)),
                                            modifier = Modifier
                                                .width(150.dp)
                                                .height(30.dp)
                                                .clip(RoundedCornerShape(18.dp))
                                        ) {
                                            Text(text = "create your own")
                                        }
                                    }


                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row() {

                                        Button(
                                            onClick = { navController.navigate("addplancareFragment")},
                                            colors = ButtonDefaults.buttonColors(Color(0xFF55B663)),
                                            modifier = Modifier
                                                .width(150.dp)
                                                .height(30.dp)
                                                .clip(RoundedCornerShape(18.dp))
                                        ) {
                                            Text(text = "+ add care plan")
                                        }
                                    }


                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "timeline",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                                Spacer(modifier = Modifier.height(40.dp))



                                Column() {
                                    Row() {
                                        Box(
                                            modifier = Modifier
                                                .width(90.dp)
                                                .height(90.dp)
                                                .shadow(4.dp, RoundedCornerShape(9.dp))
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(9.dp)
                                                )
                                                .clickable() {},
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = "Week 1")
                                        }
                                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                        Row() {
                                            Box(
                                                modifier = Modifier
                                                    .width(90.dp)
                                                    .height(90.dp)
                                                    .shadow(4.dp, RoundedCornerShape(9.dp))
                                                    .background(
                                                        Color.White,
                                                        RoundedCornerShape(9.dp)
                                                    )
                                                    .clickable() {},
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(text = "Week 2")
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                            Row() {
                                                Box(
                                                    modifier = Modifier
                                                        .width(90.dp)
                                                        .height(90.dp)
                                                        .shadow(4.dp, RoundedCornerShape(9.dp))
                                                        .background(
                                                            Color.White,
                                                            RoundedCornerShape(9.dp)
                                                        )
                                                        .clickable() {},
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(text = "Week 3")
                                                }
                                            }




                                        }

                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 40.dp))

                                Row() {
                                    Box() {
                                        Button(
                                            onClick = {
                                                plantList.add(plant.value)
                                                plant.value = Plant("","","","")
                                            },
                                            colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(40.dp)
                                                .clip(RoundedCornerShape(18.dp)))
                                        {
                                            Text(text = "add")
                                        }
                                    }
                                }
                            }
                        }

                    }

                }

            }
        }
    }


}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(plantViewModel: PlantViewModel){
    var expanded by remember { mutableStateOf(false)} //speichert der aktuelle zustand des DropdownMenu
    val plantList = plantViewModel.getAllPlants() //liste von plants abrufen und diese liste wird in plantlist gespeichert
    var selectedItem by remember { mutableStateOf("") } //speichert die ausgewählten Wert im DropdownMenu

    var textFieldSize by remember { mutableStateOf(Size.Zero) } //speichert Größe des Textfeldes

    //Icons
    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier =  Modifier.padding(20.dp)) {

        OutlinedTextField(
            value = selectedItem,
            onValueChange = {selectedItem = it},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() },
            label = { Text(text = "search for plant family ")},
            trailingIcon = {
                Icon(icon,"",Modifier.clickable{expanded =!expanded})
            }
        )


        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})   //with(LocalDensity.current): ist eine Funktion, die den Zugriff auf das aktuelle Density-Objekt in Compose ermöglicht. Density wird verwendet, um metrische Werte in DPs (Density-independent Pixels) zu konvertieren.
        ) {
            plantList.forEach{plant->
                DropdownMenuItem(onClick = {  selectedItem = plant.name
                    expanded =false }) {
                    Text(text = plant.name)
                }


            }
        }

    }



}



