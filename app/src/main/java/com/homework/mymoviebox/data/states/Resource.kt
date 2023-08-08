package com.homework.mymoviebox.data.states


sealed class Resource<out R> {
    data class Error(val exception: Exception) : Resource<Nothing>()
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T, val source: Source = Source.Network) : Resource<T>() {
        fun dataFromDB() = source == Source.Database
    }
}
