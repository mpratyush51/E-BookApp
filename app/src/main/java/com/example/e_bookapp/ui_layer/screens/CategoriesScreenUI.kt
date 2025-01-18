package com.example.e_bookapp.ui_layer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.e_bookapp.ui_layer.components.CategoryView
import com.example.e_bookapp.ui_layer.navigation.Routes
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel

@Composable
fun CategoryScreenUI(viewModel: EbookAppViewModel = hiltViewModel(),
                     navController : NavController) {

    val state = viewModel.getAllCategoryState.collectAsState()
    val data = state.value.data

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategories()
    }

    when{
           state.value.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    state.value.data.isNotEmpty() -> {

                        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
                            contentPadding = PaddingValues(10.dp)
                            ) {
                            items(data){
                                CategoryView(image = it.categoryImageUrl,
                                    name = it.categoryName){
                                    navController.navigate(Routes.BookByCategory(it.categoryName))
                                }

                            }
                        }
                    }

                    state.value.error != null -> {

                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text ="An Error Occured")
                        }
                    }
                }

}
