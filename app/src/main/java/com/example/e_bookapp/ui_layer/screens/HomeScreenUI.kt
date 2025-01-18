package com.example.e_bookapp.ui_layer.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.e_bookapp.data_layer.tables.Ebook
import com.example.e_bookapp.ui_layer.navigation.Routes.BookmarkedBooks
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenUI(navController: NavController,viewModel: EbookAppViewModel = hiltViewModel()) {
    val tabs = listOf(
        TabItem("Category" ,Icons.Rounded.Category),
        TabItem("All Books" , Icons.Rounded.Book)
    )

    var pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {TopAppBar(title = { Text(text = "E-Book App") },
            actions = {Icon(imageVector = Icons.Rounded.Book, contentDescription = null,
                modifier = Modifier.clickable(onClick = {navController.navigate(BookmarkedBooks)
                }))})

        } ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.fastForEachIndexed { index, tab ->
                    Tab(selected = pagerState.currentPage == index,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            scope.launch() {
                                pagerState.animateScrollToPage(index)
                            }
                            Log.d("HomeScreenUI", "HomeScreenUI: ${pagerState.currentPage}")
                        },

                        text = {
                            Text(text = tab.tabName)
                        },
                        icon = {
                            Icon(imageVector = tab.tabIcon, contentDescription = null)
                        }
                    )
                }

            }


            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> CategoryScreenUI(navController = navController)
                    1 -> BooksScreenUI(navController = navController)
                }
            }
        }
    }
}

data class TabItem(
    val tabName: String,
    val tabIcon: ImageVector)