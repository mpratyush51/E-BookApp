package com.example.e_bookapp.State

import com.example.e_bookapp.data_layer.response.BooksModel

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error<T>(val exception: Throwable) : ResultState<T>()

}