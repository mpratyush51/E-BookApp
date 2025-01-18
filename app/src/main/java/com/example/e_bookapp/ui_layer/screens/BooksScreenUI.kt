package com.example.e_bookapp.ui_layer.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_bookapp.ui_layer.components.BookView
import com.example.e_bookapp.ui_layer.navigation.Routes
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel

@Composable
fun BooksScreenUI(viewModel : EbookAppViewModel = hiltViewModel(),navController : NavController)  {

    val state = viewModel.getAllBooksState.collectAsState()
    val data = state.value.data

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllBooks()
    }

    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.value.data.isNotEmpty() -> {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(data){
//                    BookView(title = it.bookName ,
//                        bookCoverPageUrl = it.bookCoverUrl,
//                        author = it.bookauthor)
//                    Log.d("ImageURL", "BooksScreenUI: ${it.bookCoverUrl}")
//                }
//
//            }
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(10.dp),
                verticalItemSpacing = 10.dp,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()) {
                items(data){
                    BookView(title = it.bookName ,
                        bookCoverPageUrl = it.bookCoverUrl,
                        author = it.bookauthor,
                        category = it.bookCategory){
                        navController.navigate(Routes.pdfReader(pdfUrl = it.bookPDFUrl, bookName = it.bookName,
                            bookCoverUrl = it.bookCoverUrl, bookAuthor = it.bookauthor))
                    }
                }
            }
        }
        state.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "An Error Occured")
            }
        }

    }
}

//@Composable
//fun book(title : String,
//         bookCoverPageUrl : String,
//         author : String ){
//
//    Card(modifier = Modifier.fillMaxWidth()){
//        Row(modifier = Modifier.padding(10.dp)) {
//            AsyncImage(model = bookCoverPageUrl,
//                contentDescription = null,
//                )
//            Column(modifier = Modifier.padding(10.dp)) {
//
//                Text(text = title)
//                Text(text = author)
//            }
//        }
//    }
//}