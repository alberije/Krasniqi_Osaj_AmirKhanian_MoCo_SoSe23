import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.plantsaver.R
import com.example.plantsaver.view.myPlants.MyPlantsEvent
import com.example.plantsaver.view.myPlants.MyPlantsViewModel
import kotlinx.coroutines.flow.collectLatest
import com.example.plantsaver.view.ImageBox

@Composable
fun MyPlantsOut(viewModel: MyPlantsViewModel, navController: NavHostController) {
    val plant = viewModel.currentPlant
    val plantFamily = viewModel.currentPlantFamily

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MyPlantsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.massage, Toast.LENGTH_LONG).show()
                }

                is MyPlantsViewModel.UiEvent.NavigatePlant -> {
                    // hier ist nichts zu tun
                }

                MyPlantsViewModel.UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }

            }


            item {

                Text(
                    text = plant.value?.name ?: "My plants",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2d681c),
                    modifier = Modifier
                        .padding(vertical = 16.dp)

                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                // hier beginnt table

                // row 1
                TableRow(text1 = "Plant Family", text2 = viewModel.currentPlantFamily.value?.name ?: "leer")
            }
            item {
                // row 2
                TableRow(text1 = "Location", text2 = viewModel.currentPlantFamily.value?.location ?: "leer")
            }
            item {
                TableRow(text1 = "Care", text2 = viewModel.currentPlantFamily.value?.care ?: "leer")
            }
            item {
                TableRow(text1 = "Care Plan", text2 = plant.value?.carePlanToString() ?: "No Care Plan")
            }
            item {
                TableRow(text1 = "Timeline", text2 = "")

            }


            item {
                Images(modifier = Modifier.fillMaxWidth(), viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            plant.value?.let { viewModel.onEvent(MyPlantsEvent.DeletePlant(plant.value!!)) }

                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF2d681c)),
                        modifier = Modifier
                            .padding(18.dp)
                            .width(100.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    {
                        Text(text = "delete")
                    }

                }
            }
        }
    }


}


@Composable
fun Images(modifier: Modifier, viewModel: MyPlantsViewModel) {


    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageNumber by remember {
        mutableStateOf(0)
    }

    var tempBitmap = remember<ImageBitmap?> { null }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                // jetzt ist photo in der uri
                // zu bitmap formatieren
                val newBitmap =
                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri).asImageBitmap()

                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                        ImageDecoder.decodeBitmap(source).asImageBitmap()
                    }

                // resize image
                val inputRatio = newBitmap.width.toFloat() / newBitmap.height.toFloat()
                val smallerSide = 860
                val resizeWidth: Int
                val resizeHeight: Int
                if (newBitmap.width < newBitmap.height) {
                    // portrait - ratio < 1
                    resizeWidth = smallerSide
                    resizeHeight = (resizeWidth / inputRatio).toInt()
                } else {
                    // landscape - ratio >= 1
                    resizeHeight = smallerSide
                    resizeWidth = (inputRatio * resizeHeight).toInt()
                }
                // Resize the image to the desired resolution
                tempBitmap = Bitmap.createScaledBitmap(newBitmap.asAndroidBitmap(), resizeWidth, resizeHeight, true).asImageBitmap()


                viewModel.onEvent(MyPlantsEvent.AddPhoto(imageNumber, tempBitmap!!))
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(imageUri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    val getImage: (number: Int) -> Unit = {

        imageNumber = it



        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(imageUri)
        } else {
            // Request a permission
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }


    }
    val photos = viewModel.currentPlantPhotos.value

    // photos ist die liste von fotos von dieser pflanze. hoer wird noch einmal null dran gefügt,
    // damit am ende noch das kästchen ist um ein neues foto hinzuzufügen
    val photos2: List<ImageBitmap?> = photos + null

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        photos2.chunked(3).forEachIndexed { rowIndex, rowItems ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEachIndexed() { index, photo ->
                    if(photo == null){
                        // das hier passiert nur für das letzte element um ein neues foto hinzuzufügen
                        ImageBox(
                            modifier = Modifier
                                .requiredWidth(90.dp)
                                .size(90.dp)
                                .aspectRatio(1f)
                                .clickable { getImage(photos.size + 1) }, //images werden durchgezählt 1,2,3. dann ist also size = 3 und ich will image 4 einfügen
                            bitmap = null,
                            text = "Next Month"
                        )
                    } else {
                        ImageBox(
                            modifier = Modifier
                                .requiredWidth(90.dp)
                                .size(90.dp)
                                .aspectRatio(1f)
                                .clickable { },
                            bitmap = photo,
                            text = "Add Image"
                        )
                    }
                }
            }

        }
    }

}

@Composable
fun TableRow(text1: String, text2: String) {

    val col1Weight = .4f
    val col2Weight = .6f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        // col 1
        Column(
            modifier = Modifier
                .weight(col1Weight)
        ) {
            Text(
                text = text1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // col 2
        Column(
            modifier = Modifier
                .weight(col2Weight)
        ) {
            Text(
                text = text2,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}



