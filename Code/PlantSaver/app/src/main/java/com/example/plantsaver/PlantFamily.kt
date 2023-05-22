package com.example.plantsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.plantsaver.ui.theme.PlantSaverTheme

class PlantFamily : ComponentActivity() {
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
fun PlantSelectionScreen(navController: NavHostController, plantViewModel: PlantViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigate("AddPlantsFragment") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Select Plant Family",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF2d681c),
            modifier = Modifier.padding(vertical = 30.dp)
        )

        Spacer(modifier = Modifier.height(90.dp))

        dropDownMenu(plantViewModel)

        Spacer(modifier = Modifier.height(140.dp))

        Text(
            text = "Can't find?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(180.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(18.dp)),
            colors = ButtonDefaults.buttonColors(Color(0xFF55B663))
        ) {
            Text(text = "Create your own")
        }


    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(plantViewModel: PlantViewModel){
    var expanded by remember { mutableStateOf(false)}
    val plantList = plantViewModel.getAllPlants()
    var selectedItem by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

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
        .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
        plantList.forEach{plant->
            androidx.compose.material.DropdownMenuItem(onClick = {  selectedItem = plant.name
                expanded =false }) {
                Text(text = plant.name)
            }


        }
    }

}



}

