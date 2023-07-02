package com.example.plantsaver.view.addPlant

import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CarePlanPage(viewModel: AddPlantViewModel, navController: NavHostController) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPlantViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }

                is AddPlantViewModel.UiEvent.NavigateUp -> {
                    navController.navigateUp()
                    viewModel.onEvent(AddPlantEvent.ResetCarePlanScreen)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navController.navigateUp()
                viewModel.onEvent(AddPlantEvent.ResetCarePlanScreen)
            }) {
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


        WeekDay("Monday", viewModel.mondayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 1)) }
        WeekDay("Tuesday", viewModel.tuesdayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 2)) }
        WeekDay("Wednesday", viewModel.wednesdayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 3)) }
        WeekDay("Thursday", viewModel.thursdayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 4)) }
        WeekDay("Friday", viewModel.fridayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 5)) }
        WeekDay("Saturday", viewModel.saturdayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 6)) }
        WeekDay("Sunday", viewModel.sundayChecked.value) { viewModel.onEvent(AddPlantEvent.DayChecked(it, 7)) }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onEvent(AddPlantEvent.SaveCarePlan) },
            colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            Text(text = "Save")
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


