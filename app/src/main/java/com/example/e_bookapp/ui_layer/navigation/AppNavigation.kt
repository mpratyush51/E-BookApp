package com.example.e_bookapp.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_bookapp.ui_layer.navigation.Routes.BookByCategory
import com.example.e_bookapp.ui_layer.navigation.Routes.HomeScreen
import com.example.e_bookapp.ui_layer.screens.BookMarkedBooksScreenUI
import com.example.e_bookapp.ui_layer.screens.BooksByCategoryUI
import com.example.e_bookapp.ui_layer.screens.HomeScreenUI
import com.example.e_bookapp.ui_layer.screens.pdfReaderUI

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

        NavHost(navController = navController, startDestination = HomeScreen) {

            composable<Routes.HomeScreen>{
                HomeScreenUI(navController)
            }

            composable< Routes.BookByCategory>{
               val data = it.toRoute<Routes.BookByCategory>()
                BooksByCategoryUI(navController = navController, bookCategory = data.categoryName)
            }

            composable<Routes.pdfReader>{
                val data = it.toRoute<Routes.pdfReader>()
                pdfReaderUI(pdfUrl = data.pdfUrl,
                    bookName = data.bookName,
                    bookCoverUrl = data.bookCoverUrl,
                    bookAuthor = data.bookAuthor)

            }

            composable<Routes.BookmarkedBooks>{
                BookMarkedBooksScreenUI(navController = navController)
            }



        }

}