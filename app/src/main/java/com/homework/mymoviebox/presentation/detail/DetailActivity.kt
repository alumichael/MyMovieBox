package com.homework.mymoviebox.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homework.mymoviebox.R
import com.homework.mymoviebox.data.states.UiState
import com.homework.mymoviebox.databinding.ActivityDetailBinding
import com.homework.mymoviebox.domain.model.DomainModel
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.util.Event
import com.homework.mymoviebox.util.glideImageWithOptions
import com.homework.mymoviebox.util.invisible
import com.homework.mymoviebox.util.showSnackbar
import com.homework.mymoviebox.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        enableBackButton()
        observeState()
        lifecycleScope.launchWhenCreated {
            viewModel.intent.send(DetailIntent.FetchDetails)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    when (state) {
                        UiState.Idle -> {
                        }
                        UiState.Loading -> handleLoading(state is UiState.Loading)
                        is UiState.Error -> handleError(state.throwable)
                        is UiState.Success -> {
                            handleLoading(state is UiState.Loading)
                            when (state.data) {
                                is Movie -> renderMovieDetails(state.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeFavoriteState(title: String) {
        viewModel.favoriteState.observe(this) { itemIsFavorite ->
            val buttonText = if (itemIsFavorite)
                R.string.remove_from_my_list
            else
                R.string.add_to_my_list
            binding.content.btnFavorite.text = getString(buttonText)
        }
        viewModel.snackBarText.observe(this) { event ->
            showSnackbar(event, title)
        }
    }

    private fun showSnackbar(event: Event<Int>, title: String) {
        val message = event.getContent() ?: return
        binding.root.showSnackbar(getString(message, title))
    }

    private fun renderMovieDetails(movie: Movie) {
        observeFavoriteState(movie.title)
        bindToView(movie)
    }


    private fun bindToView(movie: Movie) {
        val backdropUrl = movie.getBackdropUrl()
        val posterUrl = movie.getPosterUrl()
        bind(backdropUrl, posterUrl, movie)


        with(binding.content) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            ratingBar.rating = movie.voteAverage.toFloat()
                .div(2)
            tvRating.text = movie.voteAverage.toString()
            tvRuntime.text = getString(R.string.runtime, movie.runtime)
            tvDirector.text = movie.director
            tvGenre.text = movie.genres
            tvReleaseDate.text = movie.releaseDate
        }
    }



    private fun bind(backdropUrl: String, posterUrl: String, model: DomainModel) = with(binding) {
        imgCover.glideImageWithOptions(backdropUrl)
        with(binding.content) {
            imgPoster.glideImageWithOptions(posterUrl)
            btnFavorite.setOnClickListener { viewModel.toggleFavoriteState(model) }
            btnFavorite.isEnabled = true
        }
    }

    private fun handleError(throwable: Throwable) = Timber.e(throwable)

    private fun handleLoading(isLoading: Boolean) = with(binding.content) {
        if (isLoading) {
            details.invisible()
            shimmerContainer.startShimmer()
        } else {
            shimmerContainer.stopShimmer()
            shimmerContainer.invisible()
            details.visible()
        }
    }

    private fun enableBackButton() = supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}