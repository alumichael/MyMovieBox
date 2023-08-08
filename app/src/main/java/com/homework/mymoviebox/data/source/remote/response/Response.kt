package com.homework.mymoviebox.data.source.remote.response

sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
    object Empty : Response<Nothing>()
}
