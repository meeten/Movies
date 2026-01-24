package com.example.movies.presentation.favorite

import androidx.lifecycle.ViewModel
import com.example.movies.domain.LoadedFavoritesUseCase
import com.example.movies.domain.state.FavoriteMoviesState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FavoriteMoviesViewModel @Inject constructor(
    loadedFavoritesUseCase: LoadedFavoritesUseCase
) : ViewModel() {
    val uiState = loadedFavoritesUseCase()
        .map { FavoriteMoviesState.FavoriteMovies(it) as FavoriteMoviesState }
        .onStart { emit(FavoriteMoviesState.Loading) }
}