package com.example.movies.di

import androidx.lifecycle.ViewModel
import com.example.movies.presentation.favorite.FavoriteMoviesViewModel
import com.example.movies.presentation.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavoriteMoviesViewModel::class)
    @Binds
    fun bindFavoritesViewModel(homeViewModel: FavoriteMoviesViewModel): ViewModel
}