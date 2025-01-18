package com.example.e_bookapp.ui_layer.viewModel

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_bookapp.State.ResultState
import com.example.e_bookapp.data_layer.repository.Repo
import com.example.e_bookapp.data_layer.response.BooksModel
import com.example.e_bookapp.data_layer.response.CategoryModel
import com.example.e_bookapp.data_layer.tables.Ebook
import com.rizzi.bouquet.HorizontalPdfReaderState
import com.rizzi.bouquet.ResourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EbookAppViewModel @Inject constructor(private val repo : Repo) : ViewModel( ){

    private val _getAllBooksState = MutableStateFlow(getAllBookState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(getAllCategoriesState())
    val getAllCategoryState = _getAllCategoriesState.asStateFlow()

    private val _getAllBooksByCategoryState = MutableStateFlow(getBooksByCategoryState())
    val getAllBooksByCategoryState = _getAllBooksByCategoryState.asStateFlow()


     fun getAllBooks(){
         viewModelScope.launch(Dispatchers.IO) {
        repo.getAllBooks().collect{

            when(it){
                is ResultState.Loading -> {
                     _getAllBooksState.value = getAllBookState(isLoading = true)
                }
                is ResultState.Success -> {
                    _getAllBooksState.value = getAllBookState(isLoading = false , data = it.data)

                }
                is ResultState.Error -> {
                    _getAllBooksState.value = getAllBookState(error = it.exception)
                }
            }
        }
        }
         Log.d("DATA", "Book data : ${getAllBooksState.value}")
    }

     fun getAllCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCategories().collect{

                when(it){
                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = getAllCategoriesState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getAllCategoriesState.value = getAllCategoriesState(
                            isLoading = false ,
                            data = it.data)

                    }
                    is ResultState.Error -> {
                        _getAllCategoriesState.value = getAllCategoriesState(error = it.exception)
                    }
                }
            }
        }
        Log.d("DATA", "Book data : ${getAllCategoryState.value}")
    }

    fun getBooksByCategory(category : String){
        viewModelScope.launch(Dispatchers.IO) {
        repo.getBooksByCategory(category).collect{
            when(it){
                is ResultState.Loading -> {
                    _getAllBooksByCategoryState.value = getBooksByCategoryState(isLoading = true)
                }
                is ResultState.Success -> {
                    _getAllBooksByCategoryState.value =
                        getBooksByCategoryState(isLoading = false ,
                        data = it.data)
                    Log.d("DATA", "Book data : ${getAllBooksByCategoryState.value}")
                }
                is ResultState.Error -> {
                    _getAllBooksByCategoryState.value =
                        getBooksByCategoryState(error = it.exception)

                }
                }
            }
        }
    }

    fun lastReadBook(book : Ebook){
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveBook(book = book)
        }
    }

    fun getBookmarkedBooks() : Flow<List<Ebook>>{
        return repo.getAllBookmarkedBooks()
    }

}

data class getAllBookState(
    val isLoading : Boolean = false,
    val data : List<BooksModel> = emptyList(),
    val error : Throwable? = null)

data class getAllCategoriesState(
    val isLoading: Boolean = false,
    val data: List<CategoryModel> = emptyList(),
    val error: Throwable? = null)

data class getBooksByCategoryState(
    val isLoading: Boolean  = false,
    val data: List<BooksModel> = emptyList(),
    val error: Throwable? = null
)