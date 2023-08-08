package com.homework.mymoviebox.data.states

import com.homework.mymoviebox.data.source.remote.response.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class LoadResource<ResultType, RequestType>(
    coroutineDispatcher: CoroutineDispatcher,
) {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().firstOrNull()
        if (shouldFetch(dbSource)) {
            when (val response = createCall().first()) {
                is Response.Success -> {
                    val result = mapRequestToResult(response.data)
                    emit(Resource.Success(result))
                }
                is Response.Empty -> {
                    emit(Resource.Error(Exception("The requested data is empty!")))
                }
                is Response.Error -> {
                    onFetchFailed(response.exception)
                    emit(Resource.Error(response.exception))
                }
            }
        } else {
            if (dbSource != null) {
                emit(Resource.Success(dbSource, Source.Database))
            } else {
                emit(Resource.Error(Exception("Data is null")))
            }
        }
    }.flowOn(coroutineDispatcher)

    private fun onFetchFailed(exception: Exception) = Timber.e(exception)

    protected open suspend fun loadFromDB(): Flow<ResultType?> = flow { emit(null) }


    protected open fun onFetchEmpty() {}

    /**
     * shouldFetch -> define to load from DB or not, set true by default to avoid boilerplate
     */
    protected open fun shouldFetch(data: ResultType?): Boolean = true

    protected abstract suspend fun createCall(): Flow<Response<RequestType>>

    protected abstract fun mapRequestToResult(data: RequestType): ResultType

    val flow: Flow<Resource<ResultType>> = result
}