package com.homework.mymoviebox.presentation.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.homework.mymoviebox.data.states.UiState
import com.homework.mymoviebox.domain.interactor.FavoriteUseCase
import com.homework.mymoviebox.domain.interactor.MovieUseCase
import com.homework.mymoviebox.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    val movieIntent = Channel<MovieIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UiState<PagingData<Movie>>>(UiState.Idle)
    val state: StateFlow<UiState<PagingData<Movie>>>
        get() = _state.asStateFlow()

    private val _favouriteState = MutableStateFlow<UiState<List<Movie>>>(UiState.Idle)
    val favouriteState: StateFlow<UiState<List<Movie>>> get() = _favouriteState.asStateFlow()

    val query = MutableStateFlow(String())


    //private var previousIntent: MovieIntent? = null
    private fun handleIntent() {
        viewModelScope.launch {
            movieIntent.consumeAsFlow().collect { intent ->
               // previousIntent?.let { }
                when (intent) {
                    MovieIntent.FetchMovie -> fetchMovie()
                    MovieIntent.SearchMovie -> searchMovie()
                    MovieIntent.FetchFavouriteMovie -> fetchFavouriteMovie()
                }
            }
        }
    }

    init {
        handleIntent()
        viewModelScope.launch {
            movieIntent.send(MovieIntent.FetchMovie)
        }
    }

    fun fetchMovie() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            movieUseCase.fetchMovies()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }
        }
    }

    private fun fetchFavouriteMovie() {
        _favouriteState.value = UiState.Loading
        viewModelScope.launch {
            favoriteUseCase.getFavoriteMovies()
                .collectLatest {
                    _favouriteState.value = UiState.Success(it)
                }
        }
    }

    private fun searchMovie() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            query.flatMapLatest { movieUseCase.searchMovie(it) }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _state.value = UiState.Success(it)
                }
        }
    }


}