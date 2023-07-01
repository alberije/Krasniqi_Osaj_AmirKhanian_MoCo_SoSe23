import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.R
import com.example.plantsaver.view.addPlant.AddPlantEvent
import com.example.plantsaver.view.addPlant.AddPlantViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlantFamScreen(viewModel: AddPlantViewModel, navController: NavHostController) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPlantViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }
                is AddPlantViewModel.UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

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

                IconButton(onClick = { navController.navigateUp()}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }


            Text(
                text = "Create Plant Family",
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
                                        value = viewModel.createPlantFam.value.name,
                                        onValueChange = { viewModel.onEvent(
                                            AddPlantEvent.PlantFamNameChanged(it)
                                        ) },
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
                                        text = "description",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextField(
                                        value = viewModel.createPlantFam.value.description,
                                        onValueChange = { viewModel.onEvent(AddPlantEvent.PlantFamDescriptionChanged(it))},
                                        label = { Text("Enter your Plant description") },
                                        modifier = Modifier.width(230.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "location",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextField(
                                        value = viewModel.createPlantFam.value.location,
                                        onValueChange = { viewModel.onEvent(AddPlantEvent.PlantFamLocationChanged(it))},
                                        label = { Text("Enter your Plant location") },
                                        modifier = Modifier.width(230.dp)
                                    )
                                }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "care",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextField(
                                        value = viewModel.createPlantFam.value.care,
                                        onValueChange = {viewModel.onEvent(AddPlantEvent.PlantFamCareChanged(it))},
                                        label = { Text("Enter Plantcare") },
                                        modifier = Modifier.width(230.dp)
                                    )
                                }



                                Spacer(modifier = Modifier.height(120.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = { viewModel.onEvent(AddPlantEvent.AddPlantFamilyButton)},
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
                        }

                    }

                }

            }
        }
    }



}