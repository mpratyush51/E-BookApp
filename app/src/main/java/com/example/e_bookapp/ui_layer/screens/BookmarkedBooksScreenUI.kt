package com.example.e_bookapp.ui_layer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_bookapp.data_layer.tables.Ebook
import com.example.e_bookapp.ui_layer.components.BookView
import com.example.e_bookapp.ui_layer.navigation.Routes
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkedBooksScreenUI(viewModel: EbookAppViewModel = hiltViewModel(),navController : NavController) {

    val bookmarks = viewModel.getBookmarkedBooks().collectAsState(emptyList())

    LaunchedEffect(key1 = Unit) {
        viewModel.getBookmarkedBooks()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {TopAppBar(title = { Text(text = "Bookmarks") })}
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(10.dp),
                verticalItemSpacing = 10.dp,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()) {

                items(bookmarks.value){
                    BookView(title = it.bookName ,
                        bookCoverPageUrl = it.bookCoverUrl,
                        author = it.bookAuthor,
                        category = it.bookAuthor){
                        navController.navigate(Routes.pdfReader(pdfUrl = it.bookUrl, bookName = it.bookName,
                            bookCoverUrl = it.bookCoverUrl, bookAuthor = it.bookAuthor))
                    }
                }
            }


        }
    }
}

