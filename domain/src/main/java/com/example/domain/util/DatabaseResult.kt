package com.example.domain.util

sealed class DatabaseResult<out T> {
    data class Success<out T>(val data: T) : DatabaseResult<T>()
    data class Failure<out T>(val message: String) : DatabaseResult<T>()
}