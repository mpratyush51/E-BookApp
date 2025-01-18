package com.example.e_bookapp.data_layer.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_bookapp.data_layer.response.BooksModel

@Entity(tableName = "book_progress")
data class Ebook(
    @PrimaryKey(autoGenerate = true) val bookId : Int? = null,
    val bookPage : String,
    val bookUrl : String,
    val bookCoverUrl : String,
    val bookName : String,
    val bookAuthor : String
)
