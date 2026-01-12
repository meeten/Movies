package com.example.movies.presentation.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.FavoriteMoviesState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FavoriteMoviesViewModel(application: Application) : ViewModel() {

    val repository = MoviesRepository.getInstance(application)
    val uiState = repository.loadedFavoriteMovies
        .map { FavoriteMoviesState.FavoriteMovies(it) as FavoriteMoviesState }
        .onStart { emit(FavoriteMoviesState.Loading) }
}