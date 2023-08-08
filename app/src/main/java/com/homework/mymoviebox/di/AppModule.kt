package com.homework.mymoviebox.di

import com.homework.mymoviebox.domain.interactor.DetailInteractor
import com.homework.mymoviebox.domain.interactor.DetailUseCase
import com.homework.mymoviebox.domain.interactor.FavoriteInteractor
import com.homework.mymoviebox.domain.interactor.FavoriteUseCase
import com.homework.mymoviebox.domain.interactor.MovieInteractor
import com.homework.mymoviebox.domain.interactor.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsDetailUseCase(detailInteractor: DetailInteractor): DetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsFavoriteUseCase(favoriteInteractor: FavoriteInteractor): FavoriteUseCase
}