package com.homework.mymoviebox.presentation.home.movie

import android.app.SearchManager
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.mymoviebox.R
import com.homework.mymoviebox.data.states.UiState
import com.homework.mymoviebox.databinding.FragmentMovieBinding
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.presentation.adapter.MoviePagedAdapter
import com.homework.mymoviebox.util.intentToDetailsActivity
import com.homework.mymoviebox.util.invisible
import com.homework.mymoviebox.util.showSnackbar
import com.homework.mymoviebox.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private val viewModel: MovieViewModel by activityViewModels()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var pagingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        when (state) {
                            UiState.Idle -> {
                                /** Do nothing **/
                            }

                            UiState.Loading -> binding.stateLoading.visible()

                            is UiState.Error -> showError(state.throwable)

                            is UiState.Success -> renderMovies(state.data)
                        }
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() = binding.apply {
        rvFavouriteMovie.invisible()
        rvDiscoverMovie.visible()
        rvDiscoverMovie.apply {
            layoutManager = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            adapter = MoviePagedAdapter(::intentToDetailsActivity)
        }
    }

    private suspend fun renderMovies(pagingData: PagingData<Movie>) {
        binding.stateLoading.invisible()
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            (binding.rvDiscoverMovie.adapter as MoviePagedAdapter)
                .submitData(pagingData)
        }
    }


    private fun showError(throwable: Throwable) {
        Timber.d(throwable)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        val searchManager = requireActivity().getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            queryHint = getString(R.string.hint_search_movie)
            setOnQueryTextListener(this@MovieFragment)
        }

        menu.findItem(R.id.search).setOnActionExpandListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { submittedText ->
            if (submittedText.isBlank() || submittedText.isEmpty()) {
                binding.root.showSnackbar(getString(R.string.message_query_null))
            } else {
                viewModel.query.value = query
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.movieIntent.send(MovieIntent.SearchMovie)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onMenuItemActionExpand(p0: MenuItem): Boolean = true

    override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
        lifecycleScope.launch {
            viewModel.movieIntent.send(MovieIntent.FetchMovie)
        }
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}