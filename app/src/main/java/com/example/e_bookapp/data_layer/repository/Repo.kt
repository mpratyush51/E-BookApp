package com.example.e_bookapp.data_layer.repository

import com.example.e_bookapp.State.ResultState
import com.example.e_bookapp.data_layer.database.EbookAppDatabase
import com.example.e_bookapp.data_layer.response.BooksModel
import com.example.e_bookapp.data_layer.response.CategoryModel
import com.example.e_bookapp.data_layer.tables.Ebook
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose

class Repo @Inject constructor(val firebaseDatabase : FirebaseDatabase, val roomDatabase : EbookAppDatabase) {



    fun getAllBooks(): Flow<ResultState<List<BooksModel>>>  = callbackFlow{

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               var items : List<BooksModel> = emptyList()
               items = snapshot.children.map { value ->
                   value.getValue<BooksModel>()!!
               }

                trySend(ResultState.Success(items))
            }


            override fun onCancelled(error: DatabaseError) {

                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose(){
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    fun getAllCategories(): Flow<ResultState<List<CategoryModel>>>  = callbackFlow{

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<CategoryModel> = emptyList()
                items = snapshot.children.map { value ->
                    value.getValue<CategoryModel>()!!
                }

                trySend(ResultState.Success(items))
            }


            override fun onCancelled(error: DatabaseError) {

                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("Category").addValueEventListener(valueEvent)

        awaitClose(){
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    fun getBooksByCategory(category: String) : Flow<ResultState<List<BooksModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BooksModel> = emptyList()
                items = snapshot.children.filter { value ->
                    value.getValue<BooksModel>()!!.bookCategory == category
                }.map{
                    it.getValue<BooksModel>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }


        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose(){
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }


    }

     fun lastReadBook () {

        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BooksModel> = emptyList()
                items = snapshot.children.map{
                    it.getValue<BooksModel>()!!
                }
                ResultState.Success(items)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

    }

    suspend fun saveBook(book : Ebook){
        roomDatabase.ebookAppDao().insertBook(book = book)
    }

    suspend fun deleteBook(book : Ebook){
        roomDatabase.ebookAppDao().deleteBook(book = book)
    }

    fun getAllBookmarkedBooks() : Flow<List<Ebook>>  {
        return roomDatabase.ebookAppDao().getBookmarkedBooks()
    }




}