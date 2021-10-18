package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util

sealed class State<out T> {

    data class Success<T>(val data: T) : State<T>()

    data class Error(val exception: Exception) : State<Nothing>()

    object Loading : State<Nothing>()

}