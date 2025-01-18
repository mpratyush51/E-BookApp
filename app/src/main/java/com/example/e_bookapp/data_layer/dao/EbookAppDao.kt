package com.example.e_bookapp.data_layer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.e_bookapp.data_layer.tables.Ebook
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow

@Dao
interface EbookAppDao {

    @Upsert
    suspend fun insertBook(book: Ebook)

    @Delete
    suspend fun deleteBook(book: Ebook)

    @Query("SELECT * FROM book_progress")
    fun getBookmarkedBooks(): Flow<List<Ebook>>

}