package com.homework.mymoviebox.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.homework.mymoviebox.R
import com.homework.mymoviebox.databinding.ItemRowLayoutBinding
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.util.glideImageWithOptions


class CardViewHolder(private val binding: ItemRowLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie, onItemClick: (movie: Movie) -> Unit) =
        with(binding) {
            tvTitle.text = movie.title
            imgPoster.glideImageWithOptions(movie.getPosterUrl())

            cvItem.setOnClickListener { onItemClick(movie) }
            ratingBar.rating = movie.voteAverage.toFloat().div(2)
            tvRating.text = movie.voteAverage.toString()
            tvDate.text = movie.releaseDate
        }
}