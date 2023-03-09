package com.goingbacking.goingbacking.util

sealed class UiState<out T> {
    object Loading:UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(var error: String?) : UiState<Nothing>()
}