package com.homework.mymoviebox.presentation.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.homework.mymoviebox.data.source.remote.response.CreditsResponse
import com.homework.mymoviebox.data.source.remote.response.CrewResponse
import com.homework.mymoviebox.data.source.remote.response.MovieResponse
import com.homework.mymoviebox.data.source.remote.response.Response
import com.homework.mymoviebox.data.states.UiState
import com.homework.mymoviebox.domain.interactor.FavoriteUseCase
import com.homework.mymoviebox.domain.interactor.MovieUseCase
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.domain.usecase.FetchMovies
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by Michael Alu on 08/08/2023.
 */
class MovieViewModelTest {


    private val fetchMoviesUsecase: MovieUseCase = mockk()
    private val favoriteUseCase: FavoriteUseCase = mockk()
    private lateinit var viewModel: MovieViewModel


    @BeforeEach
    fun setUp() {
        viewModel = MovieViewModel(
            fetchMoviesUsecase,
            favoriteUseCase
        )

    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun fetchMovie()  = runBlocking{
        val expectedResponse= listOf(
            Movie(
                movieId = 1,
                backdrop = "path",
                title = "Movie Title",
                poster = "path",
                genres = String(),
                overview = "summary",
                voteAverage = 2.0,
                releaseDate = "date",
                runtime = 2,
                director = String()
            )
        )

        val response: Flow<PagingData<Movie>> = flowOf(value = PagingData.from(data = expectedResponse))
        coEvery { fetchMoviesUsecase.fetchMovies.invoke()} returns response

        viewModel.fetchMovie()

        viewModel.state.collectLatest {
            values->
            assertEquals(UiState.Success::class.java, values::class.java)
        }


    }
}