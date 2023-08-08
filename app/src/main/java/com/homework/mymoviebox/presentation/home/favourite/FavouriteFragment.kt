package com.homework.mymoviebox.presentation.home.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.mymoviebox.data.states.UiState
import com.homework.mymoviebox.databinding.FragmentFavoriteBinding
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.presentation.adapter.FavoriteAdapter
import com.homework.mymoviebox.presentation.home.movie.MovieIntent
import com.homework.mymoviebox.presentation.home.movie.MovieViewModel
import com.homework.mymoviebox.util.intentToDetailsActivity
import com.homework.mymoviebox.util.invisible
import com.homework.mymoviebox.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favouriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            lifecycleScope.launch {
                viewModel.movieIntent.send(MovieIntent.FetchFavouriteMovie)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.favouriteState.collect { favouriteState ->
                        when (favouriteState) {
                            UiState.Idle -> {}

                            UiState.Loading -> binding.stateLoading.visible()

                            is UiState.Error -> showError(favouriteState.throwable)

                            is UiState.Success -> {
                                binding.stateLoading.invisible()
                                renderFavouriteList(favouriteState.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showError(throwable: Throwable) {
        Timber.d(throwable)
    }

    private fun renderFavouriteList(list: List<Movie>) {
        if (list.isEmpty()) {
             binding.stateEmpty.root.visible()
         } else{
            binding.stateEmpty.root.invisible()
            favouriteAdapter = FavoriteAdapter(::intentToDetailsActivity)
            with(binding){
                rvFavorite.setHasFixedSize(true)
                rvFavorite.layoutManager = LinearLayoutManager(context)
                rvFavorite.adapter = favouriteAdapter

                favouriteAdapter.submitData(list)

            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}