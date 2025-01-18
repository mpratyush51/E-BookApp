package com.example.e_bookapp.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.e_bookapp.ui_layer.components.BookView
import com.example.e_bookapp.ui_layer.navigation.Routes
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksByCategoryUI(viewModel: EbookAppViewModel = hiltViewModel(),
                      navController : NavController,
                      bookCategory : String) {
    
    val state = viewModel.getAllBooksByCategoryState.collectAsState()
    val data = state.value.data

    LaunchedEffect(key1 = Unit) {
        viewModel.getBooksByCategory(bookCategory)
    }

    when{
        state.value.isLoading ->{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.value.data.isNotEmpty() -> {
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(title = { Text(text = bookCategory)})
                }) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.padding(it)
                ) {
                    items(data) {
                        BookView(
                            title = it.bookName,
                            bookCoverPageUrl = it.bookCoverUrl,
                            author = it.bookauthor,
                            category = it.bookCategory
                        ) {
                            navController.navigate(
                                Routes.pdfReader(
                                    pdfUrl = it.bookPDFUrl,
                                    bookName = it.bookName,
                                    bookCoverUrl = it.bookCoverUrl,
                                    bookAuthor = it.bookauthor
                                )
                            )
                        }
                    }
                }
            }
        }

        state.value.error != null -> {
            Toast.makeText(LocalContext.current, "Error Loading Books",Toast.LENGTH_SHORT).show()
        }
    }
    
}