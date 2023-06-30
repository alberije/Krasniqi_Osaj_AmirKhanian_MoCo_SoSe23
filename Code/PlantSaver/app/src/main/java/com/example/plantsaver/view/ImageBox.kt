package com.example.plantsaver.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp


@Composable
fun ImageBox(modifier: Modifier, bitmap: ImageBitmap?, text: String, showText: Boolean = false){
    Box(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(9.dp))
            .background(
                Color.White,
                RoundedCornerShape(9.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if(bitmap == null) {
            Text(text = text)
        } else {
            Column {
                Image(
                    bitmap = bitmap,
                    contentDescription = "Image of Plant",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                )
                if(showText)
                    Text(text = text)
            }
        }
    }
}