package com.homework.mymoviebox.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.homework.mymoviebox.databinding.ItemRowLayoutBinding
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.presentation.viewholder.CardViewHolder


class MoviePagedAdapter(
    private val onItemClick: (movie: Movie) -> Unit,
) : PagingDataAdapter<Movie, CardViewHolder>(MovieDiffCallback()) {
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie, onItemClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

}