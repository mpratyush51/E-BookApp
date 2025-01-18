package com.example.e_bookapp.ui_layer.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_bookapp.DI.DIModule.provideDatabase
import com.example.e_bookapp.data_layer.database.EbookAppDatabase
import com.example.e_bookapp.data_layer.tables.Ebook
import com.example.e_bookapp.ui_layer.viewModel.EbookAppViewModel
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberVerticalPdfReaderState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pdfReaderUI(pdfUrl : String,
                bookName : String,
                bookCoverUrl : String,
                bookAuthor:String,
                viewModel : EbookAppViewModel = hiltViewModel()) {

    val pageNumber = remember { mutableStateOf(0) }
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(pdfUrl),
        isZoomEnable = true,
        // Track current page

    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = bookName) },
                actions = {
                    Icon(imageVector = Icons.Default.Bookmark, contentDescription = null,
                        modifier = Modifier.clickable(onClick = {

                            saveBookmark(pdfUrl, pageNumber.value,
                                viewModel=viewModel,
                                bookName = bookName,
                                bookCoverUrl = bookCoverUrl,
                                bookAuthor = bookAuthor)


                        })
                    )
                })
        }) {
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = Color.Gray)
        )
    }

}

fun saveBookmark(pdfUrl: String,
                 lastPageRead: Int,
                 viewModel : EbookAppViewModel,
                 bookName : String,
                 bookCoverUrl : String,
                 bookAuthor:String) {
    val bookmark = Ebook(
        bookPage = lastPageRead.toString(),
        bookName = bookName,
        bookCoverUrl = bookCoverUrl,
        bookUrl = pdfUrl,
        bookAuthor = bookAuthor
        )
    viewModel.lastReadBook(book = bookmark)

}


