package com.homework.mymoviebox.presentation.detail

import com.homework.mymoviebox.domain.model.DomainModel


sealed class DetailIntent {
    object FetchDetails: DetailIntent()
    data class FavoriteStateChanged(val model: DomainModel): DetailIntent()
}