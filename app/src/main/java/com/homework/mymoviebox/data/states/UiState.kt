package com.homework.mymoviebox.data.states

sealed class UiState<out R> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    object Idle : UiState<Nothing>()
}
