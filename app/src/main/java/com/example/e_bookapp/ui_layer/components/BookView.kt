package com.example.e_bookapp.ui_layer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun BookView(title : String ,
             bookCoverPageUrl : String,
             author : String,
             category : String,
             onClick : () -> Unit ){
    val context = LocalContext.current

    Card(modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .padding(10.dp)
        .clickable(onClick = onClick)){
        Column(modifier = Modifier.fillMaxSize()) {
                AsyncImage(model = (ImageRequest.Builder(context)
                    .data(bookCoverPageUrl)
                    .size(250,250).build()),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds,
                    clipToBounds = true

                )
                Text(text = title)
            }
    }

}