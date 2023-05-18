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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantsaver.ui.theme.PlantSaverTheme

class AddPlants : ComponentActivity() {
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
fun Add() {
    Box(
        modifier = Modifier

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { }) {
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
                                        value = "",
                                        onValueChange = { },
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
                                    TextField(
                                        value = "",
                                        onValueChange = { },
                                        label = { Text("Enter your Plant Family") },
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
                                        value = "",
                                        onValueChange = { },
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
                                        value = "",
                                        onValueChange = { },
                                        label = { Text("Enter Plantcare") },
                                        modifier = Modifier.width(230.dp)
                                    )
                                }


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
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }





