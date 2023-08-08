package com.homework.mymoviebox.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.homework.mymoviebox.data.Constants.DEFAULT_PAGE_INDEX
import com.homework.mymoviebox.data.mapper.mapToDomain
import com.homework.mymoviebox.data.source.remote.RemoteDataSource
import com.homework.mymoviebox.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String? = null,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = if (query == null)
                remoteDataSource.getMovies(pageNumber)
            else
                remoteDataSource.searchMovie(query, pageNumber)

            val result = response.results
                .mapToDomain()

            val nextKey =
                if (result.isEmpty() || response.totalResults < 20 || pageNumber == response.totalPages || response.totalPages == 0)
                    null
                else
                    pageNumber + 1

            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}