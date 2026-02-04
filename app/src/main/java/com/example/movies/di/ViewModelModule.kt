package com.example.movies.di

import androidx.lifecycle.ViewModel
import com.example.movies.presentation.favorite.FavoritesViewModel
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
    @ViewModelKey(FavoritesViewModel::class)
    @Binds
    fun bindFavoritesViewModel(homeViewModel: FavoritesViewModel): ViewModel
}