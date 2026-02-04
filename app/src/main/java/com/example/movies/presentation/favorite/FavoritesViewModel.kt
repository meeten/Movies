package com.example.movies.presentation.favorite

import androidx.lifecycle.ViewModel
import com.example.movies.domain.LoadedFavoritesUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    loadedFavoritesUseCase: LoadedFavoritesUseCase
) : ViewModel() {
    val uiState = loadedFavoritesUseCase()
        .map { FavoritesScreenState.FavoriteMovies(it) as FavoritesScreenState }
        .onStart { emit(FavoritesScreenState.Loading) }
}