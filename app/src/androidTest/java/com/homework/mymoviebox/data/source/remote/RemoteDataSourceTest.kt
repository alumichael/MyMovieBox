package com.homework.mymoviebox.data.source.remote

import com.homework.mymoviebox.data.source.remote.network.MyMovieBoxServices
import com.homework.mymoviebox.data.source.remote.response.CreditsResponse
import com.homework.mymoviebox.data.source.remote.response.CrewResponse
import com.homework.mymoviebox.data.source.remote.response.GenreResponse
import com.homework.mymoviebox.data.source.remote.response.ListResponse
import com.homework.mymoviebox.data.source.remote.response.MovieResponse
import com.homework.mymoviebox.data.source.remote.response.ResponseFlow
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response


/**
 * Created by Michael Alu on 08/08/2023.
 */

class RemoteDataSourceTest {

    private val services: MyMovieBoxServices = mockk()
    private val repository: RemoteDataSource = RemoteDataSource(services)


    @Test
    fun getMovies() = runBlocking {
        // Mocked response
        val pageNo = 1
        val expectedResponse = listOf(
            MovieResponse(
                id = 1,
                backdrop = "path",
                title = "Movie Title",
                poster = "path",
                genres = emptyList(),
                credits = CreditsResponse(crew = emptyList<CrewResponse>()),
                overview = "summary",
                voteAverage = 2,
                releaseDate = "date",
                runtime = 2
            )
        )
        coEvery { services.getMovies(pageNo) } returns ListResponse<MovieResponse>(
            expectedResponse, 20, 20
        )

        // Invoke the method under test
        val result = repository.getMovies(pageNo)

        // Assert the result
        assertEquals(ListResponse::class.java, result::class.java)
    }

    @Test
    fun searchMovie() = runBlocking {
        // Mocked response
        val pageNo = 1
        val query = "Hiding Strike"
        val expectedResponse = listOf(
            MovieResponse(
                id = 1,
                backdrop = "path",
                title = "Movie Title",
                poster = "path",
                genres = emptyList(),
                credits = CreditsResponse(crew = emptyList<CrewResponse>()),
                overview = "summary",
                voteAverage = 2,
                releaseDate = "date",
                runtime = 2
            )
        )
        coEvery { services.searchMovie(query, pageNo) } returns ListResponse<MovieResponse>(
            expectedResponse, 20, 20
        )

        // Invoke the method under test
        val result = repository.searchMovie(query, pageNo)

        // Assert the result
        assertEquals(ListResponse::class.java, result::class.java)
    }

    @Test
    fun getMovie() = runBlocking {
        // Mocked response
        val movieId = 1
        val expectedResponse = MovieResponse(
            id = 1,
            backdrop = "path",
            title = "Movie Title",
            poster = "path",
            genres = emptyList(),
            credits = CreditsResponse(crew = emptyList<CrewResponse>()),
            overview = "summary",
            voteAverage = 2,
            releaseDate = "date",
            runtime = 2
        )
        coEvery { services.getMovie(movieId) } returns expectedResponse

        // Invoke the method under test
        val result = repository.getMovie(movieId).collectLatest {
            value ->
            assertEquals( Response::class.java, value::class.java)
        }

        // Assert the result
        //assertEquals(ResponseFlow::class.java, result::class.java)
    }
}