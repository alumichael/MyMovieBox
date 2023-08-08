package com.homework.mymoviebox.data.source.remote.response

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class ResponseFlow<ResultType> {
    private val result = flow {
        val response = responseCall()
        checkNotNull(response)
        if (response is List<*>) {
            if (response.isNotEmpty()) {
                emit(Response.Success(response))
            } else {
                emit(Response.Empty)
            }
        } else {
            emit(Response.Success(response))
        }
    }.catch { exception ->
        emit(Response.Error(exception as Exception))
        Timber.e(exception.toString())
    }

    protected abstract suspend fun responseCall(): ResultType

    val flow: Flow<Response<ResultType>> = result
}