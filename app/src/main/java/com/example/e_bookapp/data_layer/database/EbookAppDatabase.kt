package com.example.e_bookapp.data_layer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_bookapp.data_layer.dao.EbookAppDao
import com.example.e_bookapp.data_layer.tables.Ebook


@Database(entities = [Ebook::class], version = 2, exportSchema = false)
abstract class EbookAppDatabase : RoomDatabase() {
    abstract fun ebookAppDao(): EbookAppDao

}