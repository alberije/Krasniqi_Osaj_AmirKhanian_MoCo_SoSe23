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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantsaver.Plant
import com.example.plantsaver.R

@Composable
fun MyPlantsOut(navController: NavHostController, plantList: MutableList<Plant>) {
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
                text = "My plants",
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
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Plant Name",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold

                                    )



                                    Text(
                                        text = "\t\t\tleer",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 7.dp)
                                    )



                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Plant Family",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )



                                    Text(
                                        text = "\t\t\tleer",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 2.dp)
                                    )

                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "location",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )


                                    Text(
                                        text = "leer",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 55.dp)
                                    )

                                }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "care",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "\t\t\tleer",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(horizontal = 60.dp)
                                    )
                                }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
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
                                Spacer(modifier = Modifier.padding(vertical = 70.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
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


}