package com.example.plantsaver.view.addPlant

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.plantsaver.BuildConfig
import com.example.plantsaver.R
import com.example.plantsaver.database.model.PlantFamily
import com.example.plantsaver.ui.theme.PlantSaverTheme
import com.example.plantsaver.view.ImageBox
import kotlinx.coroutines.flow.collectLatest
import java.io.File


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
fun AddplantsScreen(viewModel: AddPlantViewModel, navController: NavHostController) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPlantViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }

                AddPlantViewModel.UiEvent.NavigateUp -> {
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigateUp() }) {
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
                    .padding(vertical = 16.dp)

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
                                        value = viewModel.plantName.value,
                                        onValueChange = {
                                            viewModel.onEvent(
                                                AddPlantEvent.PlantNameFieldChanged(
                                                    it
                                                )
                                            )
                                        },
                                        label = { Text("Enter your plant Name") },
                                        singleLine = true,
                                        modifier = Modifier
                                            .width(230.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.White),

                                        colors = TextFieldDefaults.textFieldColors(
                                            containerColor = Color.White,
                                            unfocusedIndicatorColor = Color.White,
                                            focusedIndicatorColor = Color.White
                                        )
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
                                    dropDownMenu(viewModel)
                                }


                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
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
                                            onClick = { navController.navigate("createyourownFragment") },
                                            colors = ButtonDefaults.buttonColors(Color(0xFF55B663)),
                                            modifier = Modifier
                                                .width(150.dp)
                                                //.height(30.dp)
                                                .clip(RoundedCornerShape(18.dp))
                                        ) {
                                            Text(text = "create your own")
                                        }
                                    }


                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row() {

                                        Button(
                                            onClick = { navController.navigate("addplancareFragment") },
                                            colors = ButtonDefaults.buttonColors(Color(0xFF55B663)),
                                            modifier = Modifier
                                                .width(150.dp)
                                                //.height(30.dp)
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

                                ImagesAddPlants(viewModel)

                                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                                Row() {
                                    Box() {
                                        Button(
                                            onClick = {
                                                viewModel.onEvent(AddPlantEvent.AddPlantButton(context))
                                            },
                                            colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(40.dp)
                                                .clip(RoundedCornerShape(18.dp))
                                        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(viewModel: AddPlantViewModel) {
    var expanded by remember { mutableStateOf(false) } //speichert der aktuelle zustand des DropdownMenu
    var plantFamilyList: List<PlantFamily> = viewModel.plantFamilyList.value
    var textFieldSize by remember { mutableStateOf(Size.Zero) } //speichert Größe des Textfeldes

    //Icons
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(vertical = 20.dp)) {

        TextField(
            value = viewModel.selectedPlantFamily.value?.name ?: "",
            onValueChange = { viewModel.onEvent(AddPlantEvent.PlantFamilyFieldChanged(it)) },
            modifier = Modifier
                .width(230.dp)
                .clip(RoundedCornerShape(8.dp))
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() },
            label = { Text(text = "search for plant family ")},
            trailingIcon = {
                Icon(icon,"",Modifier.clickable{expanded =!expanded})
            },
            // @Lousi hier colors setzen
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            )
        )

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })   //with(LocalDensity.current): ist eine Funktion, die den Zugriff auf das aktuelle Density-Objekt in Compose ermöglicht. Density wird verwendet, um metrische Werte in DPs (Density-independent Pixels) zu konvertieren.
        ) {
            plantFamilyList.forEach { plantFamily ->
                DropdownMenuItem(onClick = {
                    viewModel.onEvent(AddPlantEvent.SelectedPlantFamilyChanged(plantFamily))
                    expanded = false
                }) {
                    Text(text = plantFamily.name)
                }


            }
        }

    }


}

@Composable
fun ImagesAddPlants(viewModel: AddPlantViewModel) {

    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageNumber by remember {
        mutableStateOf(0)
    }

    var tempBitmap = remember<ImageBitmap?> { null }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
       if (it) {
           //photo in Uri
           //zu bitmap formairen
           val newBitmap =
               if (Build.VERSION.SDK_INT < 28) {
                   MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri).asImageBitmap()

               }else {

                   val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                   ImageDecoder.decodeBitmap(source).asImageBitmap()
               }

           //resize image
           val inputRatio = newBitmap.width.toFloat() / newBitmap.height.toFloat()
           val smallerSide = 860
           val resizeWidth: Int
           val resizeHeight: Int
           if (newBitmap.width < newBitmap.height) {

               //portrait - ratio <1
               resizeWidth = smallerSide
               resizeHeight = (resizeWidth/inputRatio).toInt()

           } else {

               // landscape - ratio >= 1
               resizeHeight = smallerSide
               resizeWidth = (inputRatio * resizeHeight).toInt()
           }


           // Resize the image to the desired resolution
           tempBitmap = Bitmap.createScaledBitmap(newBitmap.asAndroidBitmap(), resizeWidth, resizeHeight, true).asImageBitmap()


           viewModel.onEvent(AddPlantEvent.AddPhoto(imageNumber, tempBitmap!!))
       }
    }

val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
) {
    if (it) {
        Toast.makeText(context, "Permission Granted ", Toast.LENGTH_SHORT).show()
        cameraLauncher.launch(imageUri)
        }else {

        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()


    }
}

val getImage: (number:Int) -> Unit = {

    imageNumber = it

    imageUri = ComposeFileProvider.getImageUri(context)

    val permissionCheckResult =
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        cameraLauncher.launch(imageUri)
    } else {
        // Request a permission
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

}
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ImageBox(
            modifier = Modifier
                .size(90.dp)
                .clickable { getImage(1) },
            bitmap = viewModel.image1.value,
            text = "Add Photo"
        )
    }
}


class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
        val directory = File(context.cacheDir, "images")
        directory.mkdirs()
        val file = File.createTempFile(
            "selectede_image_",
            ".jpg",
            directory,
        )
            val authority = BuildConfig.APPLICATION_ID + ".provider"
        return getUriForFile(
        context,
        authority,
        file,

        )

    }
    }
}





























