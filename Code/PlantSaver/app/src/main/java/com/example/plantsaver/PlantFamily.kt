package com.example.plantsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    val plantViewModel = PlantViewModel()
                    PlantSelectionScreen(plantViewModel)
                }
            }
        }
    }
}

@Composable
fun SearchField() {
    TODO("Not yet implemented")
}

@Composable
fun PlantSelectionScreen(plantViewModel: PlantViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        SearchField()

        Spacer(modifier = Modifier.height(40.dp))

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
                .padding(bottom = 16.dp).width(180.dp)
                .height(40.dp).clip(RoundedCornerShape(18.dp)),
            colors = ButtonDefaults.buttonColors(Color(0xFF55B663))
        ) {
            Text(text = "Create your own")
        }


    }
    val plantList = plantViewModel.getAllPlants()
    for (plant in plantList) {


    }

    @Composable
    fun SearchField() {
        TODO("Not yet implemented")
    }
}

