package com.example.plantsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.ui.theme.PlantSaverTheme

class example : ComponentActivity() {
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
fun CarePlanPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.navigate("AddPlantsFragment")}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }

        Text(
            text = "Add Care Plant",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2d681c),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 30.dp)
        )


        var mondayChecked by remember { mutableStateOf(false) }
        var tuesdayChecked by remember { mutableStateOf(false) }
        var wednesdayChecked by remember { mutableStateOf(false) }
        var thursdayChecked by remember { mutableStateOf(false) }
        var fridayChecked by remember { mutableStateOf(false) }
        var saturdayChecked by remember { mutableStateOf(false) }
        var sundayChecked by remember { mutableStateOf(false) }

        WeekDay("Monday", mondayChecked) { mondayChecked = it }
        WeekDay("Tuesday", tuesdayChecked) { tuesdayChecked = it }
        WeekDay("Wednesday", wednesdayChecked) { wednesdayChecked = it }
        WeekDay("Thursday", thursdayChecked) { thursdayChecked = it }
        WeekDay("Friday", fridayChecked) { fridayChecked = it }
        WeekDay("Saturday", saturdayChecked) { saturdayChecked = it }
        WeekDay("Sunday", sundayChecked) { sundayChecked = it }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            Text(text = "Add")
        }

    }
}

@Composable
fun WeekDay(day: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = day, modifier = Modifier.width(80.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}


