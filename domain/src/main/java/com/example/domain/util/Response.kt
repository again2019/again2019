package com.example.domain.util

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure<out T>(val exceptionMessage: String) : Response<T>()
    object Loading : Response<Nothing>()

    data class Except <out T>(val exception: Throwable) : Response<T>()
}