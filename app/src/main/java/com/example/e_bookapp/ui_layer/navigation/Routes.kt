package com.example.e_bookapp.ui_layer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object HomeScreen

    @Serializable
    data class BookByCategory(
        val categoryName : String
    )

    @Serializable
    data class pdfReader (
        val pdfUrl : String,
        val bookName : String,
        val bookCoverUrl : String,
        val bookAuthor : String
    )

    @Serializable
    object BookmarkedBooks

}