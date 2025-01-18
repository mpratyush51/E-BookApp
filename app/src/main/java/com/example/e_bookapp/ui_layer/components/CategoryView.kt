package com.example.e_bookapp.ui_layer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale


@Composable
fun CategoryView(image:String,
                 name : String,
                 onClick : () -> Unit) {

    val context = LocalContext.current
    Card(modifier = Modifier.wrapContentSize()
        .aspectRatio(1f)
        .padding(10.dp)
        .clickable(onClick = onClick)
        ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            AsyncImage(model = (ImageRequest.Builder(context)
                .data(image).scale(Scale.FILL)
                .crossfade(true)
                .build()),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize().padding(10.dp)

            )
            Text(text = name,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
                )
        }
    }
}