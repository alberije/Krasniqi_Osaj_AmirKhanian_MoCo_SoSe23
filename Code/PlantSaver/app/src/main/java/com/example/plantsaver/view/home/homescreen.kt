package com.example.plantsaver.view.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.R
import com.example.plantsaver.ui.theme.PlantSaverTheme

class homescreen : ComponentActivity() {
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
fun Homescreen(navController: NavHostController,nameList: MutableList<Name>) {
    val name = remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize() .background(Color(0xFFE3E9E5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Hello",
            color = Color(0xFF2d681c),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 35.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)

        ) {
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it},
                label = { Text(text = "Enter your name") },
                singleLine = true,
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        Button(onClick = {
            nameList.add(Name(name.value))
            navController.navigate("myPlantsFragment") },
            colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(170.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(18.dp)))
            {
            Text(text = "Get started")
        }
    }

}

data class Name(val name: String)
