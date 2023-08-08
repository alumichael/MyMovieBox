package com.homework.mymoviebox.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.homework.mymoviebox.data.Constants.DEFAULT_PAGE_SIZE
import com.homework.mymoviebox.data.mapper.mapToDomain
import com.homework.mymoviebox.data.mapper.mapToEntity
import com.homework.mymoviebox.data.source.local.LocalDataSource
import com.homework.mymoviebox.data.source.remote.RemoteDataSource
import com.homework.mymoviebox.data.source.remote.response.MovieResponse
import com.homework.mymoviebox.data.source.remote.response.Response
import com.homework.mymoviebox.data.states.LoadResource
import com.homework.mymoviebox.di.IoDispatcher
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.domain.repository.Repository
import com.homework.mymoviebox.presentation.paging.MoviePagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) : Repository {
    override fun fetchMovies(): Flow<PagingData<Movie>> = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { MoviePagingSource(remoteDataSource) }
    ).flow

    override fun searchMovie(query: String) = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { MoviePagingSource(remoteDataSource, query) }
    ).flow


    override fun fetchMovie(id: Int) =
        object : LoadResource<Movie, MovieResponse>(dispatcher) {
            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun loadFromDB(): Flow<Movie?> =
                localDataSource.getFavoriteMovie(id)

            override suspend fun createCall(): Flow<Response<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override fun mapRequestToResult(data: MovieResponse): Movie =
                data.mapToDomain()
        }.flow


    override fun getFavoriteMovies() =
        localDataSource.getFavoriteMovies()
            .distinctUntilChanged()
            .flowOn(dispatcher)
            .map { it.mapToDomain() }


    override fun setFavorite(movie: Movie, itemIsFavorite: Boolean) {
        CoroutineScope(dispatcher).launch {
            val entity = movie.mapToEntity()
            if (itemIsFavorite) {
                localDataSource.addToFavorite(entity)
            } else {
                localDataSource.removeFromFavorite(entity)
            }
        }
    }

    companion object {
        val DEFAULT_PAGING_CONFIG = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = DEFAULT_PAGE_SIZE
        )
    }
}