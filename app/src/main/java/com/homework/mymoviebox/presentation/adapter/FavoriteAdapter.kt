package com.homework.mymoviebox.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.mymoviebox.data.states.ItemType
import com.homework.mymoviebox.databinding.ItemRowLayoutBinding
import com.homework.mymoviebox.domain.model.DomainModel
import com.homework.mymoviebox.domain.model.Movie
import com.homework.mymoviebox.presentation.viewholder.CardViewHolder


class FavoriteAdapter(
    private val onItemClick: (item: Movie) -> Unit,
) : RecyclerView.Adapter<CardViewHolder>() {
    private val list = ArrayList<Movie>()

    fun submitData(newList: List<Movie>) {
        with(list) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Movie -> ItemType.Movie.ordinal
        else -> throw IllegalArgumentException("Undefined data type ")
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        when (holder.itemViewType) {
            ItemType.Movie.ordinal -> {
                val item = list[position] as Movie
                val callback = onItemClick as (item: Movie) -> Unit
                holder.bind(item, callback)
            }

            else -> throw IllegalArgumentException("Undefined itemType")
        }

    override fun getItemCount(): Int = list.size
}